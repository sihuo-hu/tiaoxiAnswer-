package com.royal.admin.modular.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("b_writings")
@Data
public class WritingsModel implements Serializable {

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
    @TableId(value = "b_id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 章节名称
     */
    @TableField("b_name")
    private String wName;

    /**
     * 需要答对的人数
     */
    @TableField("b_user_count")
    private Integer userCount;

    /**
     * 创建时间
     */
    @TableField(value = "b_create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 试卷ID
     */
    @TableField("b_q_id")
    private String qId;
    /**
     * 排序号
     */
    @TableField("b_sort_number")
    private Integer sortNumber;

    @TableField("b_finish_user_count")
    private Integer finishUserCount;

    @TableField("b_finish_time")
    private Date finishTime;

    @TableField(exist = false)
    private String qTitle;
}
