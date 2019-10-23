/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.royal.admin.modular.api.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.core.common.constant.cache.Cache;
import com.royal.admin.core.common.constant.cache.CacheKey;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.CacheUtil;
import com.royal.admin.core.util.DateUtils;
import com.royal.admin.core.util.QuestionPaperUtil;
import com.royal.admin.core.util.Tools;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.entity.WritingsModel;
import com.royal.admin.modular.api.json.CampStatus;
import com.royal.admin.modular.api.json.QuestionPaperStatus;
import com.royal.admin.modular.api.json.Results;
import com.royal.admin.modular.api.json.WritingsToTopicListJson;
import com.royal.admin.modular.api.service.QuestionPaperService;
import com.royal.admin.modular.api.service.WritingsPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 试卷控制器
 *
 * @author fengshuonan
 * @Date 2017-05-09 23:02:21
 */
@Controller
@RequestMapping("/api/questionPaper")
public class QuestionPaperAPPController extends BaseController {


    @Autowired
    private QuestionPaperService questionPaperService;
    @Autowired
    private WritingsPaperService writingsService;


    /**
     * 获取最近的活动
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/getNearest")
    @ResponseBody
    public Object getNearest() {
        QuestionPaperModel questionPaperModel = questionPaperService.getNearest();
        String userId = "";
        do {
            userId = Tools.getRandomCode(8, 0);
        } while (QuestionPaperUtil.userIdSet.contains(userId));
        QuestionPaperUtil.userIdSet.add(userId);
        //无活动
        if (questionPaperModel == null || Tools.isEmpty(questionPaperModel.getId())) {
            return ResponseData.error(101, "目前尚无活动！");
            //未开始
        } else if (DateUtils.compare_date(questionPaperModel.getStartTime(), new Date()) && !DateUtils.isBetween(questionPaperModel.getStartTime(), QuestionPaperUtil.COUNT_DOWN_TIME)) {
            return ResponseData.error(102, "活动尚未开始，请耐心等待！");
            //倒计时
        } else if (DateUtils.isBetween(questionPaperModel.getStartTime(), QuestionPaperUtil.COUNT_DOWN_TIME)) {
            return ResponseData.success(200, userId, questionPaperModel);
            //活动中
        } else if (DateUtils.belongCalendar(new Date(), questionPaperModel.getStartTime(), questionPaperModel.getEndTime())) {
            return ResponseData.success(200, userId, questionPaperModel);
        } else {
            return ResponseData.error(500, "系统繁忙，请稍后再试！");
        }
    }

    /**
     * 加入阵营
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/addCamp")
    @ResponseBody
    public Object addCamp(@RequestParam(value = "camp", required = true) String camp,
                          @RequestParam(value = "userId", required = true) String userId) {
        if ("south".equals(camp)) {
            int all = CacheUtil.get(camp, CacheKey.USER_COUNT) == null ? 0 : CacheUtil.get(camp, CacheKey.USER_COUNT);
            all++;
            CacheUtil.put(camp, CacheKey.USER_COUNT, all);
            QuestionPaperUtil.southUserIdSet.add(userId);
        } else if ("north".equals(camp)) {
            int all = CacheUtil.get(camp, CacheKey.USER_COUNT) == null ? 0 : CacheUtil.get(camp, CacheKey.USER_COUNT);
            all++;
            CacheUtil.put(camp, CacheKey.USER_COUNT, all);
            QuestionPaperUtil.northUserIdSet.add(userId);
        } else {
            return ResponseData.error(103, "参数不合法！");
        }
        return ResponseData.success(camp + userId);
    }

    /**
     * 获取章节列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/getWritingsList")
    @ResponseBody
    public Object getWritingsList(String qId) {
        QueryWrapper<WritingsModel> writingsModelQueryWrapper = new QueryWrapper<>();
        writingsModelQueryWrapper.eq("b_q_id", qId);
        writingsModelQueryWrapper.orderByAsc("b_sort_number");
        List<WritingsModel> list = writingsService.list(writingsModelQueryWrapper);
        if (list != null && list.size() > 0) {
            return ResponseData.success(list);
        } else {
            return ResponseData.error(104, "未找到对应题库");
        }
    }

    /**
     * 获取题目
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/getTopic")
    @ResponseBody
    public Object getTopic(@RequestParam(value = "uId", required = true) String uId,
                           @RequestParam(value = "qId", required = true) String qId,
                           @RequestParam(value = "tId", required = false) Integer tId,
                           @RequestParam(value = "answer", required = false) String answer) {
        //根据活动ID获取所有章节的题目
        List<WritingsToTopicListJson> writingsToTopicListJsonList = writingsService.myListByQId(qId);
        Map<String, Integer> wMap = writingsService.myWritingsCountByQId(qId);
        Map<String, Integer> userCountMap = writingsService.findByQId(qId);
        String camp = uId.substring(0, 5);
        String userId = uId.substring(5, uId.length());
        int all = CacheUtil.get(camp, userId + CacheKey.ALL) == null ? 0 : CacheUtil.get(camp, userId + CacheKey.ALL);
        int index = 0;
        WritingsToTopicListJson writingsToTopicListJson = null;
        //获取题目
        if (all < writingsToTopicListJsonList.size()) {
            writingsToTopicListJson = writingsToTopicListJsonList.get(all);
        }
        //提交答案
        if (all != 0 && all <= writingsToTopicListJsonList.size()) {
            WritingsToTopicListJson w = writingsToTopicListJsonList.get(all - 1);
            if (all == writingsToTopicListJsonList.size()) {
                writingsToTopicListJson = new WritingsToTopicListJson();
                index = 1;
            }
            //判断答案是否正确
            if (answer.equals(w.getAnswer())) {
                writingsToTopicListJson.setConclusion(true);
            } else {
                writingsToTopicListJson.setConclusion(false);
            }
            //判断此章节是否已经答题完毕
            //如果是最后一题时，则直接认为章节结束，否则则判断下题的章节ID和本题是否一致
            if (all == writingsToTopicListJsonList.size() || !writingsToTopicListJson.getId().equals(w.getId())) {
                int wRight = CacheUtil.get(camp, userId + w.getId() + CacheKey.RIGHT) == null ? 0 : CacheUtil.get(camp, userId + w.getId() + CacheKey.RIGHT);
                if (writingsToTopicListJson.getConclusion()) {
                    wRight++;
                    CacheUtil.put(camp, userId + w.getId() + CacheKey.RIGHT, wRight);
                }
                //判断是否全对，全对则阵营章节正确数加1
                if (wRight == wMap.get(w.getId())) {
                    int i = CacheUtil.get(camp, w.getId() + CacheKey.ALL_RIGHT) == null ? 0 : CacheUtil.get(camp, w.getId() + CacheKey.ALL_RIGHT);
                    i++;
                    CacheUtil.put(camp, w.getId() + CacheKey.ALL_RIGHT, i);
                    //如果需要答对的人数和已答对的人数相等，则记录该章节
                    if (userCountMap.get(w.getId()) == i) {
                        String a = CacheUtil.get(camp, CacheKey.WRITINGS_RIGHT) == null ? "" : CacheUtil.get(camp, CacheKey.WRITINGS_RIGHT);
                        a = a + w.getId() + ",";
                        CacheUtil.put(camp, CacheKey.WRITINGS_RIGHT, a);
                        if (a.substring(0, a.length() - 1).split(",").length == wMap.size()) {
                            String winner = CacheUtil.get(Cache.CONSTANT, CacheKey.WINNER) == null ? "" : CacheUtil.get(Cache.CONSTANT, CacheKey.WINNER);
                            if (!Tools.notEmpty(winner)) {
                                CacheUtil.put(Cache.CONSTANT, CacheKey.WINNER, camp);
                                QuestionPaperModel questionPaperModel = questionPaperService.getById(qId);
                                //答题结束，计算结果
                                questionPaperService.lottery(questionPaperModel);
                            }
                        }
                    }
                }
            }


        } else if (all == 0) {
            index = 0;
        } else {
            index = 2;
        }
        all++;
        CacheUtil.put(camp, userId + CacheKey.ALL, all);
        if (index == 1) {
            return ResponseData.success(300, "您已全部答题完毕，请等待最终结果！", writingsToTopicListJson);
        } else if (index == 2) {
            return ResponseData.error("您已全部答题完毕，请等待最终结果！");
        } else {
            return ResponseData.success(writingsToTopicListJson);
        }

    }

    /**
     * 获取答题结果
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/getResult")
    @ResponseBody
    public Object getResult(@RequestParam(value = "uId", required = true) String uId,
                            @RequestParam(value = "qId", required = true) String qId) {
        String camp = uId.substring(0, 5);
        String userId = uId.substring(5, uId.length());
        QuestionPaperModel questionPaperModel = questionPaperService.getById(qId);
        Results results = new Results();
        if (questionPaperModel.getWinner().equals(camp)) {
            results.setVictory(true);
            if (questionPaperModel.getAwardUser().indexOf(userId) >= 0) {
                results.setTitle(true);
                String[] titleNames = questionPaperModel.getAwardUser().split(",");
                for (String titleName : titleNames) {
                    if (titleName.indexOf(userId) >= 0) {
                        results.setTitleName(titleName.split("-")[1]);
                    }
                }
            }
        }
        return ResponseData.success(results);
    }

    /**
     * 获取答题结果
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/result")
    @ResponseBody
    public Object result(@RequestParam(value = "qId", required = true) String qId) {
        QuestionPaperModel questionPaperModel = questionPaperService.getById(qId);
        return ResponseData.success(questionPaperModel);
    }

    /**
     * 获取每个阵营当前状态
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/getStatus")
    @ResponseBody
    public Object getStatus(@RequestParam(value = "qId", required = true) String qId) {
        Map<String, Integer> userCountMap = writingsService.findByQId(qId);
        QuestionPaperStatus questionPaperStatus = new QuestionPaperStatus();
        List<CampStatus> southCampStatusList = new ArrayList<>();
        List<CampStatus> northCampStatusList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : userCountMap.entrySet()) {
            int south = CacheUtil.get("south", entry.getKey() + CacheKey.ALL_RIGHT) == null ? 0 : CacheUtil.get("south", entry.getKey() + CacheKey.ALL_RIGHT);
            int north = CacheUtil.get("north", entry.getKey() + CacheKey.ALL_RIGHT) == null ? 0 : CacheUtil.get("north", entry.getKey() + CacheKey.ALL_RIGHT);
            CampStatus southCampStatus = new CampStatus();
            CampStatus northCampStatus = new CampStatus();
            southCampStatus.setRightUserCount(south);
            southCampStatus.setUserCount(entry.getValue());
            southCampStatus.setWId(entry.getKey());
            southCampStatusList.add(southCampStatus);
            northCampStatus.setRightUserCount(north);
            northCampStatus.setUserCount(entry.getValue());
            northCampStatus.setWId(entry.getKey());
            northCampStatusList.add(northCampStatus);
        }
        questionPaperStatus.setNorth(northCampStatusList);
        questionPaperStatus.setSouth(southCampStatusList);

        return ResponseData.success(questionPaperStatus);
    }


    public static void main(String[] args) {
        String s = "2019-09-14 15:19:00";
        System.out.println(DateUtils.getCron(DateUtils.StringToDate(s)));
    }

}
