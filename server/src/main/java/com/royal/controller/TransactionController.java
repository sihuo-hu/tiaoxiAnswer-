package com.royal.controller;

import com.github.pagehelper.PageInfo;
import com.royal.entity.TransactionRecord;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.PageData;
import com.royal.entity.json.Result;
import com.royal.service.ITransactionRecordService;
import com.royal.service.IUserAmountService;
import com.royal.service.IUserService;
import com.royal.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 描述：交易控制层
 *
 * @author Royal
 * @date 2018年12月13日 22:47:22
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController extends BaseController {

    @Autowired
    private ITransactionRecordService transactionRecordService;

    /**
     * 描述：买入  交易状态 1建仓 2平仓 3挂单 4取消 5爆仓
     */
    @RequestMapping(value = "/buy")
    @ResponseBody
    public Result buy(TransactionRecord transactionRecord) throws Exception {
        try {
            if (Tools.isEmpty(transactionRecord.getLoginName(), transactionRecord.getLot(),
                    transactionRecord.getTransactionStatus(), transactionRecord.getSymbolCode(),
                    transactionRecord.getRansactionType(), transactionRecord.getUnitPrice(), transactionRecord.getId())) {
                return new Result(ResultEnum.PARAMETER_ERROR);
            }
            if(transactionRecord.getTransactionStatus()!=1){
                return new Result(ResultEnum.TRANSACTION_STASUA_ERROR);
            }
            return transactionRecordService.buy(transactionRecord);
        } catch (Exception e) {
            logger.error("TransactionRecord出异常了", e);
            return new Result(e);
        }

    }

    /**
     * 描述：平仓  交易状态 1建仓 2平仓 3挂单 4取消 5爆仓
     */
    @RequestMapping(value = "/sell")
    @ResponseBody
    public Result sell(TransactionRecord transactionRecord) throws Exception {
        try {
            if (Tools.isEmpty(transactionRecord.getLoginName(), transactionRecord.getId())) {
                return new Result(ResultEnum.PARAMETER_ERROR);
            }
            if(transactionRecord.getTransactionStatus()!=2){
                return new Result(ResultEnum.TRANSACTION_STASUA_ERROR);
            }
            return transactionRecordService.sell(transactionRecord);
        } catch (Exception e) {
            logger.error("TransactionRecord出异常了", e);
            return new Result(e);
        }

    }

    /**
     * 描述：挂单  交易状态 1建仓 2平仓 3挂单 4取消 5爆仓
     */
    @RequestMapping(value = "/reserve")
    @ResponseBody
    public Result reserve(TransactionRecord transactionRecord) throws Exception {
        try {
            if (Tools.isEmpty(transactionRecord.getLoginName(), transactionRecord.getLot(),
                    transactionRecord.getTransactionStatus(), transactionRecord.getSymbolCode(),
                    transactionRecord.getRansactionType(), transactionRecord.getUnitPrice(),
                    transactionRecord.getId(),transactionRecord.getEntryOrdersPrice(),transactionRecord.getErrorRange())) {
                return new Result(ResultEnum.PARAMETER_ERROR);
            }
            if(transactionRecord.getTransactionStatus()!=3){
                return new Result(ResultEnum.TRANSACTION_STASUA_ERROR);
            }
            return transactionRecordService.reserve(transactionRecord);
        } catch (Exception e) {
            logger.error("TransactionRecord出异常了", e);
            return new Result(e);
        }

    }

    /**
     * 描述：撤销挂单  交易状态 1建仓 2平仓 3挂单 4取消 5爆仓
     */
    @RequestMapping(value = "/cancelReserve")
    @ResponseBody
    public Result cancelReserve(TransactionRecord transactionRecord) throws Exception {
        try {
            if (Tools.isEmpty(transactionRecord.getId (),transactionRecord.getLoginName(), transactionRecord.getLot(),
                    transactionRecord.getTransactionStatus(), transactionRecord.getSymbolCode(),
                    transactionRecord.getRansactionType(), transactionRecord.getUnitPrice(),
                    transactionRecord.getEntryOrdersPrice(),transactionRecord.getErrorRange())) {
                return new Result(ResultEnum.PARAMETER_ERROR);
            }
            if(transactionRecord.getTransactionStatus()!=3){
                return new Result(ResultEnum.TRANSACTION_STASUA_ERROR);
            }
            return transactionRecordService.cancelReserve(transactionRecord);
        } catch (Exception e) {
            logger.error("TransactionRecord出异常了", e);
            return new Result(e);
        }

    }

}