package com.royal.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebSocketUtil {
    private static Map<String, Long> signMap = new HashMap<String, Long> ();

    public static void addSet(String sign, Long time) {
        signMap.put (sign, time);
    }

    public static synchronized boolean verificationSign(String sign) {
        if (signMap.containsKey (sign)) {
            Long time = signMap.get (sign);
            if (time + 1000 * 60 * 10 > new Date ().getTime ()) {
                signMap.remove (sign);
                return true;
            }
        }
        return false;
    }

    public static void delSet(){
        for (Map.Entry<String, Long> entry : signMap.entrySet()) {
            if (entry.getValue() + 1000 * 60 * 10 < new Date ().getTime ()) {
                signMap.remove (entry.getKey());
            }
        }
    }

}
