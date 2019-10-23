package com.royal.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.*;
import com.royal.entity.enums.FundPipelineEnum;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.enums.SellStatusEnum;
import com.royal.entity.json.PageData;
import com.royal.entity.json.Result;
import com.royal.service.*;
import com.royal.util.DateUtils;
import com.royal.util.JSONUtils;
import com.royal.util.MT4Utils;
import com.royal.util.Tools;
import org.apache.commons.collections.BagUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.mapper.TransactionRecordMapper;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 描述：交易记录表 服务实现层
 *
 * @author Royal
 * @date 2018年12月13日 22:47:22
 */
@Service
@Transactional
public class TransactionRecordServiceImpl extends BaseServiceImpl<TransactionRecord> implements ITransactionRecordService {
    @Autowired
    private ISymbolOpenCloseService symbolOpenCloseService;
    @Autowired
    private IUserCashCouponService userCashCouponService;
    @Autowired
    private IUserAmountService userAmountService;
    @Autowired
    private ISymbolInfoService symbolInfoService;
    @Autowired
    private IFundPipelineService fundPipelineService;

    private TransactionRecordMapper transactionRecordMapper;

    @Resource
    public void setBaseMapper(TransactionRecordMapper mapper) {
        super.setBaseMapper(mapper);
        this.transactionRecordMapper = mapper;
    }

    private static final Logger log = LoggerFactory.getLogger(TransactionRecordServiceImpl.class);

    /**
     * 买入
     *
     * @param transactionRecord
     * @return
     */
    @Override
    public Result buy(TransactionRecord transactionRecord) {
        SymbolInfo symbolInfo = symbolInfoService.findByCode(transactionRecord.getSymbolCode());
        if (symbolInfo == null || symbolInfo.getStatus() != 1) {
            return new Result(ResultEnum.SYMBOL_NOT_BUY);
        }
        SymbolRecord symbolRecord = MT4Utils.getSymbolRecord(symbolInfo.getSymbolCode());
        if (symbolRecord == null) {
            return new Result(ResultEnum.PRICE_GET_ERROR);
        }
        BigDecimal price = symbolRecord.getPrice();

        //购买成本
        Integer money = transactionRecord.getUnitPrice() * transactionRecord.getLot();
        transactionRecord.setCreateTime(new Date());
        transactionRecord.setExponent(price);
        transactionRecord.setId(null);
        transactionRecord.setMoney(money);
        transactionRecord.setOvernightFee(new BigDecimal(0));
        //计算数量
        Result result = setQuantity(transactionRecord, symbolInfo);
        if (result != null) {
            return result;
        }
        //设置止损止盈价格
        result = getStopExponent(transactionRecord, symbolInfo, price);
        if (result != null) {
            return result;
        }
        //优惠券买入判断
        if (transactionRecord.getUserCashCouponId() != null) {
            UserCashCoupon userCashCoupon = userCashCouponService.findById(transactionRecord.getUserCashCouponId());
            if (userCashCoupon == null) {
                return new Result(ResultEnum.NOT_FOUND_CASH_COUPON);
            }
            if (!userCashCoupon.getLoginName().equals(transactionRecord.getLoginName())) {
                return new Result(ResultEnum.ILLEGALITY);
            }
            if (userCashCoupon.getCcMoney().compareTo(new BigDecimal(money + "")) != 0) {
                return new Result(ResultEnum.CASH_COUPON_MONEY_ERROR);
            }
            transactionRecord.setCommissionCharges(new BigDecimal("0"));
            transactionRecord.setIsOvernight(2);
            transactionRecordMapper.insert(transactionRecord);
            userCashCoupon.setOrderId(transactionRecord.getId());
            userCashCouponService.update(userCashCoupon);
            return new Result(price);
        } else {

            UserAmount userAmount = userAmountService.findByLoginName(new UserAmount(), transactionRecord.getLoginName());
            transactionRecord.setIsOvernight(1);
            //计算手续费
            BigDecimal commissionCharges = getCommissionCharges(transactionRecord, symbolInfo);
            if (commissionCharges == null) {
                return new Result(ResultEnum.BUY_INFO_ERROR);
            }
            //查看用户余额是否足够
            BigDecimal totalCost = commissionCharges.add(new BigDecimal(money));
            System.out.println(totalCost);
            if (userAmount.getBalance().compareTo(totalCost) < 0) {
                return new Result(ResultEnum.AMOUNT_INSUFFICIENT_BALANCE);
            }
            transactionRecordMapper.insert(transactionRecord);
            userAmount.setBalance(userAmount.getBalance().subtract(totalCost));
            userAmountService.update(userAmount);
            FundPipeline fundPipeline = new FundPipeline(FundPipelineEnum.BUY.getKey(),totalCost,userAmount.getBalance(),userAmount.getLoginName(),transactionRecord.getId().toString(),FundPipelineEnum.EXPENDITURE.getKey());
            fundPipelineService.add(fundPipeline);
            return new Result(price);
        }
    }

