package com.royal.util;

import io.jsonwebtoken.SignatureAlgorithm;

public class Constants {

    /**
     * JWT常量池
     */
    public static class JWTConstants {

        /**
         * 分隔符
         */
        public static final String SEPARATOR = "&!43yuHHHHyu34!&";
        /**
         * AES秘钥
         */
        public static final String SECRET_KEY_STR = "pIO9x7NmfYU360CyQdNFUA==";
        /**
         * JWT 加解密类型
         */
        public static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
        /**
         * JWT 生成密钥使用的密码
         */
        public static final String JWT_RULE = "dahsjkhu&^@19ualdb%*).21sail1wd";

        /**
         * JWT 添加至HTTP HEAD中的前缀
         */
        public static final String JWT_SEPARATOR = "Bearer ";

        public static final String JWT_HEAD = "Authorization";

        /**
         * JWT TOKEN有效时间(秒)
         */
        public static final Integer DURATION = 60 * 60 * 24 * 7;

        /**
         * 签名有效期(间隔多长需要刷新token)
         */
        public static final Integer SIGN_DURATION = 600 * 1000;

        /**
         * 验证成功
         */
        public static final int SUCCEED = 0;

        /**
         * JWT验签失败
         */
        public static final int CHECK_ERROR = 1;
        /**
         * JWT有效期过期(需要刷新TOKEN)
         */
        public static final int DATE_EXPIRE = 2;
        /**
         * 签名错误
         */
        public static final int SIGN_ERROR = 5;

    }

    /**
     * 验证码常量池
     */
    public static class CodeConstants {
        /**
         * 注册
         */
        public static final int REGISTER = 1;
        /**
         * 忘记密码
         */
        public static final int FORGET_PASSWORD = 2;
    }

    /**
     * 用户常量池
     */
    public static class UserConstants {
        /**
         * 登陆密码前面的盐
         */
        public static final String FRONT_SALT = "zst";
        /**
         * 登陆密码后面的盐
         */
        public static final String BEHIND_SALT = "013";
    }

    public static class WebocketConstants {

        public static final String SIGN = "sdhjW23hUDSbqubs";

    }

    /**
     * 支付常量池
     */
    public static class PayConstants{
        /**
         * 下单地址
         */
        public static final String URL_ADDRESS = "http://pay.wstpay.cn/h5pay/order";
        /**
         * 查询地址
         */
        public static final String SELECT_URL_ADDRESS = "http://pay.wstpay.cn/h5pay/query";
        /**
         * 商户号
         */
        public static final String MER_ID ="M00000000203";
        /**
         * app名称
         */
        public static final String MER_APP_NAME="掌上投";
        /**
         * 签名key
         */
        public static final String KEY ="41b0890ddc7d48438fc9b064e4a2259b";
        /**
         * 回调地址
         */
        public static final String NOTIFY_URL="http://www.zhangstz.com/royal/amountRecord/payNotify";
//        public static final String NOTIFY_URL="http://www.wjhsh.cn/royal/amountRecord/payNotify";
    }

}
