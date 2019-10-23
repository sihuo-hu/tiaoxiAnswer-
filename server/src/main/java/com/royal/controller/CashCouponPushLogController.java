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
import com.royal.entity.CashCouponPushLog;
import com.royal.service.ICashCouponPushLogService;

/**
* 描述：代金券发放日志控制层
* @author Royal
* @date 2019年06月27日 09:53:36
*/
@Controller
@RequestMapping("/cashCouponPushLog")
public class CashCouponPushLogController extends BaseController {

    @Autowired
    private ICashCouponPushLogService cashCouponPushLogService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(CashCouponPushLog cashCouponPushLog)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<CashCouponPushLog> list = cashCouponPushLogService.getPage(cashCouponPushLog, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("CashCouponPushLog出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  代金券发放日志id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(CashCouponPushLog vo)throws Exception {
		try {
			CashCouponPushLog cashCouponPushLog = cashCouponPushLogService.findById(vo.getId());
			return new Result(cashCouponPushLog);
		} catch (Exception e) {
			logger.error("CashCouponPushLog出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建代金券发放日志
    * @param cashCouponPushLog  代金券发放日志
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(CashCouponPushLog cashCouponPushLog) throws Exception {
		try {
			cashCouponPushLogService.add(cashCouponPushLog);
			return new Result();
		} catch (Exception e) {
			logger.error("CashCouponPushLog出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除代金券发放日志
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(CashCouponPushLog cashCouponPushLog) throws Exception {
		try {
			cashCouponPushLogService.delete(cashCouponPushLog);
			return new Result();
		} catch (Exception e) {
			logger.error("CashCouponPushLog出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新代金券发放日志
    * @param cashCouponPushLog 代金券发放日志id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateCashCouponPushLog(CashCouponPushLog cashCouponPushLog) throws Exception {
		try {
			cashCouponPushLogService.update(cashCouponPushLog);
			return new Result();
		} catch (Exception e) {
			logger.error("CashCouponPushLog出异常了", e);
			return new Result(e);
		}
    }

}