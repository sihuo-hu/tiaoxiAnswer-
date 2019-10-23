package com.royal.admin.modular.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.Tools;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.entity.TopicModel;
import com.royal.admin.modular.api.mapper.QuestionPaperMapper;
import com.royal.admin.modular.api.mapper.TopicMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class TopicService extends ServiceImpl<TopicMapper, TopicModel> {

    /**
     * 获取题库列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:05 PM
     */
    public Page<Map<String, Object>> selectPageList() {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectMyList(page);
    }
}
