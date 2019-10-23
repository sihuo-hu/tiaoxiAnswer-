package com.royal.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
* 描述：代金券模型
* @author Royal
* @date 2019年05月23日 19:35:09
*/
@Data
@Table(name="b_cash_coupon")
public class CashCoupon implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private String id;
    /**
    * 名称
    */
    @Column(name = "cc_name")
    private String ccName;
    /**
    * 金额
    */
    @Column(name = "cc_money")
    private BigDecimal ccMoney;
    /**
    *使用范围ID
    */
    @Column(name = "cc_scope_id")
    private String ccScopeId;
    /**
    *发放方式
    */
    @Column(name = "grant_mode")
    private String grantMode;
    /**
    *统一过期时间
    */
    @Column(name = "past_due_time")
    private String pastDueTime;
    /**
    *统一过期天数
    */
    @Column(name = "past_due_day")
    private Integer pastDueDay;
    /**
    *说明
    */
    @Column(name = "cc_explain")
    private String explain;

    /**
     * 过期方式 UNIFY统一失效 DIFFERENT按用户领取日期失效
     */
    @Column(name="past_due_mode")
    private String pastDueMode;
    /**
    *生效时间
    */
    @Column(name = "start_time")
    private String startTime;
    /**
    *
    */
    @Column(name = "create_time")
    private String createTime;
    /**
    *发放条件，自动发放才需要此处
    */
    @Column(name = "grant_condition")
    private String grantCondition;
    /**
    *达标金额
    */
    @Column(name = "amount_to_mark")
    private BigDecimal amountToMark;

    /**
     *ENABLE:启用,DISABLE:禁用
     */
    @Column(name="cc_status")
    private String ccStatus;

}