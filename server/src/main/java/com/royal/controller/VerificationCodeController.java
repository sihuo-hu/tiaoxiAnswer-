package com.royal.controller;

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
import com.royal.entity.VerificationCode;
import com.royal.service.IVerificationCodeService;

/**
* 描述：验证码控制层
* @author Royal
* @date 2018年12月04日 13:52:12
*/
@Controller
@RequestMapping("/verificationCode")
public class VerificationCodeController extends BaseController {

    @Autowired
    private IVerificationCodeService verificationCodeService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getVerificationCodeList")
	@ResponseBody
    public Result getPage(VerificationCode verificationCode)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<VerificationCode> list = verificationCodeService.getPage(verificationCode, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("VerificationCode出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  聊天记录id
    */
    @RequestMapping(value = "/getVerificationCode")
	@ResponseBody
    public Result findById(VerificationCode vo)throws Exception {
		try {
			VerificationCode verificationCode = verificationCodeService.findById(vo.getId());
			return new Result(verificationCode);
		} catch (Exception e) {
			logger.error("VerificationCode出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建聊天记录
    * @param verificationCode  聊天记录
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(VerificationCode verificationCode) throws Exception {
		try {
			verificationCodeService.add(verificationCode);
			return new Result();
		} catch (Exception e) {
			logger.error("VerificationCode出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除聊天记录
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(VerificationCode verificationCode) throws Exception {
		try {
			verificationCodeService.delete(verificationCode);
			return new Result();
		} catch (Exception e) {
			logger.error("VerificationCode出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新聊天记录
    * @param verificationCode 聊天记录id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateVerificationCode(VerificationCode verificationCode) throws Exception {
		try {
			verificationCodeService.update(verificationCode);
			return new Result();
		} catch (Exception e) {
			logger.error("VerificationCode出异常了", e);
			return new Result(e);
		}
    }

}