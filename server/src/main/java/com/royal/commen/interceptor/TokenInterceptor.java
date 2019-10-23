package com.royal.commen.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.royal.entity.enums.ResultEnum;
import com.royal.entity.json.Result;
import com.royal.util.Constants;
import com.royal.util.JSONUtils;
import com.royal.util.JwtUtil;

/**
 * @author Royal
 * @addDate 2018年5月28日上午11:02:00
 * @description APP请求token拦截器
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    public static String[] excludePathPatterns = new String[]{
            "/price/symbols",
            "/login/getCode",
            "/login/register",
            "/login/passwordLogin",
            "/login/refreshJWT",
            "/login/forgetPassword",
            "/banner/getList",
            "/symbolInfo/*",
            "/file/upload",
            "/tradingOpportunities/*",
            "/switch/*",
            "/version/*",
            "/websocket/*",
            "/amountRecord/payNotify"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            Result result = null;
        try {
            System.out.println("JWT请求地址：" + request.getRequestURI() + ", 请求头：" + request.getHeader(Constants.JWTConstants.JWT_HEAD));
            String authorization = request.getHeader(Constants.JWTConstants.JWT_HEAD);
            Integer checkResult = JwtUtil.checkJWT(authorization);
            switch (checkResult) {
                case Constants.JWTConstants.SUCCEED:
                    String token = JwtUtil.refreshJWT(authorization);
                    response.addHeader(Constants.JWTConstants.JWT_HEAD, token);
                    return true;
                case Constants.JWTConstants.SIGN_ERROR:
                    result = new Result(ResultEnum.ILLEGALITY);
                    break;
                case Constants.JWTConstants.DATE_EXPIRE:
                    result = new Result(ResultEnum.REFRESH_JWT);
                    this.setResponse405(response, result);
                    return false;
                case Constants.JWTConstants.CHECK_ERROR:
                    result = new Result(ResultEnum.ILLEGALITY);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
            }
            this.setResponse(response, result);
            return false;
        }catch (Exception e){
            result = new Result(ResultEnum.ANEW_LOGIN);
            this.setResponse(response, result);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion (request, response, handler, ex);
    }

    private void setResponse(HttpServletResponse resp, Result result) throws IOException {
        resp.setContentType ("application/json;charset=utf-8");
        String json = JSONUtils.toJSONObject (result).toString ();
        System.out.println (json);
        resp.getWriter ().print (json);
    }

    private void setResponse405(HttpServletResponse resp, Result result) throws IOException {
        resp.setContentType ("application/json;charset=utf-8");
        String json = JSONUtils.toJSONObject (result).toString ();
        System.out.println (json);
        resp.setStatus (HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        resp.getWriter ().print (json);
    }

}
