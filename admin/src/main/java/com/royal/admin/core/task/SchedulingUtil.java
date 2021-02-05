//package com.royal.admin.core.task;
//
////import com.royal.commen.socket.NettyClient;
//
//import com.royal.admin.core.common.constant.cache.Cache;
//import com.royal.admin.core.common.constant.cache.CacheKey;
//import com.royal.admin.core.util.CacheUtil;
//import com.royal.admin.core.util.DateUtils;
//import com.royal.admin.core.util.QuestionPaperUtil;
//import com.royal.admin.core.util.Tools;
//import com.royal.admin.modular.api.entity.QuestionPaperModel;
//import com.royal.admin.modular.api.service.QuestionPaperService;
//import com.royal.admin.modular.api.service.WritingsPaperService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//
//import java.util.Collection;
//import java.util.Date;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * @author royal
// * @ClassName: SchedulingConfig
// * @Description: TODO(定时任务Controller)
// * @date 2018年6月15日 下午2:30:33
// */
//@Configuration
//@EnableScheduling
//public class SchedulingUtil {
//    @Autowired
//    private QuestionPaperService questionPaperService;
//    @Autowired
//    private WritingsPaperService writingsService;
//
//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(10);
//        return taskScheduler;
//    }
//
//
//
//
//    /**
//     * 结果清算
//     */
////    @Scheduled(cron = "1/1 * * * * ?")
//    public void resultsClearing() {
//        String methodName = new Throwable().getStackTrace()[0].getMethodName();
//        log.info(methodName + "定时任务开始======》当前时间：" + DateUtils.getCurrDateTimeStr());
//        QuestionPaperModel questionPaperModel = questionPaperService.getSchedulingNearest();
//        String date = DateUtils.getCurrDateTimeStr();
//        if (DateUtils.getFormatDateTime(questionPaperModel.getEndTime()).equals(date)) {
//            //如果有一边先胜利则直接返回
//            String winner = CacheUtil.get(Cache.CONSTANT, CacheKey.WINNER) == null ? "" : CacheUtil.get(Cache.CONSTANT, CacheKey.WINNER);
//            int southCount = CacheUtil.get("south", CacheKey.USER_COUNT) == null ? 0 : CacheUtil.get("south", CacheKey.USER_COUNT);
//            int northCount = CacheUtil.get("north", CacheKey.USER_COUNT) == null ? 0 : CacheUtil.get("north", CacheKey.USER_COUNT);
//            questionPaperModel.setParticipants(southCount + northCount);
//            if (Tools.isEmpty(winner)) {
//                //看哪边点亮的章节多
//                String south = CacheUtil.get("south", CacheKey.WRITINGS_RIGHT) == null ? "" : CacheUtil.get("south", CacheKey.WRITINGS_RIGHT);
//                String north = CacheUtil.get("north", CacheKey.WRITINGS_RIGHT) == null ? "" : CacheUtil.get("north", CacheKey.WRITINGS_RIGHT);
//                if (south.split(",").length > north.split(",").length) {
//                    winner = "south";
//                } else if (south.split(",").length == north.split(",").length) {
//                    winner = "south";
//                } else {
//                    winner = "north";
//                }
//            }
//            Set<String> set = null;
//            if (winner.equals("south")) {
//                questionPaperModel.setWinnerParticipants(southCount);
//                set = QuestionPaperUtil.southUserIdSet;
//            } else if (winner.equals("north")) {
//                questionPaperModel.setWinnerParticipants(northCount);
//                set = QuestionPaperUtil.northUserIdSet;
//            }
//            String[] s = (String[]) set.toArray();
//            String value = "";
//            for (int i = 1; i <= questionPaperModel.getAwardCount(); i++) {
//                int j = Tools.getRandom(0, s.length);
//                if (value.indexOf(s[j]) >= 0) {
//                    i--;
//                } else {
//                    value = value + s[j] + ",";
//                }
//            }
//            if (value.indexOf(",") > 0) {
//                value.substring(0, value.length() - 1);
//            }
//            questionPaperModel.setAwardUser(value);
//            questionPaperModel.setWinner(winner);
//            questionPaperService.updateById(questionPaperModel);
//
//            //清除所有缓存
//            CacheUtil.removeAll(Cache.CONSTANT);
//            CacheUtil.removeAll(Cache.TOPIC);
//            CacheUtil.removeAll(Cache.SOUTH);
//            CacheUtil.removeAll(Cache.NORTH);
//            QuestionPaperUtil.northUserIdSet = new CopyOnWriteArraySet<String>();
//            QuestionPaperUtil.southUserIdSet = new CopyOnWriteArraySet<String>();
//            QuestionPaperUtil.userIdSet = new CopyOnWriteArraySet<String>();
//        }
//        log.info(methodName + "定时任务结束======》当前时间：" + DateUtils.getCurrDateTimeStr());
//
//    }
//
//
//}
