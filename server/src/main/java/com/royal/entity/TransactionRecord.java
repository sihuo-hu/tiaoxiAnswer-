package com.royal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 描述：交易记录表模型
 *
 * @author Royal
 * @date 2018年12月13日 22:47:22
 */
@Table(name = "b_transaction_record")
public class TransactionRecord implements Serializable {

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
    @Column(name = "login_name")
    private String loginName;
    /**
     * 产品编码
     */
    @Column(name = "symbol_code")
    private String symbolCode;
    /**
     * 建仓时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 手数
     */
    @Column(name = "lot")
    private Integer lot;
    /**
     * 建仓成本
     */
    @Column(name = "unit_price")
    private Integer unitPrice;
    /**
     * 金额
     */
    @Column(name = "money")
    private Integer money;
    /**
     * 过夜费
     */
    @Column(name = "overnight_fee")
    private BigDecimal overnightFee;
    /**
     * 手续费
     */
    @Column(name = "commission_charges")
    private BigDecimal commissionCharges;
    /**
     * 交易状态 1建仓 2平仓 3挂单 4取消
     */
    @Column(name = "transaction_status")
    private Integer transactionStatus;

    /**
     * 平仓状态 loss止损平仓 profit止盈平仓  user手动平仓  overnight不过夜平仓
     */
    @Column(name="sell_status")
    private String sellStatus;
    /**
     * 当时的指数（价格）
     */
    @Column(name = "exponent")
    private BigDecimal exponent;
    /**
     * 平仓价格
     */
    @Column(name = "close_out_price")
    private BigDecimal closeOutPrice;
    /**
     * 挂单价格
     */
    @Column(name = "entry_orders_price")
    private BigDecimal entryOrdersPrice;
    /**
     * 挂单浮动点位
     */
    @Column(name = "error_range")
    private Integer errorRange;
    /**
     * 挂单最低价
     */
    @Column(name = "entry_orders_strat_price")
    private BigDecimal entryOrdersStratPrice;
    /**
     * 挂单最高价
     */
    @Column(name = "entry_orders_end_price")
    private BigDecimal entryOrdersEndPrice;
    /**
     * 平仓时间
     */
    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 止损指数（价格）
     */
    @Column(name = "stop_loss_exponent")
    private BigDecimal stopLossExponent;
    /**
     * 止盈指数（价格）
     */
    @Column(name = "stop_profit_exponent")
    private BigDecimal stopProfitExponent;
    /**
     * 止损点数
     */
    @Column(name = "stop_loss_count")
    private Integer stopLossCount;
    /**
     * 止盈点数
     */
    @Column(name = "stop_profit_count")
    private Integer stopProfitCount;

    /**
     * 1买涨 2买跌
     */
    @Column(name = "ransaction_type")
    private Integer ransactionType;

    /**
     * 1过夜 2不过夜
     */
    @Column(name = "is_overnight")
    private Integer isOvernight;
    /**
     * 盈利
     */
    @Column(name = "profit")
    private BigDecimal profit;
    /**
     * 挂单时间
     */
    @Column(name = "entry_orders_time")
    private Date entryOrdersTime;

    /**
     * 数据锁的时间
     */
    @Column(name="lock_time")
    private String lockTime;

    /**
     * 代金券ID
     */
    @Column(name="user_cash_coupon_id")
    private String userCashCouponId;
    /**
     * 毛利
     */
    @Column(name="gross_profit")
    private BigDecimal grossProfit;

    @Transient
    private String symbolName;
    @Transient
    private BigDecimal presentPrice;

    /**
     * 数量1 如1盎司 20欧元
     */
    @Column(name="quantity")
    private Integer quantity;

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

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


    public String getSymbolCode() {
        return this.symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }


    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getMoney() {
        return this.money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }


    public Integer getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setTransactionStatus(Integer transactionStatus) {
        this.transactionStatus = transactionStatus;
    }


    public BigDecimal getExponent() {
        return this.exponent;
    }

    public void setExponent(BigDecimal exponent) {
        this.exponent = exponent;
    }


    public BigDecimal getCloseOutPrice() {
        return this.closeOutPrice;
    }

    public void setCloseOutPrice(BigDecimal closeOutPrice) {
        this.closeOutPrice = closeOutPrice;
    }


    public BigDecimal getEntryOrdersPrice() {
        return this.entryOrdersPrice;
    }

    public void setEntryOrdersPrice(BigDecimal entryOrdersPrice) {
        this.entryOrdersPrice = entryOrdersPrice;
    }


    public Integer getErrorRange() {
        return this.errorRange;
    }

    public void setErrorRange(Integer errorRange) {
        this.errorRange = errorRange;
    }


    public BigDecimal getEntryOrdersStratPrice() {
        return this.entryOrdersStratPrice;
    }

    public void setEntryOrdersStratPrice(BigDecimal entryOrdersStratPrice) {
        this.entryOrdersStratPrice = entryOrdersStratPrice;
    }


    public BigDecimal getEntryOrdersEndPrice() {
        return this.entryOrdersEndPrice;
    }

    public void setEntryOrdersEndPrice(BigDecimal entryOrdersEndPrice) {
        this.entryOrdersEndPrice = entryOrdersEndPrice;
    }


    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public BigDecimal getStopLossExponent() {
        return this.stopLossExponent;
    }

    public void setStopLossExponent(BigDecimal stopLossExponent) {
        this.stopLossExponent = stopLossExponent;
    }


    public BigDecimal getStopProfitExponent() {
        return this.stopProfitExponent;
    }

    public void setStopProfitExponent(BigDecimal stopProfitExponent) {
        this.stopProfitExponent = stopProfitExponent;
    }


    public Integer getStopLossCount() {
        return this.stopLossCount;
    }

    public void setStopLossCount(Integer stopLossCount) {
        this.stopLossCount = stopLossCount;
    }

    public Integer getStopProfitCount() {
        return this.stopProfitCount;
    }

    public void setStopProfitCount(Integer stopProfitCount) {
        this.stopProfitCount = stopProfitCount;
    }

    public Integer getRansactionType() {
        return ransactionType;
    }

    public void setRansactionType(Integer ransactionType) {
        this.ransactionType = ransactionType;
    }

    public Integer getLot() {
        return lot;
    }

    public void setLot(Integer lot) {
        this.lot = lot;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getOvernightFee() {
        return overnightFee;
    }

    public void setOvernightFee(BigDecimal overnightFee) {
        this.overnightFee = overnightFee;
    }

    public BigDecimal getCommissionCharges() {
        return commissionCharges;
    }

    public void setCommissionCharges(BigDecimal commissionCharges) {
        this.commissionCharges = commissionCharges;
    }

    public Integer getIsOvernight() {
        return isOvernight;
    }

    public void setIsOvernight(Integer isOvernight) {
        this.isOvernight = isOvernight;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Date getEntryOrdersTime() {
        return entryOrdersTime;
    }

    public void setEntryOrdersTime(Date entryOrdersTime) {
        this.entryOrdersTime = entryOrdersTime;
    }

    public BigDecimal getPresentPrice() {
        return presentPrice;
    }

    public void setPresentPrice(BigDecimal presentPrice) {
        this.presentPrice = presentPrice;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getUserCashCouponId() {
        return userCashCouponId;
    }

    public void setUserCashCouponId(String userCashCouponId) {
        this.userCashCouponId = userCashCouponId;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }
}