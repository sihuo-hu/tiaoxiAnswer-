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
import com.royal.entity.TradingOpportunities;
import com.royal.service.ITradingOpportunitiesService;

/**
* 描述：交易机会控制层
* @author Royal
* @date 2019年02月14日 21:09:16
*/
@Controller
@RequestMapping("/tradingOpportunities")
public class TradingOpportunitiesController extends BaseController {

    @Autowired
    private ITradingOpportunitiesService tradingOpportunitiesService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(TradingOpportunities tradingOpportunities)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<TradingOpportunities> list = tradingOpportunitiesService.getPageOrderByCreateTime (tradingOpportunities, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("TradingOpportunities出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  交易机会id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(TradingOpportunities vo)throws Exception {
		try {
			TradingOpportunities tradingOpportunities = tradingOpportunitiesService.findById(vo.getId());
			return new Result(tradingOpportunities);
		} catch (Exception e) {
			logger.error("TradingOpportunities出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建交易机会
    * @param tradingOpportunities  交易机会
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(TradingOpportunities tradingOpportunities) throws Exception {
		try {
			tradingOpportunitiesService.add(tradingOpportunities);
			return new Result();
		} catch (Exception e) {
			logger.error("TradingOpportunities出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除交易机会
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(TradingOpportunities tradingOpportunities) throws Exception {
		try {
			tradingOpportunitiesService.delete(tradingOpportunities);
			return new Result();
		} catch (Exception e) {
			logger.error("TradingOpportunities出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新交易机会
    * @param tradingOpportunities 交易机会id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateTradingOpportunities(TradingOpportunities tradingOpportunities) throws Exception {
		try {
			tradingOpportunitiesService.update(tradingOpportunities);
			return new Result();
		} catch (Exception e) {
			logger.error("TradingOpportunities出异常了", e);
			return new Result(e);
		}
    }

}