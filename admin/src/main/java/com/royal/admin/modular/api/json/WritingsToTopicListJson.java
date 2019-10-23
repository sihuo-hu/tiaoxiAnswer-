package com.royal.admin.modular.api.json;

import com.royal.admin.modular.api.entity.TopicModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 章节题目信息
 */
@Data
public class WritingsToTopicListJson implements Serializable {
    private String id;

    private String wName;

    private Integer tId;

    private String title;

    private String options;

    private String answer;

    private Boolean conclusion;

}
