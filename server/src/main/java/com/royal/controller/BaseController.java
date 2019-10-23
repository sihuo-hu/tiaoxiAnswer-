package com.royal.controller;

import javax.servlet.http.HttpServletRequest;

import com.royal.commen.Logger;;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import com.royal.commen.Logger;
import com.royal.entity.json.PageData;

public class BaseController {

	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
}
