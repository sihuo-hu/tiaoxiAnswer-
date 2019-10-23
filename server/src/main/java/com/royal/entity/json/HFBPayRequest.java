package com.royal.entity.json;

public class HFBPayRequest {

    /**
     * 应付金额（单位分 订单金额-优惠金额）
     */
    private Integer cope_pay_amount;
    /**
     * 商户open_id
     */
    private String merchant_open_id;
    /**
     * 商户订单号(不可重复)
     */
    private String merchant_order_number;

    /**
     * 通知地址
     */
    private String notify_url;
    /**
     * 支付方式1支付宝2微信
     */
    private Integer pay_type;

    /**
     * 商户支付通道标示
     */
    private String pay_wap_mark;
    /**
     * 签名（将sign以外所有参数按照第一个字符的键值ASCII码递增排序,组合成“参数=参数值”的格式，并且把这些参数用&字符连接起来,此时生成的字符串为待签名字符串。MD5签名的商户需要将key的值拼接在字符串后面，调用MD5算法生成sign）
     */
    private String sign;

    /**
     * 订单名称
     */
    private String subject;

    /**
     * 当前时间戳
     */
    private String timestamp;

    public Integer getCope_pay_amount() {
        return cope_pay_amount;
    }

    public void setCope_pay_amount(Integer cope_pay_amount) {
        this.cope_pay_amount = cope_pay_amount;
    }

    public String getMerchant_open_id() {
        return merchant_open_id;
    }

    public void setMerchant_open_id(String merchant_open_id) {
        this.merchant_open_id = merchant_open_id;
    }

    public String getMerchant_order_number() {
        return merchant_order_number;
    }

    public void setMerchant_order_number(String merchant_order_number) {
        this.merchant_order_number = merchant_order_number;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_wap_mark() {
        return pay_wap_mark;
    }

    public void setPay_wap_mark(String pay_wap_mark) {
        this.pay_wap_mark = pay_wap_mark;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