    /**
     * 平仓
     *
     * @param transactionRecord
     * @return
     */
    @Override
    public Result sell(TransactionRecord transactionRecord) {
        TransactionRecord tr = transactionRecordMapper.selectByPrimaryKey(transactionRecord.getId());
        if (tr == null || !tr.getLoginName().equals(transactionRecord.getLoginName()) || tr.getTransactionStatus() != 1) {
            return new Result(ResultEnum.TRANSACTION_INFO_ERROR);
        }
        SymbolRecord symbolRecord = MT4Utils.getSymbolRecord(tr.getSymbolCode());
        if (symbolRecord == null) {
            return new Result(ResultEnum.PRICE_GET_ERROR);
        }
        BigDecimal price = symbolRecord.getPrice();
        //获取盈利
        BigDecimal profit = updateSell(tr, price, SellStatusEnum.USER.getKey());
        transactionRecordMapper.updateByPrimaryKeySelective(tr);
        UserAmount userAmount = userAmountService.findByLoginName(new UserAmount(), tr.getLoginName());
        if (userAmount == null) {
            return new Result(ResultEnum.ACCOUNT_NONENTIY);
        }
        //代金券平仓
        if (tr.getUserCashCouponId() != null) {
            if (profit.compareTo(new BigDecimal("0")) > 0) {
                userAmount.setBalance(profit);
                userAmountService.update(userAmount);
            }
        } else {
            updateBalance(userAmount,tr,profit);
        }
        return new Result(price);
    }

    /**
     * 获取盈利金额并设置平仓信息
     *
     * @param tr
     * @param price
     * @return
     */
    private BigDecimal updateSell(TransactionRecord tr, BigDecimal price,String sellStatus) {
        //计算盈亏 公式 买涨 （当前价格-买入价格）乘以 quantityXXX 乘以 lot  买跌 （买入价格-当前价格）乘以 quantityXXX 乘以 lot
        BigDecimal floatingProfitAndLoss = null;
        if (tr.getRansactionType() == 1) {
            floatingProfitAndLoss = price.subtract(tr.getExponent());
        } else {
            floatingProfitAndLoss = tr.getExponent().subtract(price);
        }
        Integer number = tr.getQuantity() * tr.getLot();
        BigDecimal profit = new BigDecimal(number).multiply(floatingProfitAndLoss);
        tr.setProfit(profit.subtract(tr.getCommissionCharges()).subtract(tr.getOvernightFee()));
        tr.setEndTime(new Date());
        tr.setCloseOutPrice(price);
        tr.setTransactionStatus(2);
        tr.setSellStatus(sellStatus);
        tr.setGrossProfit(profit);
        return profit;
    }

    private void updateBalance(UserAmount userAmount,TransactionRecord tr,BigDecimal profit){
        BigDecimal balance = userAmount.getBalance().add(new BigDecimal(tr.getMoney()).add(profit));
        if (tr.getOvernightFee().compareTo(balance) > 0) {
            userAmount.setBalance(new BigDecimal("0"));
        } else {
            userAmount.setBalance(balance);
        }
        userAmountService.update(userAmount);
        FundPipeline fundPipeline = new FundPipeline(FundPipelineEnum.SELL.getKey(),new BigDecimal(tr.getMoney()).add(profit),userAmount.getBalance(),userAmount.getLoginName(),tr.getId().toString(),FundPipelineEnum.INCOME.getKey());
        fundPipelineService.add(fundPipeline);
    }

