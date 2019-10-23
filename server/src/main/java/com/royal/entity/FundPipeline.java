package com.royal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.royal.util.DateUtils;
import com.royal.util.Tools;

/**
 * 描述：资金流水日志模型
 *
 * @author Royal
 * @date 2019年06月18日 16:36:40
 */
@Table(name = "b_fund_pipeline")
public class FundPipeline implements Serializable {

    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private String id;
    /**
     * 流水类型 pay 充值 withdraw 提现 buy 建仓 sell 平仓
     */
    @Column(name = "pipeline_type")
    private String pipelineType;
    /**
     *
     */
    @Column(name = "money")
    private BigDecimal money;
    /**
     *
     */
    @Column(name = "balance")
    private BigDecimal balance;
    /**
     *
     */
    @Column(name = "login_name")
    private String loginName;
    /**
     *
     */
    @Column(name = "order_id")
    private String orderId;
    /**
     *
     */
    @Column(name = "create_time")
    private String createTime;
    /**
     * 收入或支出
     */
    @Column(name = "income_or_expenditure")
    private String incomeOrExpenditure;

    public FundPipeline() {
        super();
    }

    public FundPipeline(String pipelineType, BigDecimal money, BigDecimal balance, String loginName, String orderId, String incomeOrExpenditure) {
        this.id = Tools.getRandomCode(32,7);
        this.pipelineType = pipelineType;
        this.money = money;
        this.balance = balance;
        this.loginName = loginName;
        this.orderId = orderId;
        this.createTime = DateUtils.getCurrDateTimeStr();
        this.incomeOrExpenditure = incomeOrExpenditure;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPipelineType() {
        return this.pipelineType;
    }

    public void setPipelineType(String pipelineType) {
        this.pipelineType = pipelineType;
    }


    public BigDecimal getMoney() {
        return this.money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getIncomeOrExpenditure() {
        return this.incomeOrExpenditure;
    }

    public void setIncomeOrExpenditure(String incomeOrExpenditure) {
        this.incomeOrExpenditure = incomeOrExpenditure;
    }


}