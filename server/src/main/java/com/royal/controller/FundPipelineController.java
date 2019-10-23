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
import com.royal.entity.FundPipeline;
import com.royal.service.IFundPipelineService;

/**
* 描述：资金流水日志控制层
* @author Royal
* @date 2019年06月18日 16:36:40
*/
@Controller
@RequestMapping("/fundPipeline")
public class FundPipelineController extends BaseController {

    @Autowired
    private IFundPipelineService fundPipelineService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(FundPipeline fundPipeline)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<FundPipeline> list = fundPipelineService.getPage(fundPipeline, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("FundPipeline出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  资金流水日志id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(FundPipeline vo)throws Exception {
		try {
			FundPipeline fundPipeline = fundPipelineService.findById(vo.getId());
			return new Result(fundPipeline);
		} catch (Exception e) {
			logger.error("FundPipeline出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建资金流水日志
    * @param fundPipeline  资金流水日志
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(FundPipeline fundPipeline) throws Exception {
		try {
			fundPipelineService.add(fundPipeline);
			return new Result();
		} catch (Exception e) {
			logger.error("FundPipeline出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除资金流水日志
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(FundPipeline fundPipeline) throws Exception {
		try {
			fundPipelineService.delete(fundPipeline);
			return new Result();
		} catch (Exception e) {
			logger.error("FundPipeline出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新资金流水日志
    * @param fundPipeline 资金流水日志id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateFundPipeline(FundPipeline fundPipeline) throws Exception {
		try {
			fundPipelineService.update(fundPipeline);
			return new Result();
		} catch (Exception e) {
			logger.error("FundPipeline出异常了", e);
			return new Result(e);
		}
    }

}