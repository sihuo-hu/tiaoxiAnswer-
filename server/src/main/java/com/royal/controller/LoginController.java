package com.royal.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.royal.commen.Logger;
import com.royal.commen.rong.RYUtil;
import com.royal.commen.rong.models.RYIDResponse;
import com.royal.entity.User;
import com.royal.entity.UserAmount;
import com.royal.entity.VerificationCode;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.PageData;
import com.royal.entity.json.Result;
import com.royal.service.ICashCouponService;
import com.royal.service.IUserAmountService;
import com.royal.service.IUserService;
import com.royal.service.IVerificationCodeService;
import com.royal.util.*;
import com.royal.commen.Logger;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;


@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    protected static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private IVerificationCodeService verificationCodeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserAmountService userAmountService;
    @Autowired
    private ICashCouponService cashCouponService;

    /**
     * 获取验证码
     *
     * @param verificationCode
     * @return
     */
    @RequestMapping(value = "/getCode")
    @ResponseBody
    public Result getCode(VerificationCode verificationCode) {
        try {
            if (!Tools.checkMobileNumber(verificationCode.getLoginName())) {
                return new Result(ResultEnum.PHONE_FORMAT_ERROR);
            }
            User user = userService.findByLoginName(new User(), verificationCode.getLoginName());
            if (verificationCode.getCodeType() == Constants.CodeConstants.REGISTER) {
                if (user != null && !Tools.isEmpty(user.getId())) {
                    return new Result(ResultEnum.ACCOUNT_EXIST);
                }
            } else if (verificationCode.getCodeType() == Constants.CodeConstants.FORGET_PASSWORD) {
                if (user == null || Tools.isEmpty(user.getId())) {
                    return new Result(ResultEnum.NOT_REGISTER);
                }
            }
            verificationCodeService.deleteByLoginNameAndCodeType(verificationCode);
            String code = Tools.getRandomNum();
            verificationCode.setCodeType(Constants.CodeConstants.REGISTER);
            verificationCode.setCreateTime(new Date());
            verificationCode.setExpirationTime(DateUtils.getDateBeforeOrAfterMinute(new Date(), 10));
            verificationCode.setVerificationCode(code);
            String json = SMSUtils.sendSMS(verificationCode.getLoginName(), code);
            if (json.indexOf("\"result\":0") >= 0) {
                verificationCodeService.add(verificationCode);
                return new Result(verificationCode.getId());
            }
            return new Result(ResultEnum.SMS_SEND_ERROR, json);
        } catch (Exception e) {
            logger.error("出异常了", e);
            return new Result(e);
        }
    }

    /**
     * 注册
     *
     * @param verificationCode
     * @param user
     * @param response
     * @return
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public Result register(VerificationCode verificationCode, User user, HttpServletResponse response) throws Exception {
        if (Tools.isEmpty(verificationCode.getId(), verificationCode.getLoginName(), user.getPassword(), user.getNickname(),user.getDitch())) {
            return new Result(ResultEnum.PARAMETER_ERROR);
        }
        //查看验证码是否正确
        verificationCode.setCodeType(Constants.CodeConstants.REGISTER);
        Result result = verificationCodeService.verification(verificationCode);
        if (!result.getMsgCode().equals("0")) {
            return result;
        }
        //检测账号是否存在
        User u = userService.findByLoginName(user, user.getLoginName());
        if (u != null && u.getId() != null) {
            return new Result(ResultEnum.ACCOUNT_EXIST);
        }
        String pasw = user.getPassword();
        pasw = new String(Base64Utils.decodeFromString(pasw), "UTF-8");
        pasw = pasw.substring(3, pasw.length() - 3);
        pasw = MD5.md5(Constants.UserConstants.FRONT_SALT + pasw + Constants.UserConstants.BEHIND_SALT);
        user.setId(null);
        user.setCreateTime(new Date());
        user.setPassword(pasw);
        user.setAuditStatus("DONT_SUBMIT");

        String ryId = null;
        try {
            ryId = initRYUser(user);
        } catch (Exception e) {
            logger.error("获取融云token是错误：" + JSONUtils.toJSONString(user), e);
            return new Result(e);
        }
        user.setRyToken(ryId);
        userService.add(user);

        String token = JwtUtil.buildJWT(verificationCode.getLoginName());
        response.addHeader(Constants.JWTConstants.JWT_HEAD, token);
        verificationCodeService.deleteById(verificationCode.getId());
        initUserAmount(user.getLoginName());
       BigDecimal money = cashCouponService.pushCashCoupon( user.getLoginName(), "NEW_CUSTOMER",null);
       if(money!=null){
           PushUtil.push(user.getLoginName(),"恭喜您的手机号已注册成功，一张"+money.toString()+"$代金券已飞奔到您的账户中，请及时体验使用。",null);
       }
        return new Result(true);
    }

    /**
     * 密码登陆
     *
     * @param user
     * @param response
     * @return
     */
    @RequestMapping(value = "/passwordLogin")
    @ResponseBody
    public Result passwordLogin(User user, HttpServletResponse response) throws UnsupportedEncodingException {
        String pasw = user.getPassword();
        pasw = new String(Base64Utils.decodeFromString(pasw), "UTF-8");
        pasw = pasw.substring(3, pasw.length() - 3);
        pasw = MD5.md5(Constants.UserConstants.FRONT_SALT + pasw + Constants.UserConstants.BEHIND_SALT);
        user.setPassword(pasw);
        if (userService.verificationPassword(user)) {
            String token = JwtUtil.buildJWT(user.getLoginName());
            response.addHeader(Constants.JWTConstants.JWT_HEAD, token);
            return new Result(true);
        }
        return new Result(ResultEnum.ACCOUNT_OR_PASSWORD_ERROR);
    }

    /**
     * 刷新JWT
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/refreshJWT")
    @ResponseBody
    public Result refreshJWT(HttpServletResponse response) {
        String token = JwtUtil.refreshJWT(this.getRequest().getHeader(Constants.JWTConstants.JWT_HEAD));
        if (Tools.isEmpty(token)) {
            return new Result(ResultEnum.ILLEGALITY);
        }
        response.addHeader(Constants.JWTConstants.JWT_HEAD, token);
        return new Result(true);
    }

    /**
     * 忘记密码
     *
     * @param verificationCode
     * @param user
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/forgetPassword")
    @ResponseBody
    public Result forgetPassword(VerificationCode verificationCode, User user, HttpServletResponse response) throws UnsupportedEncodingException {
        if (Tools.isEmpty(verificationCode.getId(), verificationCode.getLoginName(), user.getPassword(), user.getUserName())) {
            return new Result(ResultEnum.PARAMETER_ERROR);
        }
        //查看验证码是否正确
        verificationCode.setCodeType(Constants.CodeConstants.FORGET_PASSWORD);
        Result result = verificationCodeService.verification(verificationCode);
        if (!result.getMsgCode().equals("0")) {
            return result;
        }
        String pasw = user.getPassword();
        pasw = new String(Base64Utils.decodeFromString(pasw), "UTF-8");
        pasw = pasw.substring(3, pasw.length() - 3);
        pasw = MD5.md5(Constants.UserConstants.FRONT_SALT + pasw + Constants.UserConstants.BEHIND_SALT);
        user.setPassword(pasw);
        user.setLoginName(verificationCode.getLoginName());
        userService.updateByLoginName(user);
        String token = JwtUtil.buildJWT(verificationCode.getLoginName());
        response.addHeader(Constants.JWTConstants.JWT_HEAD, token);
        verificationCodeService.deleteById(verificationCode.getId());
        return new Result(true);
    }

    /**
     * 上传公钥
     *
     * @param publicKey
     * @return
     */
    @RequestMapping(value = "/uploadPublicKey")
    @ResponseBody
    public Result uploadPublicKey(String publicKey) throws Exception {
        String loginName = JwtUtil.getUser(getRequest());
        if (Tools.notEmpty(loginName)) {
            User user = new User();
            user.setLoginName(loginName);
            user.setPublicKey(publicKey);
            userService.updateByLoginName(user);
            return new Result(true);
        }
        return new Result(ResultEnum.ILLEGALITY);
    }

    /**
     * 获取客服ID
     *
     * @return
     */
    @RequestMapping(value = "/getServiceId")
    @ResponseBody
    public Result getServiceId() throws Exception {
        return new Result("17195995235");
    }

    public void initUserAmount(String loginName) {
        UserAmount userAmount = new UserAmount();
        userAmount.setLoginName(loginName);
        userAmount.setBalance(new BigDecimal(0));
        userAmount.setGrantAmount(new BigDecimal(0));
        userAmount.setRechargeAmount(new BigDecimal(0));
        userAmount.setWithdrawAmount(new BigDecimal(0));
        userAmountService.deleteByLoginName(loginName);
        userAmountService.add(userAmount);
    }

    private String initRYUser(User user) throws Exception {
        RYIDResponse r = new RYIDResponse();
        r.setImg("http://img.zhangstz.com/morentouxiang.png");
        r.setUserId(user.getLoginName());
        r.setUserName(user.getNickname());
        return RYUtil.getRY_ID(r);
    }

}
