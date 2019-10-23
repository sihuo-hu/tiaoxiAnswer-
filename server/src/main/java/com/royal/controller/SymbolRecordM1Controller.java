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
import com.royal.entity.SymbolRecordM1;
import com.royal.service.ISymbolRecordM1Service;

/**
* 描述：产品历史记录控制层
* @author Royal
* @date 2019年07月01日 16:39:01
*/
@Controller
@RequestMapping("/symbolRecordM1")
public class SymbolRecordM1Controller extends BaseController {

    @Autowired
    private ISymbolRecordM1Service symbolRecordM1Service;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(SymbolRecordM1 symbolRecordM1)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<SymbolRecordM1> list = symbolRecordM1Service.getPage(symbolRecordM1, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("SymbolRecordM1出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  产品历史记录id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(SymbolRecordM1 vo)throws Exception {
		try {
			SymbolRecordM1 symbolRecordM1 = symbolRecordM1Service.findById(vo.getId());
			return new Result(symbolRecordM1);
		} catch (Exception e) {
			logger.error("SymbolRecordM1出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建产品历史记录
    * @param symbolRecordM1  产品历史记录
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(SymbolRecordM1 symbolRecordM1) throws Exception {
		try {
			symbolRecordM1Service.add(symbolRecordM1);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolRecordM1出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除产品历史记录
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(SymbolRecordM1 symbolRecordM1) throws Exception {
		try {
			symbolRecordM1Service.delete(symbolRecordM1);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolRecordM1出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新产品历史记录
    * @param symbolRecordM1 产品历史记录id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateSymbolRecordM1(SymbolRecordM1 symbolRecordM1) throws Exception {
		try {
			symbolRecordM1Service.update(symbolRecordM1);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolRecordM1出异常了", e);
			return new Result(e);
		}
    }

}