package com.royal.admin.modular.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.constant.cache.Cache;
import com.royal.admin.core.common.constant.cache.CacheKey;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.Tools;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.entity.WritingsModel;
import com.royal.admin.modular.api.json.WritingsCount;
import com.royal.admin.modular.api.json.WritingsToTopicListJson;
import com.royal.admin.modular.api.mapper.QuestionPaperMapper;
import com.royal.admin.modular.api.mapper.WritingsMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class WritingsPaperService extends ServiceImpl<WritingsMapper, WritingsModel> {

    /**
     * 获取章节列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:05 PM
     */
    public Page<Map<String, Object>> selectPageList() {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectPageList(page);
    }

    /**
     * 根据活动ID获取章节列表及题目
     *
     * @param qId
     * @return
     */
    @Cacheable(value = Cache.TOPIC, key = "'" + CacheKey.QUESTION_PAPER + "'+#qId")
    public List<WritingsToTopicListJson> myListByQId(String qId) {
        return this.baseMapper.myListByQId(qId);
    }

    /**
     * 根据活动ID获取章节下的题目总数
     *
     * @param qId
     * @return
     */
    @Cacheable(value = Cache.TOPIC, key = "'" + CacheKey.QUESTION_PAPER_WRITINGS_COUNT + "'+#qId")
    public Map<String, Integer> myWritingsCountByQId(String qId) {
        List<WritingsCount> list = this.baseMapper.myWritingsCountByQId(qId);
        Map<String, Integer> map = new HashMap<>();
        for (WritingsCount writingsCount : list) {
            map.put(writingsCount.getId(), writingsCount.getTCount());
        }
        return map;
    }

    @Cacheable(value = Cache.TOPIC, key = "'" + CacheKey.WRITINGS_INFO + "'+#qId")
    public Map<String, Integer> findByQId(String qId) {
        QueryWrapper<WritingsModel> writingsModelQueryWrapper = new QueryWrapper<>();
        writingsModelQueryWrapper.eq("b_q_id", qId);
        writingsModelQueryWrapper.orderByAsc("b_sort_number");
        List<WritingsModel> list = this.list(writingsModelQueryWrapper);
        Map<String, Integer> map = new HashMap<>();
        for (WritingsModel writingsModel : list) {
            map.put(writingsModel.getId(),writingsModel.getUserCount());
        }
        return map;
    }
}
