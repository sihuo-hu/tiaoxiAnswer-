package com.royal.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：用户资金模型
* @author Royal
* @date 2018年12月13日 17:16:02
*/
@Table(name="b_user_amount")
public class UserAmount implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
    *用户手机号
    */
    @Column(name = "login_name")
    private String loginName;
    /**
    *总充值金额
    */
    @Column(name = "recharge_amount")
    private BigDecimal rechargeAmount;
    /**
    *余额
    */
    @Column(name = "balance")
    private BigDecimal balance;
    /**
    *赠送金额
    */
    @Column(name = "grant_amount")
    private BigDecimal grantAmount;
    /**
    *已提现金额
    */
    @Column(name = "withdraw_amount")
    private BigDecimal withdrawAmount;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


     public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


     public BigDecimal getRechargeAmount() {
        return this.rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }


     public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


     public BigDecimal getGrantAmount() {
        return this.grantAmount;
    }

    public void setGrantAmount(BigDecimal grantAmount) {
        this.grantAmount = grantAmount;
    }


     public BigDecimal getWithdrawAmount() {
        return this.withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

}