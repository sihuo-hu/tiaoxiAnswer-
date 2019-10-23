package com.royal.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    /**
     * 随机生成六位数验证码
     *
     * @return
     */
    public static String getRandomNum() {
        Random r = new Random();
        return String.valueOf(r.nextInt(900000) + 100000);// (Math.random()*(999999-100000)+100000)
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(Object... s) {
        Object[] obj = s;
        for (Object object : obj) {
            return object == null || "".equals(object) || "null".equals(object);
        }
        return true;
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     * @return
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 生成uuid
     *
     * @return UUID
     */
    public static String getUUId() {
        return UUID.randomUUID().toString();
    }

    /**
     * unicode编码转字符串
     *
     * @param str
     * @return
     */
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern
                    .compile("^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * @param param 参数
     * @return
     */
    public static String formatParam(Map<String, Object> param) {
        String params = "";
        Map<String, Object> map = param;

        try {
            List<Map.Entry<String, Object>> itmes = new ArrayList<Map.Entry<String, Object>>(map.entrySet());

            //对所有传入的参数按照字段名从小到大排序
            //Collections.sort(items); 默认正序
            //可通过实现Comparator接口的compare方法来完成自定义排序
            Collections.sort(itmes, new Comparator<Map.Entry<String, Object>>() {
                @Override
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    // TODO Auto-generated method stub
                    return (o1.getKey().toString().compareTo(o2.getKey()));
                }
            });

            //构造URL 键值对的形式
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, Object> item : itmes) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    Object val = item.getValue();
                    sb.append(key + ":" + val);
                    sb.append(",");
                }
            }

            params = sb.toString();
            if (!params.isEmpty()) {
                params = params.substring(0, params.length() - 1);
            }
        } catch (Exception e) {
            return "";
        }
        return params;
    }



        /**
         * @param passLength
         *            : 要生成多少长度的字符串
         * @param type
         *            : 需要哪种类型
         * @return 根据传入的type来判定
         */

        // 可以根据自己需求来删减下面的代码，不要要的类型可以删掉

        // type=0：纯数字(0-9)
        // type=1：全小写字母(a-z)
        // type=2：全大写字母(A-Z)
        // type=3: 数字+小写字母
        // type=4: 数字+大写字母
        // type=5：大写字母+小写字母
        // type=6：数字+大写字母+小写字母
        // type=7：固定长度33位：根据UUID拿到的随机字符串，去掉了四个"-"(相当于长度33位的小写字母加数字)

        public static String getRandomCode(int passLength, int type) {
            StringBuffer buffer = null;
            StringBuffer sb = new StringBuffer();
            Random r = new Random();
            r.setSeed(new Date().getTime());
            switch (type) {
                case 0:
                    buffer = new StringBuffer("0123456789");
                    break;
                case 1:
                    buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyz");
                    break;
                case 2:
                    buffer = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                    break;
                case 3:
                    buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
                    break;
                case 4:
                    buffer = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                    break;
                case 5:
                    buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
                    break;
                case 6:
                    buffer = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
                    sb.append(buffer.charAt(r.nextInt(buffer.length() - 10)));
                    passLength -= 1;
                    break;
                case 7:
                    String s = UUID.randomUUID().toString();
                    sb.append(s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24));
            }

            if (type != 7) {
                int range = buffer.length();
                for (int i = 0; i < passLength; ++i) {
                    sb.append(buffer.charAt(r.nextInt(range)));
                }
            }
            return sb.toString();
        }

        public static String getPrice(){
            String priceJson = HttpUtils.doGet("http://api.k780.com/?app=finance" +
                    ".rate&scur=USD&tcur=CNY&appkey=40626&sign=2cb664dfcb09f13e0ea92d51125cb9bb&format=json");
            JSONObject jSONObject = JSONUtils.toJSONObject(priceJson);
            String price = jSONObject.getJSONObject("result").getString("rate");
            return price;
        }
}
