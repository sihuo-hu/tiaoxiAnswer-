package com.royal.controller;

import com.royal.entity.TransactionRecord;
import com.royal.entity.enums.ResultEnum;
import com.royal.util.JwtUtil;
import com.royal.util.Tools;
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
import com.royal.entity.UserCashCoupon;
import com.royal.service.IUserCashCouponService;

import java.util.List;

/**
* 描述：用户代金券控制层
* @author Royal
* @date 2019年05月23日 19:41:18
*/
@Controller
@RequestMapping("/userCashCoupon")
public class UserCashCouponController extends BaseController {

    @Autowired
    private IUserCashCouponService userCashCouponService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(UserCashCoupon userCashCoupon)throws Exception {
		try {
			PageData pd = this.getPageData();
			userCashCoupon.setLoginName(JwtUtil.getUser (this.getRequest ()));
			PageInfo<UserCashCoupon> list = userCashCouponService.getMyPage(userCashCoupon, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("UserCashCoupon出异常了", e);
			return new Result(e);
		}
    
    }

	/**
	 * 描述：查询可用的代金券
	 */
	@RequestMapping(value = "/getUsableList")
	@ResponseBody
	public Result getUsableList(TransactionRecord transactionRecord)throws Exception {
		if(Tools.isEmpty(transactionRecord.getUnitPrice(),transactionRecord.getLot(),transactionRecord.getSymbolCode())){
			return new Result(ResultEnum.PARAMETER_ERROR);
		}
		try {
			transactionRecord.setMoney(transactionRecord.getUnitPrice()*transactionRecord.getLot());
			transactionRecord.setLoginName(JwtUtil.getUser (this.getRequest ()));
			List<UserCashCoupon> list = userCashCouponService.getUsableList(transactionRecord);
			return new Result(list);
		} catch (Exception e) {
			logger.error("UserCashCoupon出异常了", e);
			return new Result(e);
		}

	}



}