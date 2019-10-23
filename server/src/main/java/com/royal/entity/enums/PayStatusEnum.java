package com.royal.entity.enums;

public enum PayStatusEnum {
    TRADE_SUCCESS ("TRADE_SUCCESS", "成功"),
    WAIT_BUYER_PAY ("WAIT_BUYER_PAY", "等待付款"),
    NEW_ORDER ("NEW_ORDER", "新订单"),
    UNKNOWN ("UNKNOWN", "不明确的交易"),
    TRADE_CLOSED ("TRADE_CLOSED", "交易关闭"),
    TRADE_REFUND ("TRADE_REFUND", "退款"),
    AUDIT_SUCCESS ("AUDIT_SUCCESS", "审核成功"),
    WAITING_PROCESS ("WAITING_PROCESS", "等待处理"),
    BEING_PROCESSED ("BEING_PROCESSED", "处理中"),
    FAILURE ("FAILURE", "失败");

    private PayStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByKey(String key){
        for (PayStatusEnum value : PayStatusEnum.values ()) {
            if(value.getKey ().equals (key)){
                return value.getValue ();
            }
        }
        return null;
    }
}
