package com.royal.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：用户模型
* @author Royal
* @date 2018年12月04日 14:19:32
*/
@Table(name="b_user")
public class User implements Serializable {

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
    *密码
    */
    @Column(name = "password")
    private String password;
    /**
    *创建时间
    */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
    *用户名
    */
    @Column(name = "user_name")
    private String userName;
    /**
     * 公钥
     */
    @Column(name="public_key")
    private String publicKey;

    /**
     * 昵称
     */
    @Column(name="nickname")
    private String nickname;

    /**
     * 头像
     */
    @Column(name="user_img")
    private String userImg;
    /**
     * 性别 0女 1男
     */
    @Column(name="gender")
    private String gender;
    /**
     * 出生日期
     */
    @Column(name="birthdate")
    private String birthdate;
    /**
     * 身份证号
     */
    @Column(name="id_number")
    private String idNumber;
    /**
     * 身份证正面图片地址
     */
    @Column(name="card_front")
    private String cardFront;
    /**
     * 身份证反面图片地址
     */
    @Column(name="card_reverse")
    private String cardReverse;

    /**
     * 审核状态 DONT_SUBMIT:未提交,NO_AUDIT:未审核,VERIFIED:审核通过,REJECTED:审核不通过
     */
    @Column(name="audit_status")
    private String auditStatus;
    /**
     * 银行卡号
     */
    @Column(name="bank_card")
    private String bankCard;
    /**
     * 开户行
     */
    @Column(name="bank_of_deposit")
    private String bankOfDeposit;

    /**
     * 分行
     */
    @Column(name="branch")
    private String branch;

    /**
     * 银行地址
     */
    @Column(name="bank_address")
    private String bankAddress;

    @Column(name="ry_token")
    private String ryToken;

    /**
     * 渠道
     */
    @Column(name="ditch")
    private String ditch;

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankOfDeposit() {
        return bankOfDeposit;
    }

    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
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


     public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


     public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

     public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardReverse() {
        return cardReverse;
    }

    public void setCardReverse(String cardReverse) {
        this.cardReverse = cardReverse;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBranch() {
        return branch;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public String getRyToken() {
        return ryToken;
    }

    public void setRyToken(String ryToken) {
        this.ryToken = ryToken;
    }

    public String getDitch() {
        return ditch;
    }

    public void setDitch(String ditch) {
        this.ditch = ditch;
    }
}