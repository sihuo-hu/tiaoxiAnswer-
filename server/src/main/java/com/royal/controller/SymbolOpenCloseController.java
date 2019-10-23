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
import com.royal.entity.SymbolOpenClose;
import com.royal.service.ISymbolOpenCloseService;

/**
* 描述：产品昨收今开控制层
* @author Royal
* @date 2019年01月07日 12:32:36
*/
@Controller
@RequestMapping("/symbolOpenClose")
public class SymbolOpenCloseController extends BaseController {

    @Autowired
    private ISymbolOpenCloseService symbolOpenCloseService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(SymbolOpenClose symbolOpenClose)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<SymbolOpenClose> list = symbolOpenCloseService.getPage(symbolOpenClose, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("SymbolOpenClose出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  产品昨收今开id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(SymbolOpenClose vo)throws Exception {
		try {
			SymbolOpenClose symbolOpenClose = symbolOpenCloseService.findById(vo.getId());
			return new Result(symbolOpenClose);
		} catch (Exception e) {
			logger.error("SymbolOpenClose出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建产品昨收今开
    * @param symbolOpenClose  产品昨收今开
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(SymbolOpenClose symbolOpenClose) throws Exception {
		try {
			symbolOpenCloseService.add(symbolOpenClose);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolOpenClose出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除产品昨收今开
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(SymbolOpenClose symbolOpenClose) throws Exception {
		try {
			symbolOpenCloseService.delete(symbolOpenClose);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolOpenClose出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新产品昨收今开
    * @param symbolOpenClose 产品昨收今开id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateSymbolOpenClose(SymbolOpenClose symbolOpenClose) throws Exception {
		try {
			symbolOpenCloseService.update(symbolOpenClose);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolOpenClose出异常了", e);
			return new Result(e);
		}
    }

}