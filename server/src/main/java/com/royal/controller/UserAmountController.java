package com.royal.controller;

import com.royal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.json.Result;
import com.royal.entity.json.PageData;
import com.royal.entity.UserAmount;
import com.royal.service.IUserAmountService;

/**
* 描述：用户资金控制层
* @author Royal
* @date 2018年12月13日 17:16:02
*/
@Controller
@RequestMapping("/userAmount")
public class UserAmountController extends BaseController {

    @Autowired
    private IUserAmountService userAmountService;
    
    /**
    * 描述：根据Id 查询
    * @param vo  用户资金id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(UserAmount vo)throws Exception {
		try {
			String loginName = JwtUtil.getUser (getRequest ());
			UserAmount userAmount = userAmountService.findByLoginName(vo,loginName );
			return new Result(userAmount);
		} catch (Exception e) {
			logger.error("UserAmount出异常了", e);
    		return new Result(e);
		}
    
    }

}