package com.royal.service;

import com.github.pagehelper.PageInfo;
import com.royal.entity.SymbolRecord;
import com.royal.entity.TransactionRecord;
import com.royal.entity.json.PageData;
import com.royal.entity.json.Result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述：交易记录表 服务实现层接口
 *
 * @author Royal
 * @date 2018年12月13日 22:47:22
 */
public interface ITransactionRecordService extends BaseService<TransactionRecord> {


    Result buy(TransactionRecord transactionRecord);

    Result sell(TransactionRecord transactionRecord);

    Result constraintSell(TransactionRecord transactionRecord, BigDecimal price,String sellStatus);

    PageInfo<TransactionRecord> getMyPage(TransactionRecord transactionRecord, PageData pd);

    Result reserve(TransactionRecord transactionRecord);

    Result reserveToBuy(TransactionRecord transactionRecord, BigDecimal price);

    void computeOvernightFee();

    List<TransactionRecord> findByTransactionStatus(List<Integer> transactionStatusList);

    void deleteByEntryOrdersTime(Date dateBeforeOrAfter);

    void noDaylightSavingTime();

    Result cancelReserve(TransactionRecord transactionRecord);

    List<TransactionRecord> selectReserveByPrice(SymbolRecord symbolRecord);

    Result overnight(TransactionRecord tr);

    Result notOvernight(TransactionRecord tr);

    List<TransactionRecord> findListByCodeAndPrice(String lockTime);

    String setTransactionRecordLockTime(String symbolCode, BigDecimal price);
}