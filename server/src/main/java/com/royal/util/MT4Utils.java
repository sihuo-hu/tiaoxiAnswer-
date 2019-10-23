
package com.royal.util;


import com.royal.entity.SymbolInfo;
import com.royal.entity.SymbolOpenClose;
import com.royal.entity.SymbolRecord;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.enums.SymbolEnum;
import com.royal.entity.json.Result;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MT4Utils {

    public static Map<String, SymbolRecord> map = new HashMap<String, SymbolRecord>();

    public static SymbolRecord getSymbolRecord(String code) {
        return map.get(code);
    }

    public static void setSymbolRecord(String code, SymbolRecord symbolRecord) {
        MT4Utils.map.put(code, symbolRecord);
    }

    public static Map<String, String> saveTimeMap = new HashMap<String, String>();

    public static SymbolRecord StringToSymbolRecord(String msgSymbolRecord) {
        SymbolRecord symbolRecord = new SymbolRecord();
        if (msgSymbolRecord.length() > 0) {
            JSONObject job = JSONUtils.toJSONObject(msgSymbolRecord);
            String timestamp = job.getString("timestamp");
            String price = job.getString("price");
            String code = job.getString("code");
            Date date = new Date();
            date.setTime(Long.parseLong(timestamp) * 1000);
            String symbolCode = SymbolEnum.getValueByKey(code);
            if (Tools.isEmpty(symbolCode)) {
                return null;
            }
            symbolRecord.setSymbolCode(symbolCode);
            symbolRecord.setCreateTime(new Date());
            symbolRecord.setPrice(new BigDecimal(price));
            symbolRecord.setMarketTime(date);
        }
        return symbolRecord;
    }

    public static boolean isOpen(SymbolInfo symbolInfo) {
        Date date = new Date();
        String dateFormat = DateUtils.getFormatDate(date);
        if (symbolInfo.getSymbolCode().equals("XAUUSD") || symbolInfo.getSymbolCode().equals("XAGUSD") || symbolInfo.getSymbolCode().equals("WTI") || symbolInfo.getSymbolCode().equals("CAD")) {
            String startStr = "06:00:00";
            String endStr = "07:00:00";
            Date start = DateUtils.StringToDate(dateFormat + " " + endStr);
            Date end = DateUtils.StringToDate(dateFormat + " " + startStr);
            if (date.getTime() < DateUtils.getWeekDate(1, endStr).getTime() || date.getTime() > DateUtils.getWeekDate(6, startStr).getTime()) {
                return false;
            }else{
                if (DateUtils.belongCalendar(date, start, end)) {
                    return false;
                }
            }
            //外汇 06:10:00-06:00:00
        } else {
            String startStr = "06:10:00";
            String endStr = "06:00:00";
            Date start = DateUtils.StringToDate(dateFormat + " " + endStr);
            Date end = DateUtils.StringToDate(dateFormat + " " + startStr);
            if ( date.getTime()< DateUtils.getWeekDate(1, startStr).getTime() || date.getTime() > DateUtils.getWeekDate(6, endStr).getTime()) {
                return false;
            }else{
                if (DateUtils.belongCalendar(date, start, end)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setSymbolOpenClose(SymbolOpenClose symbolOpenClose, SymbolInfo info){
        if (symbolOpenClose != null && !Tools.isEmpty(symbolOpenClose.getId())) {
            info.setClose(symbolOpenClose.getClose());
            info.setOpen(symbolOpenClose.getOpen());
            info.setMaxPrice(symbolOpenClose.getMaxPrice());
            info.setMinPrice(symbolOpenClose.getMinPrice());
            if (Tools.isEmpty(MT4Utils.getSymbolRecord(info.getSymbolCode()))) {
                info.setPresentPrice(symbolOpenClose.getClose());
            } else {
                info.setPresentPrice(MT4Utils.getSymbolRecord(info.getSymbolCode()).getPrice());
            }
        }
    }


    public static void main(String[] args) {
        Date date = new Date();
        String dateFormat = DateUtils.getFormatDate(date);
        String startStr = "06:00:00";
        String endStr = "07:00:00";
        Date start = DateUtils.StringToDate(dateFormat + " " + endStr);
        Date end = DateUtils.StringToDate(dateFormat + " " + startStr);
        System.out.println(DateUtils.getWeekDate(1, endStr));
        System.out.println(DateUtils.getWeekDate(6, startStr));
        if (DateUtils.getWeekDate(1, endStr).getTime() < date.getTime() && DateUtils.getWeekDate(6, startStr).getTime() > date.getTime()) {
            if (DateUtils.belongCalendar(date, start, end)) {
                System.out.println(111111111);
            }
        }
    }
}