    /**
     * 强制平仓
     *
     * @param tr
     * @return
     */
    @Override
    public Result constraintSell(TransactionRecord tr, BigDecimal price,String sellStatus) {
        //获取盈利
        BigDecimal profit = updateSell(tr, price,sellStatus);
        transactionRecordMapper.updateByPrimaryKeySelective(tr);
        UserAmount userAmount = userAmountService.findByLoginName(new UserAmount(), tr.getLoginName());
        if (userAmount == null) {
            return new Result(ResultEnum.ACCOUNT_NONENTIY);
        }
        updateBalance(userAmount,tr,profit);
        return new Result(price);
    }

    /**
     * 获取交易记录
     *
     * @param transactionRecord
     * @param pd
     * @return
     */
    @Override
    public PageInfo<TransactionRecord> getMyPage(TransactionRecord transactionRecord, PageData pd) {
        Example ex = new Example(TransactionRecord.class);
        Example.Criteria ec = ex.createCriteria();
        //全部记录
        if (transactionRecord.getTransactionStatus() == null) {
            ex.setOrderByClause("create_time DESC");
            //挂单记录
        } else if (transactionRecord.getTransactionStatus() == 3) {
            ex.setOrderByClause("entry_orders_time DESC");
            ec.andEqualTo("transactionStatus", transactionRecord.getTransactionStatus());
            //非挂单交易记录
        } else if (transactionRecord.getTransactionStatus() == 0) {
            ex.setOrderByClause("create_time DESC");
            ec.andNotEqualTo("transactionStatus", 3);
            //指定记录
        } else {
            ec.andEqualTo("transactionStatus", transactionRecord.getTransactionStatus());
            ex.setOrderByClause("create_time DESC");
        }
        if (!Tools.isEmpty(transactionRecord.getLoginName())) {
            ec.andEqualTo("loginName", transactionRecord.getLoginName());
        }

        Page<Object> ph = PageHelper.startPage(pd.getPage(), pd.getPageSize());
        PageInfo<TransactionRecord> list = new PageInfo<TransactionRecord>(transactionRecordMapper.selectByExample(ex));
        if (list != null && list.getList() != null) {
            List<SymbolInfo> symbolInfoList = symbolInfoService.getAll();
            Map<String, SymbolInfo> map = new HashMap<String, SymbolInfo>();
            for (SymbolInfo symbolInfo : symbolInfoList) {
                map.put(symbolInfo.getSymbolCode(), symbolInfo);
            }
            for (TransactionRecord record : list.getList()) {
                record.setSymbolName(map.get(record.getSymbolCode()).getSymbolName());
                SymbolRecord symbolRecord = MT4Utils.getSymbolRecord(record.getSymbolCode());
                if (symbolRecord == null) {
                    SymbolOpenClose symbolOpenClose = symbolOpenCloseService.getBySymbolCode(record.getSymbolCode());
                    record.setPresentPrice(symbolOpenClose.getClose());
                } else {
                    record.setPresentPrice(symbolRecord.getPrice());
                }
            }
        }
        return list;
    }

