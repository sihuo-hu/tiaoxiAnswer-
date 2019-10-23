package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：自选产品模型
* @author Royal
* @date 2019年01月02日 12:48:23
*/
@Table(name="b_user_symbol")
public class UserSymbol implements Serializable {

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
    *
    */
    @Column(name = "symbol_code")
    private String symbolCode;


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


}