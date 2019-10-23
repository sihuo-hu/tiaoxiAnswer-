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
import com.royal.entity.CashCoupon;
import com.royal.service.ICashCouponService;

/**
* 描述：代金券控制层
* @author Royal
* @date 2019年05月23日 19:35:09
*/
@Controller
@RequestMapping("/cashCoupon")
public class CashCouponController extends BaseController {

    @Autowired
    private ICashCouponService cashCouponService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(CashCoupon cashCoupon)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<CashCoupon> list = cashCouponService.getPage(cashCoupon, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("CashCoupon出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  代金券id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(CashCoupon vo)throws Exception {
		try {
			CashCoupon cashCoupon = cashCouponService.findById(vo.getId());
			return new Result(cashCoupon);
		} catch (Exception e) {
			logger.error("CashCoupon出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建代金券
    * @param cashCoupon  代金券
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(CashCoupon cashCoupon) throws Exception {
		try {
			cashCouponService.add(cashCoupon);
			return new Result();
		} catch (Exception e) {
			logger.error("CashCoupon出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除代金券
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(CashCoupon cashCoupon) throws Exception {
		try {
			cashCouponService.delete(cashCoupon);
			return new Result();
		} catch (Exception e) {
			logger.error("CashCoupon出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新代金券
    * @param cashCoupon 代金券id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateCashCoupon(CashCoupon cashCoupon) throws Exception {
		try {
			cashCouponService.update(cashCoupon);
			return new Result();
		} catch (Exception e) {
			logger.error("CashCoupon出异常了", e);
			return new Result(e);
		}
    }

}