    /**
     * 挂单
     *
     * @param transactionRecord
     * @return
     */
    @Override
    public Result reserve(TransactionRecord transactionRecord) {
        Example ex = new Example(TransactionRecord.class);
        ex.createCriteria().andEqualTo("loginName", transactionRecord.getLoginName()).andEqualTo(
                "transactionStatus", 3).andEqualTo("symbolCode", transactionRecord.getSymbolCode());
        int count = transactionRecordMapper.selectCountByExample(ex);
        if (count >= 2) {
            return new Result(ResultEnum.RESERVE_COUNT_MAX);
        }
//        Example ex = new Example(TransactionRecord.class);
//        ex.createCriteria().andEqualTo("loginName", transactionRecord.getLoginName()).andEqualTo(
//                "transactionStatus", 3);
//        int count = transactionRecordMapper.selectCountByExample(ex);
//        if (count > 3) {
//            return new Result(ResultEnum.RESERVE_COUNT_MAX);
//        }
        SymbolInfo symbolInfo = symbolInfoService.findByCode(transactionRecord.getSymbolCode());
        if (symbolInfo == null || symbolInfo.getStatus() != 1) {
            return new Result(ResultEnum.SYMBOL_NOT_BUY);
        }
        UserAmount userAmount = userAmountService.findByLoginName(new UserAmount(), transactionRecord.getLoginName());
        transactionRecord.setEntryOrdersTime(new Date());
        transactionRecord.setId(null);
        transactionRecord.setMoney(transactionRecord.getUnitPrice() * transactionRecord.getLot());
        transactionRecord.setOvernightFee(new BigDecimal(0));
        transactionRecord.setIsOvernight(1);
        Result result = setQuantity(transactionRecord, symbolInfo);
        if (result != null) {
            return result;
        }
        //计算手续费
        BigDecimal commissionCharges = getCommissionCharges(transactionRecord, symbolInfo);
        if (commissionCharges == null) {
            return new Result(ResultEnum.TRANSACTION_INFO_ERROR);
        }
        //查看用户余额是否足够
        BigDecimal totalCost =
                commissionCharges.add(new BigDecimal(transactionRecord.getUnitPrice() * transactionRecord.getLot()));
        if (userAmount.getBalance().compareTo(totalCost) < 0) {
            return new Result(ResultEnum.AMOUNT_INSUFFICIENT_BALANCE);
        }
        //设置挂单价格 挂单价格+-挂单浮动点位*数量*挂单点位对应价格
        setEntryOrdersPrice(symbolInfo, transactionRecord);
        transactionRecordMapper.insert(transactionRecord);
//        userAmount.setBalance(userAmount.getBalance().subtract(totalCost));
//        userAmountService.update(userAmount);
        return new Result(true);
    }

    /**
     * 挂单转建仓
     *
     * @param transactionRecord
     * @return
     */
    @Override
    public Result reserveToBuy(TransactionRecord transactionRecord, BigDecimal price) {
        UserAmount userAmount = userAmountService.findByLoginName(new UserAmount(), transactionRecord.getLoginName());
        SymbolInfo symbolInfo = symbolInfoService.findByCode(transactionRecord.getSymbolCode());
        transactionRecord.setCreateTime(new Date());
        transactionRecord.setExponent(price);
        transactionRecord.setMoney(transactionRecord.getUnitPrice() * transactionRecord.getLot());
        transactionRecord.setOvernightFee(new BigDecimal(0));
        transactionRecord.setIsOvernight(1);
        transactionRecord.setTransactionStatus(1);

        //设置止损止盈价格
        Result result = getStopExponent(transactionRecord, symbolInfo, price);
        if (result != null) {
            return result;
        }
        BigDecimal totalCost = transactionRecord.getCommissionCharges().add(new BigDecimal(transactionRecord.getMoney().toString()));
        if (userAmount.getBalance().compareTo(totalCost) < 0) {
            transactionRecord.setTransactionStatus(4);
            transactionRecordMapper.updateByPrimaryKeySelective(transactionRecord);
            return new Result(ResultEnum.AMOUNT_INSUFFICIENT_BALANCE);
        }
        TransactionRecord tr = transactionRecordMapper.selectByPrimaryKey(transactionRecord.getId());
        if (tr.getTransactionStatus() == 3) {
            userAmount.setBalance(userAmount.getBalance().subtract(totalCost));
            userAmountService.update(userAmount);
            transactionRecordMapper.updateByPrimaryKeySelective(transactionRecord);
            FundPipeline fundPipeline = new FundPipeline(FundPipelineEnum.BUY.getKey(),totalCost,userAmount.getBalance(),userAmount.getLoginName(),tr.getId().toString(),FundPipelineEnum.EXPENDITURE.getKey());
            fundPipelineService.add(fundPipeline);
        }
        return new Result(price);
    }

