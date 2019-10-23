package com.royal.commen.interceptor;

//import com.royal.commen.Logger;

import com.royal.entity.SymbolInfo;
import com.royal.entity.User;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.PageData;
import com.royal.entity.json.Result;
import com.royal.service.ISymbolInfoService;
import com.royal.service.IUserService;
import com.royal.util.*;
import com.royal.commen.Logger;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Royal
 * @addDate 2018年5月28日上午11:02:00
 * @description APP请求交易验签拦截器
 */
public class TransactionInterceptor extends HandlerInterceptorAdapter {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    public static String[] excludePathPatterns = new String[]{};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        ISymbolInfoService symbolInfoService = (ISymbolInfoService) SpringContextUtils.getBean(
                "symbolInfoServiceImpl");
        IUserService userService = (IUserService) SpringContextUtils.getBean(
                "userServiceImpl");
        PageData pd = new PageData(request);
        System.out.println(pd.toString());
        SymbolInfo symbolInfo = symbolInfoService.findByCode(pd.getString("symbolCode"));
        if (symbolInfo.getStatus() != 1) {
            this.setResponse(response, new Result(ResultEnum.SYMBOL_NOT_BUY));
            return false;
        }
        //夏令时  05:05:00-04:50:00   原油，黄金，白银 06:00:00-04:50:00
//        if (month >= 4 && month <= 9) {
//            if (symbolInfo.getSymbolCode ().equals ("XAUUSD") ||symbolInfo.getSymbolCode ().equals ("XAGUSD") ||symbolInfo.getSymbolCode ().equals ("WTI") ) {
//                Date start = DateUtils.StringToDate(dateFormat+" 06:00:00");
//                Date end = DateUtils.StringToDate(dateFormat+" 04:50:00");
//                if(DateUtils.compare_date(date,end)&&DateUtils.compare_date(start,date)){
//                    this.setResponse(response, new Result(ResultEnum.TRANSACTION_TIME_ERROR));
//                    return false;
//                }
//            } else {
//                Date start = DateUtils.StringToDate(dateFormat+" 05:05:00");
//                Date end = DateUtils.StringToDate(dateFormat+" 04:50:00");
//                if(DateUtils.compare_date(date,end)&&DateUtils.compare_date(start,date)){
//                    this.setResponse(response, new Result(ResultEnum.TRANSACTION_TIME_ERROR));
//                    return false;
//                }
//            }
//            //冬令时  06:05:00-05:50:00  原油，黄金，白银 07:00:00-05:50:00
//        } else {
        if (!MT4Utils.isOpen(symbolInfo)) {
            this.setResponse(response, new Result(ResultEnum.TRANSACTION_TIME_ERROR));
            return false;
        }

        User user = userService.findByLoginName(new User(), JwtUtil.getUser(request));
        String signStr = pd.getString("sign");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("loginName", user.getLoginName());
        map.put("transactionStatus", pd.getInteger("transactionStatus"));
        map.put("symbolCode", pd.getString("symbolCode"));
        map.put("ransactionType", pd.getInteger("ransactionType"));
        map.put("unitPrice", pd.getInteger("unitPrice"));
        map.put("lot", pd.getInteger("lot"));
        map.put("id", pd.getInteger("id"));
        map.put("url", request.getRequestURI().replace("/royal", ""));
        String content = Tools.formatParam(map);
        System.out.println(signStr);
        System.out.println(content);
        if (SecurityUtil.RsaUtil.verify(content, signStr,
                user.getPublicKey(), true)) {
            return true;
        } else {
            this.setResponse(response, new Result(ResultEnum.ILLEGALITY));
            return false;
        }
    }

    private void setResponse(HttpServletResponse resp, Result result) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        String json = JSONUtils.toJSONString(result);
        resp.getWriter().println(json);
    }
}
