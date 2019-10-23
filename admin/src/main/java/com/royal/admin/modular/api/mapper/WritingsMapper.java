package com.royal.admin.modular.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.modular.api.entity.QuestionPaperModel;
import com.royal.admin.modular.api.entity.WritingsModel;
import com.royal.admin.modular.api.json.WritingsCount;
import com.royal.admin.modular.api.json.WritingsToTopicListJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface WritingsMapper extends BaseMapper<WritingsModel> {

    Page<Map<String, Object>> selectPageList(@Param("page") Page page);

    List<WritingsToTopicListJson> myListByQId(@Param("qId") String qId);

    List<WritingsCount> myWritingsCountByQId(@Param("qId") String qId);
}
