package com.royal.admin.modular.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("b_question_paper")
@Data
public class QuestionPaperModel implements Serializable {

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
     * 开始时间
     */
    @TableField("b_start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("b_end_time")
    private Date endTime;

    /**
     * 标题
     */
    @TableField("b_title")
    private String title;

    /**
     * 创建时间
     */
    @TableField(value = "b_create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 获胜方
     */
    @TableField(value = "b_winner")
    private String winner;

    /**
     * 获胜方参与人数
     */
    @TableField(value = "b_winner_participants")
    private int winnerParticipants;
    /**
     * 总参与人数
     */
    @TableField(value = "b_participants")
    private int participants;

    /**
     * 获奖人数
     */
    @TableField(value = "b_award_count")
    private int awardCount;

    /**
     * 获奖人员
     */
    @TableField(value = "b_award_user")
    private String awardUser;

}
