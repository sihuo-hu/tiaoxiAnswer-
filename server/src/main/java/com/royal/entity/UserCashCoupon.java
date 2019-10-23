package com.royal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 描述：用户代金券模型
 *
 * @author Royal
 * @date 2019年05月23日 19:41:18
 */
@Table(name = "b_user_cash_coupon")
public class UserCashCoupon implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
     *
     */
    @Column(name = "cash_coupon_id")
    private String cashCouponId;
    /**
     *
     */
    @Column(name = "login_name")
    private String loginName;
    /**
     * 发放时间
     */
    @Column(name = "create_time")
    private String createTime;
    /**
     * 过期时间
     */
    @Column(name = "past_due_time")
    private String pastDueTime;
    /**
     * 发放说明
     */
    @Column(name = "grant_explain")
    private String grantExplain;

    /**
     * 金额
     */
    @Column(name = "cc_money")
    private BigDecimal ccMoney;

    /**
     * 生效时间
     */
    @Column(name = "start_time")
    private String startTime;

    /**
     * 使用后的订单ID
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 发放日志ID
     */
    @Column(name = "cash_coupon_push_log_id")
    private String cashCouponPushLogId;

    @Transient
    private String ccName;

    @Transient
    private String ccExplain;

    @Transient
    private String ccScopeNames;

    public BigDecimal getCcMoney() {
        return ccMoney;
    }

    public void setCcMoney(BigDecimal ccMoney) {
        this.ccMoney = ccMoney;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCashCouponId() {
        return this.cashCouponId;
    }

    public void setCashCouponId(String cashCouponId) {
        this.cashCouponId = cashCouponId;
    }


    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getPastDueTime() {
        return this.pastDueTime;
    }

    public void setPastDueTime(String pastDueTime) {
        this.pastDueTime = pastDueTime;
    }


    public String getGrantExplain() {
        return this.grantExplain;
    }

    public void setGrantExplain(String grantExplain) {
        this.grantExplain = grantExplain;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCashCouponPushLogId() {
        return cashCouponPushLogId;
    }

    public void setCashCouponPushLogId(String cashCouponPushLogId) {
        this.cashCouponPushLogId = cashCouponPushLogId;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcExplain() {
        return ccExplain;
    }

    public void setCcExplain(String ccExplain) {
        this.ccExplain = ccExplain;
    }

    public String getCcScopeNames() {
        return ccScopeNames;
    }

    public void setCcScopeNames(String ccScopeNames) {
        this.ccScopeNames = ccScopeNames;
    }
}