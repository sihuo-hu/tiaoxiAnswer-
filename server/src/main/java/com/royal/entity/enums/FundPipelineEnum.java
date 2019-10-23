package com.royal.entity.enums;

public enum FundPipelineEnum {
    INCOME("INCOME", "收入"),
    EXPENDITURE("EXPENDITURE", "支出"),
    PAY("PAY", "充值"),
    WITHDRAW("WITHDRAW", "提现"),
    BUY("BUY", "建仓"),
    SELL("SELL", "平仓");

    private FundPipelineEnum(String key, String value) {
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
}
