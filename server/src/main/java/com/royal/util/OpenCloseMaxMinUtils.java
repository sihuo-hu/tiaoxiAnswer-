package com.royal.util;

import com.royal.entity.SymbolOpenClose;
import com.royal.service.ISymbolOpenCloseService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenCloseMaxMinUtils {

    private static Map<String, BigDecimal> map = new HashMap<String, BigDecimal> ();

    public static void setMap(String symbolCode, BigDecimal price) {
        map.put (symbolCode, price);
    }

    public static boolean validationMap(String symbolCode) {
        return map.containsKey (symbolCode);
    }

    public static void purgingMap() {
        map = new HashMap<String, BigDecimal> ();
    }

    private static Map<String, BigDecimal> maxMap = new HashMap<String, BigDecimal> ();
    private static Map<String, BigDecimal> minMap = new HashMap<String, BigDecimal> ();

    public static boolean setMax(String symbolCode, BigDecimal price) {
        if(price==null){
            price= new BigDecimal ("0");
        }
        if (maxMap.containsKey (symbolCode)) {
            if (maxMap.get (symbolCode).compareTo (price) < 0) {
                maxMap.put (symbolCode, price);
                return true;
            }
        } else {
            maxMap.put (symbolCode, price);
            return true;
        }
        return false;
    }

    public static boolean setMin(String symbolCode, BigDecimal price) {
        if(price==null){
            price= new BigDecimal ("0");
        }
        if (minMap.containsKey (symbolCode)) {
            if (minMap.get (symbolCode).compareTo (price) > 0) {
                minMap.put (symbolCode, price);
                return true;
            }
        } else {
            minMap.put (symbolCode, price);
            return true;
        }
        return false;
    }

    public static void purgingMaxAndMin() {
        maxMap = new HashMap<String, BigDecimal> ();
        minMap = new HashMap<String, BigDecimal> ();
    }


    public static void initMaxAndMin() {
        ISymbolOpenCloseService symbolOpenCloseService = (ISymbolOpenCloseService) SpringContextUtils.getBean (
                "symbolOpenCloseServiceImpl");
        String createTime = symbolOpenCloseService.getLastCreateTime();
        List<SymbolOpenClose> list = symbolOpenCloseService.findByCreateTime (createTime);
        if(list!=null&&list.size ()>0) {
            for (SymbolOpenClose symbolOpenClose : list) {
                setMax (symbolOpenClose.getSymbolCode (),symbolOpenClose.getMaxPrice ());
                setMin (symbolOpenClose.getSymbolCode (),symbolOpenClose.getMinPrice ());
            }
        }
    }
}
