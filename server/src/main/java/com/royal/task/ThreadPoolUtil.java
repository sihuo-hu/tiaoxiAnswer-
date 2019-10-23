package com.royal.task;

import com.royal.commen.socket.WebSocketServer;
import com.royal.entity.SymbolInfo;
import com.royal.entity.SymbolOpenClose;
import com.royal.entity.SymbolRecord;
import com.royal.entity.TransactionRecord;
import com.royal.entity.enums.SellStatusEnum;
import com.royal.service.ISymbolOpenCloseService;
import com.royal.service.ITransactionRecordService;
import com.royal.service.impl.TransactionRecordServiceImpl;
import com.royal.util.MT4Utils;
import com.royal.util.OpenCloseMaxMinUtils;
import com.royal.util.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static final Logger log = LoggerFactory.getLogger(TransactionRecordServiceImpl.class);
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    /**
     * 止损止盈处理
     *
     * @param symbolRecord
     */
    public static void transactionsTrigger(final SymbolRecord symbolRecord) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                SymbolInfo symbolInfo = new SymbolInfo();
                symbolInfo.setSymbolCode(symbolRecord.getSymbolCode());
                if (MT4Utils.isOpen(symbolInfo)) {
                    ITransactionRecordService transactionRecordService = (ITransactionRecordService) SpringContextUtils.getBean(
                            "transactionRecordServiceImpl");
                    String lockTime = transactionRecordService.setTransactionRecordLockTime(symbolRecord.getSymbolCode(),
                            symbolRecord.getPrice());
                    List<TransactionRecord> list = transactionRecordService.findListByCodeAndPrice(lockTime);
                    if (list != null) {
                        for (TransactionRecord transactionRecord : list) {
                            try {
                                String sellStatus = "";
                                if(transactionRecord.getRansactionType()==1){
                                    if(transactionRecord.getStopLossExponent().compareTo(symbolRecord.getPrice())>=0){
                                        sellStatus= SellStatusEnum.LOSS.getKey();
                                    }else{
                                        sellStatus= SellStatusEnum.PROFIT.getKey();
                                    }
                                }else{
                                    if(transactionRecord.getStopLossExponent().compareTo(symbolRecord.getPrice())<=0){
                                        sellStatus= SellStatusEnum.LOSS.getKey();
                                    }else{
                                        sellStatus= SellStatusEnum.PROFIT.getKey();
                                    }
                                }
                                transactionRecordService.constraintSell(transactionRecord, symbolRecord.getPrice(),sellStatus);
                            } catch (Exception e) {
                                transactionRecord.setLockTime(null);
                                transactionRecordService.update(transactionRecord);
                                log.error("止损止盈处理出错：", e);
                            }


                        }
                    }
                }
            }
        });
    }

    /**
     * 发送给客户端实时报价
     *
     * @param symbolRecord
     */
    public static void sendPrice(final SymbolRecord symbolRecord) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                WebSocketServer.sendPrice(symbolRecord);
            }
        });
    }

    /**
     * 记录最高最低价
     *
     * @param symbolRecord
     */
    public static void maxAndMin(final SymbolRecord symbolRecord) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                boolean maxUpdate = false;
                boolean minUpdate = false;
                if (OpenCloseMaxMinUtils.setMax(symbolRecord.getSymbolCode(), symbolRecord.getPrice())) {
                    maxUpdate = true;
                }
                if (OpenCloseMaxMinUtils.setMin(symbolRecord.getSymbolCode(), symbolRecord.getPrice())) {
                    minUpdate = true;
                }
                if (maxUpdate || minUpdate) {
                    ISymbolOpenCloseService symbolOpenCloseService = (ISymbolOpenCloseService) SpringContextUtils.getBean(
                            "symbolOpenCloseServiceImpl");
                    SymbolOpenClose symbolOpenClose =
                            symbolOpenCloseService.getBySymbolCodeAndDate(symbolRecord.getSymbolCode());
                    if (symbolOpenClose != null) {
                        if (maxUpdate) {
                            symbolOpenClose.setMaxPrice(symbolRecord.getPrice());
                        }
                        if (minUpdate) {
                            symbolOpenClose.setMinPrice(symbolRecord.getPrice());
                        }
                        symbolOpenCloseService.update(symbolOpenClose);
                    }
                }
            }
        });
    }

    /**
     * 记录今开
     *
     * @param symbolRecord
     */
    public static void open(final SymbolRecord symbolRecord) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if (!OpenCloseMaxMinUtils.validationMap(symbolRecord.getSymbolCode())) {
                    ISymbolOpenCloseService symbolOpenCloseService = (ISymbolOpenCloseService) SpringContextUtils.getBean(
                            "symbolOpenCloseServiceImpl");
                    SymbolOpenClose symbolOpenClose =
                            symbolOpenCloseService.getBySymbolCodeAndDate(symbolRecord.getSymbolCode());
                    if (symbolOpenClose != null && symbolOpenClose.getOpen() == null) {
                        symbolOpenClose.setOpen(symbolRecord.getPrice());
                        symbolOpenCloseService.update(symbolOpenClose);
                    }
                    OpenCloseMaxMinUtils.setMap(symbolRecord.getSymbolCode(), symbolRecord.getPrice());
                }
            }
        });
    }

    public static BlockingQueue publicBoxQueue = new LinkedBlockingQueue(200);
    public static Map<Integer, Object> map = new ConcurrentHashMap<Integer, Object>();

    /**
     * 存挂单处理
     *
     * @param symbolRecord
     */
    public static void entryOrdersSave(final SymbolRecord symbolRecord) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                SymbolInfo symbolInfo = new SymbolInfo();
                symbolInfo.setSymbolCode(symbolRecord.getSymbolCode());
                if (MT4Utils.isOpen(symbolInfo)) {
                    ITransactionRecordService transactionRecordService =
                            (ITransactionRecordService) SpringContextUtils.getBean(
                                    "transactionRecordServiceImpl");
                    List<TransactionRecord> list = transactionRecordService.selectReserveByPrice(symbolRecord);
                    if (list != null && list.size() > 0) {
                        for (TransactionRecord transactionRecord : list) {
                            if (!map.containsKey(transactionRecord.getId())) {
                                transactionRecord.setExponent(symbolRecord.getPrice());
                                boolean b = publicBoxQueue.offer(transactionRecord);
                                if (b) {
                                    map.put(transactionRecord.getId(), transactionRecord);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 挂单处理
     */
    public static void entryOrders() {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                ITransactionRecordService transactionRecordService =
                        (ITransactionRecordService) SpringContextUtils.getBean(
                                "transactionRecordServiceImpl");
                while (true) {
                    try {
                        TransactionRecord transactionRecord = (TransactionRecord) publicBoxQueue.take();
                        transactionRecordService.reserveToBuy(transactionRecord, transactionRecord.getExponent());
                        map.remove(transactionRecord.getId());
                    } catch (Exception e) {
                        log.error("挂单转建仓失败:", e);
                    }
                }
            }
        });
    }


}