    /**
     * 计算过夜费
     */
    @Override
    public void computeOvernightFee() {
        //获取产品详情
        List<SymbolInfo> symbolInfoList = symbolInfoService.getAll();
        Map<String, SymbolInfo> map = new HashMap<String, SymbolInfo>();
        for (SymbolInfo symbolInfo : symbolInfoList) {
            map.put(symbolInfo.getSymbolCode(), symbolInfo);
        }
        //获取所有需要处理的交易
        Example ex = new Example(TransactionRecord.class);
        ex.createCriteria().andEqualTo("isOvernight", 1).andEqualTo("transactionStatus", 1);
        List<TransactionRecord> transactionRecordList = transactionRecordMapper.selectByExample(ex);
        if (transactionRecordList == null || transactionRecordList.size() <= 0) {
            return;
        }
        //开始处理
        for (TransactionRecord transactionRecord : transactionRecordList) {
            try {
                BigDecimal overnightFee = getOvernightFee(transactionRecord, map);
//                //获取用户余额
//                UserAmount userAmount =
//                        userAmountService.findByLoginName(new UserAmount(), transactionRecord.getLoginName());
//                if (userAmount.getBalance().compareTo(overnightFee) < 0) {
//                    //余额不足，设置为不过夜，并强制交易
//                    transactionRecord.setIsOvernight(2);
//
//                } else {
                //余额足够，扣除过夜费
                transactionRecord.setOvernightFee(transactionRecord.getOvernightFee().add(overnightFee));
//                    userAmount.setBalance(userAmount.getBalance().subtract(overnightFee));
//                    userAmountService.update(userAmount);
//                }
                transactionRecordMapper.updateByPrimaryKeySelective(transactionRecord);
            } catch (Exception e) {
                log.error("定时器计算过夜费时出错：" + JSONUtils.toJSONString(transactionRecord), e);
            }
        }
    }

    /**
     * 获取全部未平仓的交易记录
     *
     * @param transactionStatusList
     * @return
     */
    @Override
    public List<TransactionRecord> findByTransactionStatus(List<Integer> transactionStatusList) {
        Example ex = new Example(TransactionRecord.class);
        ex.createCriteria().andIn("transactionStatus", transactionStatusList);
        return transactionRecordMapper.selectByExample(ex);
    }

    /**
     * 清除挂单记录
     *
     * @param dateBeforeOrAfter
     */
    @Override
    public void deleteByEntryOrdersTime(Date dateBeforeOrAfter) {
        TransactionRecord tr = new TransactionRecord();
        tr.setTransactionStatus(4);
        Example ex = new Example(TransactionRecord.class);
        ex.createCriteria().andLessThanOrEqualTo("entryOrdersTime", dateBeforeOrAfter).andEqualTo(
                "transactionStatus", 3);
        transactionRecordMapper.updateByExampleSelective(tr, ex);
    }

    /**
     * 不过夜强制交易
     */
    @Override
    public void noDaylightSavingTime() {
        //获取所有需要处理的交易
        Example ex = new Example(TransactionRecord.class);
        ex.createCriteria().andEqualTo("isOvernight", 2).andEqualTo("transactionStatus", 1);
        List<TransactionRecord> transactionRecordList = transactionRecordMapper.selectByExample(ex);
        if (transactionRecordList == null || transactionRecordList.size() <= 0) {
            return;
        }
        //获取产品详情
        List<SymbolInfo> symbolInfoList = symbolInfoService.getAll();
        Map<String, SymbolInfo> map = new HashMap<String, SymbolInfo>();
        for (SymbolInfo symbolInfo : symbolInfoList) {
            map.put(symbolInfo.getSymbolCode(), symbolInfo);
        }
        //开始处理
        for (TransactionRecord transactionRecord : transactionRecordList) {
            try {
                SymbolInfo symbolInfo = map.get(transactionRecord.getSymbolCode());
                constraintSell(transactionRecord, MT4Utils.getSymbolRecord(symbolInfo.getSymbolCode()).getPrice(),SellStatusEnum.OVERNIGHT.getKey());
            } catch (Exception e) {
                log.error("不过夜强制交易出错：" + JSONUtils.toJSONString(transactionRecord), e);
            }
        }
    }

    /**
     * 撤销挂单
     *
     * @param transactionRecord
     * @return
     */
    @Override
    public Result cancelReserve(TransactionRecord transactionRecord) {
        TransactionRecord tr = transactionRecordMapper.selectByPrimaryKey(transactionRecord.getId());
        if (tr == null || !transactionRecord.getLoginName().equals(tr.getLoginName()) || tr.getTransactionStatus() != 3) {
            return new Result(ResultEnum.TRANSACTION_INFO_ERROR);
        }
        tr.setTransactionStatus(4);
        transactionRecordMapper.updateByPrimaryKeySelective(tr);
        return null;
    }

