package com.royal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 描述：交易品种模型
 *
 * @author Royal
 * @date 2018年12月12日 17:05:27
 */
@Data
@Table(name = "b_symbol_info")
public class SymbolInfo implements Serializable {

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
    @Column(name = "symbol_name")
    private String symbolName;
    /**
     * 1启用 2停用
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 类别 1外汇 2贵金属 3原油 4全球指数
     */
    @Column(name = "symbol_type")
    private Integer symbolType;

    /**
     * 价格1
     */
    @Column(name = "unit_price_one")
    private Integer unitPriceOne;
    /**
     * 价格2
     */
    @Column(name = "unit_price_two")
    private Integer unitPriceTwo;
    /**
     * 价格3
     */
    @Column(name = "unit_price_three")
    private Integer unitPriceThree;
    /**
     * 数量1 如1盎司 20欧元
     */
    @Column(name = "quantity_one")
    private Integer quantityOne;
    /**
     * 数量2 如1盎司 20欧元
     */
    @Column(name = "quantity_two")
    private Integer quantityTwo;
    /**
     * 数量3 如1盎司 20欧元
     */
    @Column(name = "quantity_three")
    private Integer quantityThree;

    /**
     * 每份数量对应的手续费
     */
    @Column(name = "quantity_commission_charges_one")
    private BigDecimal quantityCommissionChargesOne;
    /**
     * 每份数量对应的手续费
     */
    @Column(name = "quantity_commission_charges_two")
    private BigDecimal quantityCommissionChargesTwo;
    /**
     * 每份数量对应的手续费
     */
    @Column(name = "quantity_commission_charges_three")
    private BigDecimal quantityCommissionChargesThree;
    /**
     * 每个数量的过夜费
     */
    @Column(name = "quantity_overnight_fee_one")
    private BigDecimal quantityOvernightFeeOne;
    /**
     * 每个数量的过夜费
     */
    @Column(name = "quantity_overnight_fee_two")
    private BigDecimal quantityOvernightFeeTwo;
    /**
     * 每个数量的过夜费
     */
    @Column(name = "quantity_overnight_fee_three")
    private BigDecimal quantityOvernightFeeThree;
    /**
     * 每个数量对应的波动价格
     */
    @Column(name = "quantity_price_fluctuation_one")
    private BigDecimal quantityPriceFluctuationOne;
    /**
     * 每个数量对应的波动价格
     */
    @Column(name = "quantity_price_fluctuation_two")
    private BigDecimal quantityPriceFluctuationTwo;
    /**
     * 每个数量对应的波动价格
     */
    @Column(name = "quantity_price_fluctuation_three")
    private BigDecimal quantityPriceFluctuationThree;

    /**
     * 挂单点位对应价格
     */
    @Column(name = "entry_orders")
    private BigDecimal entryOrders;

    /**
     * 1展示 2不展示
     */
    @Column(name = "symbol_show")
    private Integer symbolShow;

    /**
     * 今日最低
     */
    @Transient
    private BigDecimal minPrice;

    /**
     * 今日最高
     */
    @Transient
    private BigDecimal maxPrice;

    /**
     * 今开
     */
    @Transient
    private BigDecimal open;
    /**
     * 昨收
     */
    @Transient
    private BigDecimal close;

    @Transient
    private BigDecimal presentPrice;

    @Transient
    private boolean isTrading;

}