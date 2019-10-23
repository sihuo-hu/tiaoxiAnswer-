package com.royal.admin.modular.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.system.entity.Notice;
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
public interface QuestionPaperMapper extends BaseMapper<QuestionPaperModel> {

    /**
     * 获取试卷列表
     */
    Page<Map<String, Object>> list(@Param("page") Page page, @Param("condition") String condition);

}
