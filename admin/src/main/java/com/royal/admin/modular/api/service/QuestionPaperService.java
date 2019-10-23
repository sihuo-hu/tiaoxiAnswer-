package com.royal.admin.modular.api.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.constant.cache.Cache;
import com.royal.admin.core.common.constant.cache.CacheKey;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.task.DynamicSchedulingUtil;
import com.royal.admin.core.util.CacheUtil;
import com.royal.admin.core.util.DateUtils;
import com.royal.admin.core.util.QuestionPaperUtil;
import com.royal.admin.core.util.Tools;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.mapper.QuestionPaperMapper;
import com.royal.admin.modular.system.entity.Notice;
import com.royal.admin.modular.system.mapper.NoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class QuestionPaperService extends ServiceImpl<QuestionPaperMapper, QuestionPaperModel> {
    private static final Logger log = LoggerFactory.getLogger(QuestionPaperService.class);
    /**
     * 获取试卷列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:05 PM
     */
    public Page<Map<String, Object>> selectPageList(String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage();
        QueryWrapper<QuestionPaperModel> questionPaperModelWrapper = new QueryWrapper<QuestionPaperModel>();
        if (!Tools.isEmpty(beginTime, endTime)) {
            questionPaperModelWrapper.between("b_start_time", beginTime, endTime);
        }
        questionPaperModelWrapper.orderByDesc("b_create_time");
        return (Page<Map<String, Object>>) this.baseMapper.selectMapsPage(page, questionPaperModelWrapper);
    }

    /**
     * 获取最近一条记录
     *
     * @return
     */
    public QuestionPaperModel getNearest() {
        QueryWrapper<QuestionPaperModel> questionPaperModelWrapper = new QueryWrapper<>();
        questionPaperModelWrapper.ge("b_end_time", new Date()).orderByDesc("b_start_time").last("limit 1");
        return this.baseMapper.selectOne(questionPaperModelWrapper);
    }

    /**
     * 获取最近一条记录
     *
     * @return
     */
    public QuestionPaperModel getSchedulingNearest() {
        QueryWrapper<QuestionPaperModel> questionPaperModelWrapper = new QueryWrapper<>();
        questionPaperModelWrapper.ge("b_end_time", new Date(new Date().getTime()-50000)).orderByDesc("b_start_time").last("limit 1");
        return this.baseMapper.selectOne(questionPaperModelWrapper);
    }

    public void lottery(QuestionPaperModel questionPaperModel) {
        try {
            if (questionPaperModel != null && Tools.notEmpty(questionPaperModel.getId()) && Tools.isEmpty(questionPaperModel.getWinner())) {
                //如果有一边先胜利则直接返回
                String winner = CacheUtil.get(Cache.CONSTANT, CacheKey.WINNER) == null ? "" : CacheUtil.get(Cache.CONSTANT, CacheKey.WINNER);
                int southCount = CacheUtil.get("south", CacheKey.USER_COUNT) == null ? 0 : CacheUtil.get("south", CacheKey.USER_COUNT);
                int northCount = CacheUtil.get("north", CacheKey.USER_COUNT) == null ? 0 : CacheUtil.get("north", CacheKey.USER_COUNT);
                questionPaperModel.setParticipants(southCount + northCount);
                if (Tools.isEmpty(winner)) {
                    //看哪边点亮的章节多
                    String south = CacheUtil.get("south", CacheKey.WRITINGS_RIGHT) == null ? "" : CacheUtil.get("south", CacheKey.WRITINGS_RIGHT);
                    String north = CacheUtil.get("north", CacheKey.WRITINGS_RIGHT) == null ? "" : CacheUtil.get("north", CacheKey.WRITINGS_RIGHT);
                    if (south.split(",").length > north.split(",").length) {
                        winner = "south";
                    } else if (south.split(",").length == north.split(",").length) {
                        if (southCount > northCount) {
                            winner = "south";
                        } else {
                            winner = "north";
                        }
                    } else {
                        winner = "north";
                    }
                }
                Set<String> set = null;
                if (winner.equals("south")) {
                    questionPaperModel.setWinnerParticipants(southCount);
                    set = QuestionPaperUtil.southUserIdSet;
                } else if (winner.equals("north")) {
                    questionPaperModel.setWinnerParticipants(northCount);
                    set = QuestionPaperUtil.northUserIdSet;
                }
                List<String> s = new ArrayList<>(set);
                String value = "";
                int index = questionPaperModel.getAwardCount();
                if (s.size() < questionPaperModel.getAwardCount()) {
                    index = s.size();
                }
                for (int i = 1; i <= index; i++) {
                    int j = Tools.getRandom(0, s.size());
                    if (value.indexOf(s.get(j)) >= 0) {
                        i--;
                    } else {
                        value = value + s.get(j) + "--" + QuestionPaperUtil.titleNames[i - 1] + ",";
                    }
                }
                if (value.indexOf(",") > 0) {
                    value.substring(0, value.length() - 1);
                }
                questionPaperModel.setAwardUser(value);
                questionPaperModel.setWinner(winner);
                this.updateById(questionPaperModel);

                //清除所有缓存
                CacheUtil.removeAll(Cache.CONSTANT);
                CacheUtil.removeAll(Cache.TOPIC);
                CacheUtil.removeAll(Cache.SOUTH);
                CacheUtil.removeAll(Cache.NORTH);
                QuestionPaperUtil.northUserIdSet = new CopyOnWriteArraySet<String>();
                QuestionPaperUtil.southUserIdSet = new CopyOnWriteArraySet<String>();
                QuestionPaperUtil.userIdSet = new CopyOnWriteArraySet<String>();
            }
        }catch (Exception e){
            log.error("计算结果出错：",e);
        }
    }
}
