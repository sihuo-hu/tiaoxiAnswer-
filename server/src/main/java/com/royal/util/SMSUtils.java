package com.royal.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

public class SMSUtils {
    private static final String ACCOUNT = "0al3yw";
    private static final String PASSWORD = "DU90y8g8";
    private static final String SEND_SMS = "http://118.178.138.170/msg/HttpBatchSendSM";

    public static String sendSMS(String phone, String code) {
        String date = DateUtils.getFormatDate(new Date(), "yyyyMMddHHmmss");
        Map<String,String> map = new HashMap<String,String>();
        map.put("account", ACCOUNT);
        map.put("ts", date);
        map.put("pswd", MD5.md5(ACCOUNT + PASSWORD + date));
        map.put ("mobile",phone);
        map.put("msg", "您好，您的验证码："+code+"，10分钟内有效");
        map.put("needstatus", "true");
        map.put("resptype", "json");
        System.out.println (JSONUtils.toJSONString (map));
        String json = HttpUtils.doPost(SEND_SMS, map);
        System.out.println(json);
        return json;
    }
}
