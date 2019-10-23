package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：验证码模型
* @author Royal
* @date 2018年12月04日 13:52:12
*/
@Table(name="p_verification_code")
public class VerificationCode implements Serializable {

    /**
    *
    */
   	@Id
	@GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Integer id;
    /**
    *登录名
    */
    @Column(name = "login_name")
    private String loginName;
    /**
    *验证码
    */
    @Column(name = "verification_code")
    private String verificationCode;
    /**
    *
    */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
    *验证码类别 1注册
    */
    @Column(name = "code_type")
    private Integer codeType;
    /**
    *
    */
    @Column(name = "expiration_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expirationTime;


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


     public String getVerificationCode() {
        return this.verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


     public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getCodeType() {
        return this.codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }


     public Date getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }


}