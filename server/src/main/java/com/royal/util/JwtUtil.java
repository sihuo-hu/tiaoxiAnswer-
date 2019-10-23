package com.royal.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import com.royal.commen.Logger;;
import org.joda.time.DateTime;

//import com.royal.commen.Logger;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT校验工具类
 * <p>
 * iss: jwt签发者 sub: jwt所面向的用户 aud: 接收jwt的一方 exp: jwt的过期时间，这个过期时间必须要大于签发时间 nbf:
 * 定义在什么时间之前，该jwt都是不可用的 iat: jwt的签发时间 jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
 */
public class JwtUtil {
    static Logger logger = Logger.getLogger (JwtUtil.class);

    /**
     * 构建JWT 使用 UUID 作为 jti 唯一身份标识 JWT有效时间 600 秒，即 10 分钟
     *
     * @param sub jwt 面向的用户
     * @return JWT字符串
     */
    public static String buildJWT(String sub) {
        String sign = getSign (sub);
        if (Tools.isEmpty (sign)) {
            return null;
        }
        String jwtStr = buildJWT (sub, null, UUID.randomUUID ().toString (), null, null, Constants.JWTConstants.DURATION,
                sign);
        return jwtStr;
    }

    /**
     * 刷新JWT
     *
     * @param claimsJws
     * @return JWT或null (如果返回null则代表用户长时间未使用过APP,需要强制重新登录)
     */
    public static String refreshJWT(String claimsJws) {
        Claims claims = getClaims (claimsJws);
        if (checkJWT (claims) == Constants.JWTConstants.DATE_EXPIRE) {
            return buildJWT (claims.getSubject ());
        }
        return null;
    }

    /**
     * 获取用户loginName,此方法必须在效验完JWT后调用,否则有抛出Exception的可能
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getUser(HttpServletRequest request) throws Exception {
        String claimsJws = request.getHeader (Constants.JWTConstants.JWT_HEAD);
        Claims claims = getClaims (claimsJws);
        System.out.println (claims.getSubject ());
        return claims.getSubject ();
    }

    /**
     * 校验JWT
     *
     * @param claimsJws jwt 内容文本
     */
    public static Integer checkJWT(String claimsJws) {
        Claims claims = getClaims (claimsJws);
        return checkJWT (claims);
    }

    /**
     * 校验单点登录JWT
     *
     * @param claimsJws jwt 内容文本
     */
    public static Integer checkJtiJWT(String claimsJws, String jti) {
        Claims claims = getClaims (claimsJws);
        return checkJWT (claims, jti);
    }

    /**
     * 构建JWT(用户单点登录),
     *
     * @param sub      jwt 面向的用户
     * @param duration jwt 有效期(单位为秒,建议30-60之间)
     * @param jti      jwt 唯一身份标识，必须保证不重复,调用者需要自行存储(用于验证)
     * @return JWT字符串
     */
    public static String buildJWT(String sub, String jti, Integer duration) {
        String sign = getSign (sub);
        if (Tools.isEmpty (sign)) {
            return null;
        }
        return buildJWT (sub, null, jti, null, null, duration, sign);
    }

    /**
     * 验证JWT
     *
     * @param claims
     * @return
     */
    private static Integer checkJWT(Claims claims, String jti) {
        if (claims != null) {
            //验证签名内的时间是否过期
            if (checkDate (claims)) {
                if (claims.getId ().equals (jti)) {
                    if (checkUser (claims)) {
                        return Constants.JWTConstants.SUCCEED;
                    }
                    return Constants.JWTConstants.SIGN_ERROR;
                }
                return Constants.JWTConstants.CHECK_ERROR;
            }
            return Constants.JWTConstants.DATE_EXPIRE;
        }
        return Constants.JWTConstants.CHECK_ERROR;
    }

    /**
     * 验证JWT
     *
     * @param claims
     * @return
     */
    private static Integer checkJWT(Claims claims) {
        //初步验签
        if (claims != null) {
            //验证签名内的时间是否过期
            if (checkDate (claims)) {
                //验证用户是否匹配
                if (checkUser (claims)) {
                    return Constants.JWTConstants.SUCCEED;
                }
                return Constants.JWTConstants.SIGN_ERROR;
            }
            return Constants.JWTConstants.DATE_EXPIRE;
        }
        return Constants.JWTConstants.CHECK_ERROR;
    }

