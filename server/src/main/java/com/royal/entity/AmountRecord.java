package com.royal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 描述：资金记录模型
 *
 * @author Royal
 * @date 2019年03月05日 13:28:09
 */
@Table(name = "b_amount_record")
@Data
public class AmountRecord implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private String id;
    /**
     *
     */
    @Column(name = "login_name")
    private String loginName;
    /**
     * 金额，单位美元
     */
    @Column(name = "money")
    private BigDecimal money;
    /**
     * 手续费
     */
    @Column(name = "commission")
    private BigDecimal commission;
    /**
     * 支付单号
     */
    @Column(name = "pay_no")
    private String payNo;
    /**
     * 支付状态  TRADE_SUCCESS:成功,
     * WAIT_BUYER_PAY:等待付款,
     * NEW_ORDER:新订单,
     * UNKNOWN:不明确的交易,
     * TRADE_CLOSED:交易关闭,
     * TRADE_REFUND:退款,
     * AUDIT_SUCCESS:审核成功
     * WAITING_PROCESS:等待处理
     * BEING_PROCESSED:处理中
     * FAILURE:失败
     */
    @Column(name = "pay_status")
    private String payStatus;
    /**
     * 交易类型 PAY-支付 WITHDRAWAL-提现
     */
    @Column(name = "amount_type")
    private String amountType;
    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private String payTime;
    /**
     * 下单时间
     */
    @Column(name = "order_time")
    private String orderTime;
    /**
     * 人民币金额
     */
    @Column(name = "rmb_money")
    private BigDecimal rmbMoney;
    /**
     * 支付说明
     */
    @Column(name = "pay_msg")
    private String payMsg;
    /**
     * 支付方式 1:微信，2:支付宝，3:云闪付
     */
    @Column(name = "pay_way")
    private String payWay;

    /**
     * 支付回调状态 1已收到 2未收到
     */
    @Column(name = "pay_notify_status")
    private Integer payNotifyStatus;

    /**
     * 发起支付金额
     */
    @Column(name = "sponsor_money")
    private BigDecimal sponsorMoney;

    /**
     * 汇率
     */
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Transient
    private String returnUrl;
    @Transient
    private String bankCard;
    /**
     * 开户行
     */
    @Transient
    private String bankOfDeposit;

    /**
     * 分行
     */
    @Transient
    private String branch;

    /**
     * 银行地址
     */
    @Transient
    private String bankAddress;
    @Transient
    private String payStatusStr;

}