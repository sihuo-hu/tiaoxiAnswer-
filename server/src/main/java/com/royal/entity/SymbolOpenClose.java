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
* 描述：产品昨收今开模型
* @author Royal
* @date 2019年01月07日 12:32:36
*/
@Table(name="b_symbol_open_close")
public class SymbolOpenClose implements Serializable {

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
    @Column(name = "symbol_code")
    private String symbolCode;
    /**
    *
    */
    @Column(name = "today_open")
    private BigDecimal open;
    /**
    *
    */
    @Column(name = "yesterday_close")
    private BigDecimal close;
    /**
    *
    */
    @Column(name = "create_time")
    private String createTime;
    /**
     * 今日最低
     */
    @Column(name = "min_price")
    private BigDecimal minPrice;

    /**
     * 今日最高
     */
    @Column(name = "max_price")
    private BigDecimal maxPrice;

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


     public String getSymbolCode() {
        return this.symbolCode;
    }

    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }


     public BigDecimal getOpen() {
        return this.open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }


     public BigDecimal getClose() {
        return this.close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }


     public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


}