package com.royal.task;

//import com.royal.commen.socket.NettyClient;

import com.royal.commen.socket.WebsocketClient;
import com.royal.entity.SymbolOpenClose;
import com.royal.entity.SymbolRecord;
import com.royal.service.IAmountRecordService;
import com.royal.service.ISymbolOpenCloseService;
import com.royal.service.ITransactionRecordService;
import com.royal.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author royal
 * @ClassName: SchedulingConfig
 * @Description: TODO(定时任务Controller)
 * @date 2018年6月15日 下午2:30:33
 */
@Configuration
@EnableScheduling
public class SchedulingUtil {
    @Autowired
    private ITransactionRecordService transactionRecordService;
    @Autowired
    private ISymbolOpenCloseService symbolOpenCloseService;
    @Autowired
    private IAmountRecordService amountRecordService;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler ();
        taskScheduler.setPoolSize (10);
        return taskScheduler;
    }

    private static final Logger log = LoggerFactory.getLogger (SchedulingUtil.class);


    /**
     * 过夜费
     */
    @Scheduled(cron = "1 0 6 * * ?")
    public void daylightSavingTime() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        transactionRecordService.computeOvernightFee ();
        log.info (methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr ());

    }

    /**
     * 不过夜强制交易
     */
    @Scheduled(cron = "7 0 6 * * TUE-SAT")
    public void noDaylightSavingTime() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        transactionRecordService.noDaylightSavingTime ();
        log.info (methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr ());

    }

    /**
     * 记录昨收价格
     */
    @Scheduled(cron = "10 0 6 * * TUE-SAT")
    public void close() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        Collection<SymbolRecord> list = MT4Utils.map.values ();
        if (list!=null&&list.size ()>0) {
            for (SymbolRecord symbolRecord : list) {
                SymbolOpenClose symbolOpenClose = new SymbolOpenClose ();
                symbolOpenClose.setSymbolCode (symbolRecord.getSymbolCode ());
                symbolOpenClose.setClose (symbolRecord.getPrice ());
                if(DateUtils.getWeekOfDate (null)==6){
                    symbolOpenClose.setCreateTime (DateUtils.getFormatDate (DateUtils.getDateBeforeOrAfter (2)));
                }else{
                    symbolOpenClose.setCreateTime (DateUtils.getFormatDate (new Date ()));
                }
                symbolOpenCloseService.add (symbolOpenClose);
            }
        }
        log.info (methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr ());
    }

    /**
     * 清除今开数据
     */
    @Scheduled(cron = "10 3 6 * * ?")
    public void purgingMap() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        OpenCloseMaxMinUtils.purgingMap ();
        OpenCloseMaxMinUtils.purgingMaxAndMin ();
        log.info (methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr ());
    }

    /**
     * 检测获取报价接口是否正常
     */
    @Scheduled(cron = "30 1/10 * * * ?")
    public void detectionWebSocket() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        try {
            Thread.sleep (60000);
        } catch (InterruptedException e) {
            log.error (methodName + "定时任务异常======》当前时间:" + DateUtils.getCurrDateTimeStr (), e);
        }
        if (WebsocketClient.client == null || WebsocketClient.client.isClosed ()) {
            WebsocketClient.open ();
        }
        log.info (methodName + "定时任务结束======》当前时间:" + DateUtils.getCurrDateTimeStr ());

    }


    /**
     * 每个小时清除一次websocket的地址
     */
    @Scheduled(cron = "1 30 * * * ?")
    public void delSignMap() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        WebSocketUtil.delSet ();
        log.info (methodName + "定时任务结束======》当前时间:" + DateUtils.getCurrDateTimeStr ());
    }

    /**
     * 每个小时清除一次挂单
     */
    @Scheduled(cron = "15 0 * * * ?")
    public void eliminateReserve() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        transactionRecordService.deleteByEntryOrdersTime (DateUtils.getDateBeforeOrAfter (-3));
        log.info (methodName + "定时任务结束======》当前时间:" + DateUtils.getCurrDateTimeStr ());
    }

    /**
     * 每个小时清除一次充值失败的数据
     */
    @Scheduled(cron = "1 1 * * * ?")
    public void payError() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        amountRecordService.updatePayStatus (DateUtils.getCurrDateTimeStr());
        log.info (methodName + "定时任务结束======》当前时间:" + DateUtils.getCurrDateTimeStr ());
    }

    /**
     * 每个15分钟查询一次订单状态
     */
    @Scheduled(cron = "1 1/15 * * * ?")
    public void selectPayStatus() {
        String methodName = new Throwable ().getStackTrace ()[0].getMethodName ();
        log.info (methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr ());
        amountRecordService.selectPayStatus ();
        log.info (methodName + "定时任务结束======》当前时间:" + DateUtils.getCurrDateTimeStr ());
    }


}
