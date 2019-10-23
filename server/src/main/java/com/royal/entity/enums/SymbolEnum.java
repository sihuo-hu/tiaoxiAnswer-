package com.royal.entity.enums;

public enum SymbolEnum {
    HJ ("XAUUSD", "XAUUSD"),
    BY ("XAGUSD", "XAGUSD"),
    MYY ("US_OIL", "WTI"),
    LDT ("COPPER", "CAD"),
    OYMY ("EURUSD", "EURUSD"),
    AYMY ("AUDUSD", "AUDUSD"),
    MYRY ("USDJPY", "USDJPY"),
    YBMY ("GBPUSD", "GBPUSD"),
    MYJY ("USDCAD", "USDCAD"),
    MYRSFL ("USDCHF", "USDCHF"),
    XXLYMY ("NZDUSD", "NZDUSD");

    private SymbolEnum(String key, String value) {
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
        for (SymbolEnum value : SymbolEnum.values ()) {
            if(value.getKey ().equals (key)){
                return value.getValue ();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println (SymbolEnum.getValueByKey ("US_OIL"));
    }
}