    /**
     * 使用指定密钥生成规则，生成JWT加解密密钥
     *
     * @param alg  加解密类型
     * @param rule 密钥生成规则
     * @return
     */
    private static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        // 将密钥生成键转换为字节数组
        byte[] bytes = Base64.decodeBase64 (rule);
        // 根据指定的加密方式，生成密钥
        return new SecretKeySpec (bytes, alg.getJcaName ());
    }

    /**
     * 构建JWT
     *
     * @param alg      jwt 加密算法
     * @param key      jwt 加密密钥
     * @param sub      jwt 面向的用户
     * @param aud      jwt 接收方
     * @param jti      jwt 唯一身份标识
     * @param iss      jwt 签发者
     * @param nbf      jwt 生效日期时间
     * @param duration jwt 有效时间，单位：秒
     * @return JWT字符串
     */
    private static String buildJWT(SignatureAlgorithm alg, Key key, String sub, String aud, String jti, String iss,
                                   Date nbf, Integer duration, String sign) {
        // jwt的签发时间
        DateTime iat = new DateTime ();
        // jwt的过期时间，这个过期时间必须要大于签发时间
        DateTime exp = null;
        if (duration != null)
            exp = (nbf == null ? iat.plusSeconds (duration) : new DateTime (nbf).plusSeconds (duration));

        // 获取JWT字符串
        String compact = Jwts.builder ().signWith (alg, key).setSubject (sub).setAudience (aud).setId (jti).setIssuer (iss)
                .setNotBefore (nbf).setIssuedAt (iat.toDate ()).setExpiration (exp != null ? exp.toDate () : null)
                .claim ("sign", sign).compact ();

        // 在JWT字符串前添加"Bearer "字符串，用于加入"Authorization"请求头
        return Constants.JWTConstants.JWT_SEPARATOR + compact;
    }

    /**
     * 构建JWT
     *
     * @param sub      jwt 面向的用户
     * @param aud      jwt 接收方
     * @param jti      jwt 唯一身份标识
     * @param iss      jwt 签发者
     * @param nbf      jwt 生效日期时间
     * @param duration jwt 有效时间，单位：秒
     * @return JWT字符串
     */
    private static String buildJWT(String sub, String aud, String jti, String iss, Date nbf, Integer duration,
                                   String sign) {
        return buildJWT (Constants.JWTConstants.JWT_ALG,
                generateKey (Constants.JWTConstants.JWT_ALG, Constants.JWTConstants.JWT_RULE), sub, aud, jti, iss, nbf,
                duration, sign);
    }

    /**
     * 解析JWT
     *
     * @param key       jwt 加密密钥
     * @param claimsJws jwt 内容文本
     * @return {@link Jws}
     * @throws Exception
     */
    private static Jws<Claims> parseJWT(Key key, String claimsJws) {
        // 移除 JWT 前的"Bearer "字符串
        claimsJws = StringUtils.substringAfter (claimsJws, Constants.JWTConstants.JWT_SEPARATOR);
        // 解析 JWT 字符串
        return Jwts.parser ().setSigningKey (key).parseClaimsJws (claimsJws);
    }

    /**
     * 解析成Claims
     *
     * @param claimsJws
     * @return
     */
    private static Claims getClaims(String claimsJws) {
        try {
            SecretKey key = generateKey (Constants.JWTConstants.JWT_ALG, Constants.JWTConstants.JWT_RULE);
            // 获取 JWT 的 payload 部分
            return parseJWT (key, claimsJws).getBody ();
        } catch (Exception e) {
            logger.error ("JWT验证出错，错误原因：{}", e);
            return null;
        }
    }

    /**
     * 验证是否为同一个用户
     *
     * @param claims
     * @return
     */
    private static Boolean checkUser(Claims claims) {
        String userId = claims.getSubject ();
        String sign = claims.get ("sign").toString ();
        String signUserId = getLoginName (sign);
        return userId.equals (signUserId);
    }

    /**
     * 验证签名的时间是否在有效期内
     *
     * @param claims
     * @return
     */
    private static Boolean checkDate(Claims claims) {
        String sign = claims.get ("sign").toString ();
        Date issuedAt = getSignDate (sign);
        System.out.println (DateUtils.getFormatDateTime (issuedAt));
        if (issuedAt == null) {
            return false;
        }
        return DateUtils.belongCalendar (new Date (), issuedAt,
                new Date (issuedAt.getTime () + Constants.JWTConstants.SIGN_DURATION));
    }

    /**
     * 获取签名中的用户loginName
     *
     * @param encryptStr
     * @return
     */
    private static String getLoginName(String encryptStr) {
        Map<String, String> map = getMap (encryptStr);
        return map == null ? null : map.get ("loginName");
    }

    /**
     * 获取签名的签发时间
     *
     * @param encryptStr
     * @return
     */
    private static Date getSignDate(String encryptStr) {
        Map<String, String> map = getMap (encryptStr);
        return map == null ? null : new Date (Long.parseLong (map.get ("date")));
    }

    /**
     * 获取签名中的信息
     *
     * @param encryptStr
     * @return
     */
    public static Map<String, String> getMap(String encryptStr) {
        try {
            String sign = SecurityUtil.AesUtil.decrypt (encryptStr, Constants.JWTConstants.SECRET_KEY_STR);
            Map<String, String> map = new HashMap<String, String> ();
            map.put ("loginName", sign.split (Constants.JWTConstants.SEPARATOR)[0]);
            map.put ("date", sign.split (Constants.JWTConstants.SEPARATOR)[1]);
            return map;
        } catch (Exception e) {
            logger.error ("JWT获取SIGN出错，错误原因：{}", e);
            return null;
        }
    }


    /**
     * 生成sign签名
     *
     * @param loginName
     * @return
     */
    private static String getSign(String loginName) {
        try {
            return SecurityUtil.AesUtil.encrypt (loginName + Constants.JWTConstants.SEPARATOR + System.currentTimeMillis (),
                    Constants.JWTConstants.SECRET_KEY_STR);
        } catch (Exception e) {
            logger.error ("JWT生成SIGN出错，错误原因：{}", e);
            return null;
        }
    }


}
