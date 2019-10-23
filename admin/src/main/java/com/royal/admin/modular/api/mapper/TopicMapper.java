package com.royal.admin.modular.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.entity.TopicModel;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 试卷表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface TopicMapper extends BaseMapper<TopicModel> {


    Page<Map<String, Object>> selectMyList(Page page);
}
