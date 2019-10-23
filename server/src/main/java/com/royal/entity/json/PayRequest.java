package com.royal.entity.json;

public class PayRequest {

    //商户号
    private String mer_id;
    //商户订单号
    private String mer_trade_no;
   // 金额 单位分
    private String amt;
    //通知地址
    private String notify_url;
    //支付类型 1:微信，2:支付宝，3:云闪付
    private String pay_type;
    //业务应用类型 微信H5支付必填。
    //用于苹果app应用里值为IOS_SDK ；用于安卓app 应用里值为AND_SDK；用于手机网站，值为IOS_WAP 或AND_WAP
    private String sceneType;
    //应用名称 微信H5支付必填。
    //用于苹或安卓app 应用中，传分别 对应在 AppStore和安卓分发市场中的应用名（如：全民付）；用于手机网站，传对应的网站名（如：银联商务官网）
//    private String merAppName;
    //应用标识 微信H5支付必填。
    //用于苹果或安卓 app 应用中，苹果传 IOS 应用唯一标识(如： com.tencent.wzryIOS )
    //安卓传包名 (如： com.tencent.tmgp.sgame)
    //如果是用于手机网站 ，传首页 URL 地址 , (如： https://m.jd.com ) ，支付宝H5支付参数无效
    private String merAppId;
    //回跳地址
    private String returnUrl;
    //签名
    private String sign;

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    public String getMer_trade_no() {
        return mer_trade_no;
    }

    public void setMer_trade_no(String mer_trade_no) {
        this.mer_trade_no = mer_trade_no;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

//    public String getMerAppName() {
//        return merAppName;
//    }

//    public void setMerAppName(String merAppName) {
//        this.merAppName = merAppName;
//    }

    public String getMerAppId() {
        return merAppId;
    }

    public void setMerAppId(String merAppId) {
        this.merAppId = merAppId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
