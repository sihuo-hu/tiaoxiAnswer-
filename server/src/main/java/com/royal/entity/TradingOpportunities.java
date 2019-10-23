package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：交易机会模型
* @author Royal
* @date 2019年02月14日 21:09:16
*/
@Table(name="b_trading_opportunities")
public class TradingOpportunities implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;
    /**
    *专家头像
    */
    @Column(name = "user_img")
    private String userImg;
    /**
    *专家名字
    */
    @Column(name = "user_name")
    private String userName;
    /**
    *产品名称
    */
    @Column(name = "symbol_name")
    private String symbolName;
    /**
    *操作方式
    */
    @Column(name = "operating_mode")
    private String operatingMode;
    /**
    *发布时间
    */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;
    /**
    *标题
    */
    @Column(name = "title")
    private String title;
    /**
    *标题下方核心主题
    */
    @Column(name = "theme_text")
    private String themeText;
    /**
    *买涨百分比
    */
    @Column(name = "rise_percentage")
    private Integer risePercentage;
    /**
    *基本分析
    */
    @Column(name = "foundation_analysis")
    private String foundationAnalysis;
    /**
    *技术面分析
    */
    @Column(name = "technological_analysis")
    private String technologicalAnalysis;
    /**
    *技术面分析图片
    */
    @Column(name = "technological_analysis_img")
    private String technologicalAnalysisImg;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


     public String getUserImg() {
        return this.userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }


     public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


     public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


     public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


     public String getThemeText() {
        return this.themeText;
    }

    public void setThemeText(String themeText) {
        this.themeText = themeText;
    }


    public Integer getRisePercentage() {
        return this.risePercentage;
    }

    public void setRisePercentage(Integer risePercentage) {
        this.risePercentage = risePercentage;
    }


     public String getFoundationAnalysis() {
        return this.foundationAnalysis;
    }

    public void setFoundationAnalysis(String foundationAnalysis) {
        this.foundationAnalysis = foundationAnalysis;
    }


     public String getTechnologicalAnalysis() {
        return this.technologicalAnalysis;
    }

    public void setTechnologicalAnalysis(String technologicalAnalysis) {
        this.technologicalAnalysis = technologicalAnalysis;
    }


     public String getTechnologicalAnalysisImg() {
        return this.technologicalAnalysisImg;
    }

    public void setTechnologicalAnalysisImg(String technologicalAnalysisImg) {
        this.technologicalAnalysisImg = technologicalAnalysisImg;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getOperatingMode() {
        return operatingMode;
    }

    public void setOperatingMode(String operatingMode) {
        this.operatingMode = operatingMode;
    }
}