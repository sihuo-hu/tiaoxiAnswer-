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
 * 描述：产品历史记录模型
 *
 * @author Royal
 * @date 2019年07月01日 16:44:20
 */
@Data
@Table(name = "r_symbol_record_m1")
public class SymbolRecordM1 implements Serializable {
    public SymbolRecordM1(SymbolRecordM1 symbolRecordM1) {
        new SymbolRecordM1(symbolRecordM1.marketTime, symbolRecordM1.symbolCode, symbolRecordM1.symbolOpen, symbolRecordM1.getSymbolClose(), symbolRecordM1.getSymbolMin(), symbolRecordM1.getSymbolMax(), symbolRecordM1.symbolTurnover);
    }

    public SymbolRecordM1(String marketTime, String symbolCode, BigDecimal symbolOpen, BigDecimal symbolClose, BigDecimal symbolMin, BigDecimal symbolMax, Integer symbolTurnover) {
        this.marketTime = marketTime;
        this.symbolCode = symbolCode;
        this.symbolOpen = symbolOpen;
        this.symbolClose = symbolClose;
        this.symbolMin = symbolMin;
        this.symbolMax = symbolMax;
        this.symbolTurnover = symbolTurnover;
    }

    public SymbolRecordM1() {
        super();
    }
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
     * 行情时间
     */
    @Column(name = "market_time")
    private String marketTime;
    /**
     *
     */
    @Column(name = "symbol_code")
    private String symbolCode;
    /**
     * 开
     */
    @Column(name = "symbol_open")
    private BigDecimal symbolOpen;
    /**
     * 收
     */
    @Column(name = "symbol_close")
    private BigDecimal symbolClose;
    /**
     * 低
     */
    @Column(name = "symbol_min")
    private BigDecimal symbolMin;
    /**
     * 高
     */
    @Column(name = "symbol_max")
    private BigDecimal symbolMax;
    /**
     * 成交量
     */
    @Column(name = "symbol_turnover")
    private Integer symbolTurnover;


}