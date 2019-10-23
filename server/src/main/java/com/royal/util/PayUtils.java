package com.royal.util;

import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.PayRequest;
import com.royal.entity.json.Result;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class PayUtils {


    /**
     *  发起支付
     * @param payWay 1:微信，2:支付宝，3:云闪付
     * @param money 美元
     * @param price 汇率
     * @param uuid ID
     * @param returnUrl 支付回调
     * @return
     * @throws Exception
     */
    public static Object pay(String payWay,BigDecimal money,String price,String uuid,String returnUrl) throws Exception {
        PayRequest payRequest = new PayRequest();
        payRequest.setMer_id(Constants.PayConstants.MER_ID);
        payRequest.setPay_type(payWay);
        payRequest.setAmt(money.multiply(new BigDecimal(price)).setScale(2,
                BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue() + "");
        payRequest.setNotify_url(Constants.PayConstants.NOTIFY_URL);
        payRequest.setReturnUrl(returnUrl);
        payRequest.setMer_trade_no(uuid);
        Map<String, Object> params = JSONUtils.objectToMap(payRequest);
        params.put("sign", PayUtils.sign(params));
        String json = HttpUtils.doPost(Constants.PayConstants.URL_ADDRESS, JSONUtils.toJSONString(params));
        Map<String, Object> response = JSONUtils.toHashMap(json);
        if (!response.containsKey("sign")) {
            return new Result(ResultEnum.PAY_ERROR);
        }
        String responseSign = response.get("sign").toString();
        response.remove("sign");
        String myResponseSign = PayUtils.sign(response);
        if (!responseSign.equals(myResponseSign)) {
            return new Result(ResultEnum.PAY_SIGN_ERROR);
        }
        return response;
    }

    public static String formatParam(Map<String, Object> param) {
        String params = "";
        Map<String, Object> map = param;

        try {
            List<Map.Entry<String, Object>> itmes = new ArrayList<Map.Entry<String, Object>> (map.entrySet ());

            //对所有传入的参数按照字段名从小到大排序
            //Collections.sort(items); 默认正序
            //可通过实现Comparator接口的compare方法来完成自定义排序
            Collections.sort (itmes, new Comparator<Map.Entry<String, Object>> () {
                @Override
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    // TODO Auto-generated method stub
                    return (o1.getKey ().toString ().compareTo (o2.getKey ()));
                }
            });

            //构造URL 键值对的形式
            StringBuffer sb = new StringBuffer ();
            for (Map.Entry<String, Object> item : itmes) {
                if (StringUtils.isNotBlank (item.getKey ())) {
                    String key = item.getKey ();
                    Object val = item.getValue ();
                    sb.append (key + "=" + val);
                    sb.append ("&");
                }
            }
            sb.append ("key=");
            sb.append (Constants.PayConstants.KEY);
            params = sb.toString ();

        } catch (Exception e) {
            return "";
        }
        return params;
    }

    public static String sign(Map<String, Object> param){
        String sign = MD5.md5 (formatParam(param));
        return sign.toUpperCase ();
    }
}
