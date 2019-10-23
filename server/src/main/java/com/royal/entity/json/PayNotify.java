package com.royal.entity.json;

public class PayNotify {
    //平台订单号
    private String out_trade_no;
    //商户订单号
    private String mer_trade_no;
    //支付时间
    private String payTime;
    //账单状态
    private String billStatus;
    //账单总金额
    private Integer totalAmount;
    //签名
    private String sign;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getMer_trade_no() {
        return mer_trade_no;
    }

    public void setMer_trade_no(String mer_trade_no) {
        this.mer_trade_no = mer_trade_no;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "PayNotify{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", mer_trade_no='" + mer_trade_no + '\'' +
                ", payTime='" + payTime + '\'' +
                ", billStatus='" + billStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", sign='" + sign + '\'' +
                '}';
    }
}