    /**
     * 根据产品编码及价格获取需要处理的挂单记录
     *
     * @param symbolRecord
     * @return
     */
    @Override
    public List<TransactionRecord> selectReserveByPrice(SymbolRecord symbolRecord) {
        Example ex = new Example(TransactionRecord.class);
        ex.createCriteria().andEqualTo("symbolCode", symbolRecord.getSymbolCode()).andEqualTo("transactionStatus", 3).andLessThanOrEqualTo("entryOrdersStratPrice",
                symbolRecord.getPrice()).andGreaterThanOrEqualTo("entryOrdersEndPrice", symbolRecord.getPrice());
        return transactionRecordMapper.selectByExample(ex);
    }

    public BigDecimal getOvernightFee(TransactionRecord transactionRecord, Map<String, SymbolInfo> map) {
        SymbolInfo symbolInfo = null;
        if (map != null && map.get(transactionRecord.getSymbolCode()) != null) {
            symbolInfo = map.get(transactionRecord.getSymbolCode());
        } else {
            symbolInfo = symbolInfoService.findByCode(transactionRecord.getSymbolCode());
        }
        BigDecimal overnightFee = null;
        if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceOne()) {
            overnightFee = symbolInfo.getQuantityOvernightFeeOne().multiply(new BigDecimal(transactionRecord.getLot()));
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceTwo()) {
            overnightFee = symbolInfo.getQuantityOvernightFeeTwo().multiply(new BigDecimal(transactionRecord.getLot()));
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceThree()) {
            overnightFee = symbolInfo.getQuantityOvernightFeeThree().multiply(new BigDecimal(transactionRecord.getLot()));
        }
        return overnightFee;
    }


    /**
     * 设置为过夜
     *
     * @param transactionRecord
     * @return
     */
    @Override
    public Result overnight(TransactionRecord transactionRecord) {
        if (transactionRecord.getIsOvernight() == 1) {
            return new Result(ResultEnum.REPETITIVE_OPERATION_ERROR);
        }
        try {
//            BigDecimal overnightFee = getOvernightFee(transactionRecord, null);
//            //获取用户余额
//            UserAmount userAmount =
//                    userAmountService.findByLoginName(new UserAmount(), transactionRecord.getLoginName());
//            if (userAmount.getBalance().compareTo(overnightFee) < 0) {
//                return new Result(ResultEnum.AMOUNT_INSUFFICIENT_BALANCE);
//            } else {
            //余额足够，扣除过夜费
            transactionRecord.setIsOvernight(1);
//                transactionRecord.setOvernightFee(transactionRecord.getOvernightFee().add(overnightFee));
//                userAmount.setBalance(userAmount.getBalance().subtract(overnightFee));
//                userAmountService.update(userAmount);
//            }
            transactionRecordMapper.updateByPrimaryKeySelective(transactionRecord);
            return new Result(true);
        } catch (Exception e) {
            log.error("设置为过夜时出错：" + JSONUtils.toJSONString(transactionRecord), e);
            return new Result(e);
        }
    }

    /**
     * 设置为不过夜
     *
     * @param transactionRecord
     * @return
     */
    @Override
    public Result notOvernight(TransactionRecord transactionRecord) {
        if (transactionRecord.getIsOvernight() == 2) {
            return new Result(ResultEnum.REPETITIVE_OPERATION_ERROR);
        }
        try {
//            BigDecimal overnightFee = getOvernightFee(transactionRecord, null);
//            //获取用户余额
//            UserAmount userAmount =
//                    userAmountService.findByLoginName(new UserAmount(), transactionRecord.getLoginName());
//            if (transactionRecord.getOvernightFee().compareTo(overnightFee) < 0) {
//                overnightFee = transactionRecord.getOvernightFee();
//            }
            //过夜费足够，补回过夜费
            transactionRecord.setIsOvernight(2);
//            transactionRecord.setOvernightFee(transactionRecord.getOvernightFee().subtract(overnightFee));
//            userAmount.setBalance(userAmount.getBalance().add(overnightFee));
//            userAmountService.update(userAmount);
            transactionRecordMapper.updateByPrimaryKeySelective(transactionRecord);
            return new Result(true);
        } catch (Exception e) {
            log.error("设置为过夜时出错：" + JSONUtils.toJSONString(transactionRecord), e);
            return new Result(e);
        }
    }

    /**
     * 获取该产品需要自动平仓的数据
     *
     * @return
     */
    @Override
    public List<TransactionRecord> findListByCodeAndPrice(String lockTime) {
        Example ex = new Example(TransactionRecord.class);
        ex.createCriteria().andEqualTo("lockTime", lockTime).andEqualTo("transactionStatus", 1);
        return transactionRecordMapper.selectByExample(ex);
    }

    @Override
    public String setTransactionRecordLockTime(String symbolCode, BigDecimal price) {
        TransactionRecord tr = new TransactionRecord();
        String lockTime = String.valueOf(System.currentTimeMillis());
        tr.setLockTime(lockTime);
        Example ex = new Example(TransactionRecord.class);
        ex.or().andEqualTo("symbolCode", symbolCode).andEqualTo("transactionStatus", 1).andEqualTo("ransactionType", 1).andGreaterThanOrEqualTo("stopLossExponent", price).andIsNull("lockTime");
        ex.or().andEqualTo("symbolCode", symbolCode).andEqualTo("transactionStatus", 1).andEqualTo("ransactionType", 1).andLessThanOrEqualTo("stopProfitExponent", price).andIsNull("lockTime");
        ex.or().andEqualTo("symbolCode", symbolCode).andEqualTo("transactionStatus", 1).andEqualTo("ransactionType", 2).andLessThanOrEqualTo("stopLossExponent", price).andIsNull("lockTime");
        ex.or().andEqualTo("symbolCode", symbolCode).andEqualTo("transactionStatus", 1).andEqualTo("ransactionType", 2).andGreaterThanOrEqualTo("stopProfitExponent", price).andIsNull("lockTime");
        transactionRecordMapper.updateByExampleSelective(tr, ex);
        return lockTime;
    }


    /**
     * 获取手续费
     *
     * @param transactionRecord
     * @param symbolInfo
     * @return
     */
    public BigDecimal getCommissionCharges(TransactionRecord transactionRecord, SymbolInfo symbolInfo) {
        if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceOne()) {
            transactionRecord.setCommissionCharges(symbolInfo.getQuantityCommissionChargesOne().multiply(new BigDecimal(transactionRecord.getLot().toString())));
            return symbolInfo.getQuantityCommissionChargesOne().multiply(new BigDecimal(transactionRecord.getLot().toString()));
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceTwo()) {
            transactionRecord.setCommissionCharges(symbolInfo.getQuantityCommissionChargesTwo().multiply(new BigDecimal(transactionRecord.getLot().toString())));
            return symbolInfo.getQuantityCommissionChargesTwo().multiply(new BigDecimal(transactionRecord.getLot().toString()));
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceThree()) {
            transactionRecord.setCommissionCharges(symbolInfo.getQuantityCommissionChargesThree().multiply(new BigDecimal(transactionRecord.getLot().toString())));
            return symbolInfo.getQuantityCommissionChargesThree().multiply(new BigDecimal(transactionRecord.getLot().toString()));
        } else {
            return null;
        }
    }

    public Result setQuantity(TransactionRecord transactionRecord, SymbolInfo symbolInfo) {
        if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceOne()) {
            transactionRecord.setQuantity(symbolInfo.getQuantityOne());
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceTwo()) {
            transactionRecord.setQuantity(symbolInfo.getQuantityTwo());
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceThree()) {
            transactionRecord.setQuantity(symbolInfo.getQuantityThree());
        } else {
            return new Result(ResultEnum.PROFIT_COUNT_ERROR);
        }
        return null;
    }

    /**
     * 设置止损止盈价格
     *
     * @param transactionRecord
     * @param symbolInfo
     * @param price
     * @return
     */
    public Result getStopExponent(TransactionRecord transactionRecord, SymbolInfo symbolInfo, BigDecimal price) {
        //计算止损止盈价格
        //得到一个波动点对应的价格
        Integer number = null;
        BigDecimal priceFluctuation = null;
        if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceOne()) {
            priceFluctuation = symbolInfo.getQuantityPriceFluctuationOne().multiply(new BigDecimal(transactionRecord.getLot()));
            number = symbolInfo.getQuantityOne() * transactionRecord.getLot();
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceTwo()) {
            priceFluctuation = symbolInfo.getQuantityPriceFluctuationTwo().multiply(new BigDecimal(transactionRecord.getLot()));
            number = symbolInfo.getQuantityTwo() * transactionRecord.getLot();
        } else if (transactionRecord.getUnitPrice() == symbolInfo.getUnitPriceThree()) {
            priceFluctuation = symbolInfo.getQuantityPriceFluctuationThree().multiply(new BigDecimal(transactionRecord.getLot()));
            number = symbolInfo.getQuantityThree() * transactionRecord.getLot();
        } else {
            return new Result(ResultEnum.PROFIT_COUNT_ERROR);
        }
        //得到止损金额（最多亏多少钱）
        BigDecimal stopLossMoney = null;
        if (transactionRecord.getStopLossCount() == 0) {
            stopLossMoney = new BigDecimal(transactionRecord.getUnitPrice() * transactionRecord.getLot() * 0.8);
        } else {
            stopLossMoney = priceFluctuation.multiply(new BigDecimal(transactionRecord.getStopLossCount()));
        }
        //得到止盈金额(最多赚多少钱)
        BigDecimal stopProfitMoney = null;

        if (transactionRecord.getStopProfitCount() == 0) {
            stopProfitMoney = new BigDecimal(transactionRecord.getUnitPrice() * transactionRecord.getLot() * 2);
        } else {
            stopProfitMoney = priceFluctuation.multiply(new BigDecimal(transactionRecord.getStopProfitCount()));
        }
        //计算波动点位是否合法（）
        if (stopProfitMoney.compareTo(new BigDecimal(transactionRecord.getUnitPrice() * transactionRecord.getLot() / 10)) < 0 || stopProfitMoney.compareTo(new BigDecimal(transactionRecord.getUnitPrice() * transactionRecord.getLot() * 2)) > 0) {
            return new Result(ResultEnum.PROFIT_COUNT_ERROR);
        }
        if (stopLossMoney.compareTo(new BigDecimal(transactionRecord.getUnitPrice() * transactionRecord.getLot() / 10)) < 0 || stopLossMoney.compareTo(new BigDecimal(transactionRecord.getUnitPrice() * transactionRecord.getLot() * 0.8)) > 0) {
            return new Result(ResultEnum.PROFIT_COUNT_ERROR);
        }
        //止损止盈价格计算公式  当前行情+-（止损止盈金额/购买数量）
        if (transactionRecord.getRansactionType() == 1) {
            transactionRecord.setStopLossExponent(price.subtract(stopLossMoney.divide(new BigDecimal(number), 2, BigDecimal.ROUND_HALF_UP)));
            transactionRecord.setStopProfitExponent(price.add(stopProfitMoney.divide(new BigDecimal(number), 2, BigDecimal.ROUND_HALF_UP)));
        } else if (transactionRecord.getRansactionType() == 2) {
            transactionRecord.setStopLossExponent(price.add(stopLossMoney.divide(new BigDecimal(number), 2, BigDecimal.ROUND_HALF_UP)));
            transactionRecord.setStopProfitExponent(price.subtract(stopProfitMoney.divide(new BigDecimal(number), 2, BigDecimal.ROUND_HALF_UP)));
        } else {
            return new Result(ResultEnum.BUY_INFO_ERROR);
        }
        return null;
    }

    /**
     * 设置挂单价格
     *
     * @param symbolInfo
     * @param transactionRecord
     */
    public void setEntryOrdersPrice(SymbolInfo symbolInfo, TransactionRecord transactionRecord) {
        BigDecimal floatPrice =
                symbolInfo.getEntryOrders().multiply(new BigDecimal(transactionRecord.getErrorRange()));
        transactionRecord.setEntryOrdersStratPrice(transactionRecord.getEntryOrdersPrice().subtract(floatPrice));
        transactionRecord.setEntryOrdersEndPrice(transactionRecord.getEntryOrdersPrice().add(floatPrice));
    }

}