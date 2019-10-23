package com.royal.service.impl;

import javax.annotation.Resource;

import com.royal.entity.CashCouponPushLog;
import com.royal.entity.UserCashCoupon;
import com.royal.service.ICashCouponPushLogService;
import com.royal.service.IUserCashCouponService;
import com.royal.util.DateUtils;
import com.royal.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.CashCoupon;
import com.royal.mapper.CashCouponMapper;
import com.royal.service.ICashCouponService;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;


/**
 * 描述：代金券 服务实现层
 *
 * @author Royal
 * @date 2019年05月23日 19:35:09
 */
@Service
@Transactional
public class CashCouponServiceImpl extends BaseServiceImpl<CashCoupon> implements ICashCouponService {

    @Autowired
    private ICashCouponPushLogService cashCouponPushLogService;
    @Autowired
    private IUserCashCouponService userCashCouponService;

    private CashCouponMapper cashCouponMapper;

    @Resource
    public void setBaseMapper(CashCouponMapper mapper) {
        super.setBaseMapper(mapper);
        this.cashCouponMapper = mapper;
    }

    @Override
    public BigDecimal pushCashCoupon(String loginName, String grantCondition, BigDecimal payMoney) {
        Example ex = new Example(CashCoupon.class);
        Example.Criteria criteria = ex.createCriteria().andEqualTo("grantCondition", grantCondition).andEqualTo("grantMode", "AUTO").andEqualTo("ccStatus", "ENABLE");
        if (payMoney != null) {
            criteria.andLessThanOrEqualTo("amountToMark", payMoney);
            ex.setOrderByClause("cc_money DESC");
        } else {
            ex.setOrderByClause("create_time DESC");
        }
        List<CashCoupon> list = this.baseMapper.selectByExample(ex);
        if (list != null && list.size() > 0) {
            String uuid = Tools.getRandomCode(32, 7);
            CashCoupon cashCoupon = list.get(0);
            CashCouponPushLog cashCouponPushLog = new CashCouponPushLog();
            cashCouponPushLog.setCashCouponId(cashCoupon.getId());
            cashCouponPushLog.setCreateTime(DateUtils.getCurrDateTimeStr());
            cashCouponPushLog.setId(uuid);
            cashCouponPushLog.setLoginNames(loginName);
            cashCouponPushLog.setOperator("SYSTEM");
            cashCouponPushLog.setPushCount(1);
            cashCouponPushLog.setTargetPopulation("PART_USER");
            cashCouponPushLogService.add(cashCouponPushLog);
            UserCashCoupon userCashCoupon = new UserCashCoupon();
            userCashCoupon.setLoginName(loginName);
            userCashCoupon.setCashCouponId(cashCoupon.getId());
            userCashCoupon.setCashCouponPushLogId(uuid);
            userCashCoupon.setCcMoney(cashCoupon.getCcMoney());
            userCashCoupon.setCreateTime(DateUtils.getCurrDateTimeStr());
            userCashCoupon.setPastDueTime(DateUtils.getFormatDateTime(
                    DateUtils.getDateBeforeOrAfter(cashCoupon.getPastDueDay())));
            userCashCoupon.setStartTime(DateUtils.getCurrDateTimeStr());
            userCashCouponService.add(userCashCoupon);
        return cashCoupon.getCcMoney();
        }
        return null;
    }
}