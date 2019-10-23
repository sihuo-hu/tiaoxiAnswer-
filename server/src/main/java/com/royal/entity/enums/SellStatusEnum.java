package com.royal.entity.enums;

public enum SellStatusEnum {
    USER ("USER", "手动平仓"),
    LOSS ("LOSS", "止损平仓"),
    PROFIT("PROFIT", "止盈平仓"),
    OVERNIGHT ("OVERNIGHT", "不过夜平仓");

    private SellStatusEnum(String key, String value) {
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
        for (SellStatusEnum value : SellStatusEnum.values ()) {
            if(value.getKey ().equals (key)){
                return value.getValue ();
            }
        }
        return null;
    }

}
