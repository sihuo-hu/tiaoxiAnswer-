package com.royal.admin.modular.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("b_topic")
@Data
public class TopicModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * AUTO 数据库ID自增
     * INPUT 用户输入ID
     * ID_WORKER 全局唯一ID，Long类型的主键
     * ID_WORKER_STR 字符串全局唯一ID
     * UUID 全局唯一ID，UUID类型的主键
     * NONE 该类型为未设置主键类型
     */
    @TableId(value = "b_id", type = IdType.AUTO)
    private Integer id;

    @TableField("b_title")
    private String title;

    @TableField("b_options")
    private String options;

    @TableField("b_answer")
    private String answer;

    @TableField("b_q_id")
    private String qId;

    @TableField("b_w_id")
    private String wId;

    @TableField(exist = false)
    private String wName;

    @TableField(exist = false)
    private String qName;
}
