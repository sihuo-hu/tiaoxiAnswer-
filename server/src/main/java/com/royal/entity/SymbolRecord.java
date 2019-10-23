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
* 描述：产品价格记录模型
* @author Royal
* @date 2018年12月25日 21:45:16
*/
@Table(name="b_symbol_record")
public class SymbolRecord implements Serializable {

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
    *价格
    */
    @Column(name = "price")
    private BigDecimal price;
    /**
    *时间
    */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     *行情时间
     */
    @Column(name = "market_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date marketTime;

    public Date getMarketTime() {
        return marketTime;
    }

    public void setMarketTime(Date marketTime) {
        this.marketTime = marketTime;
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


     public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


     public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SymbolRecord{" +
                "id=" + id +
                ", symbolCode='" + symbolCode + '\'' +
                ", price=" + price +
                ", createTime=" + createTime +
                ", marketTime=" + marketTime +
                '}';
    }
}