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
import com.royal.entity.SymbolRecord;
import com.royal.service.ISymbolRecordService;

/**
* 描述：产品价格记录控制层
* @author Royal
* @date 2018年12月25日 21:45:16
*/
@Controller
@RequestMapping("/symbolRecord")
public class SymbolRecordController extends BaseController {

    @Autowired
    private ISymbolRecordService symbolRecordService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(SymbolRecord symbolRecord)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<SymbolRecord> list = symbolRecordService.getPage(symbolRecord, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("SymbolRecord出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  产品价格记录id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(SymbolRecord vo)throws Exception {
		try {
			SymbolRecord symbolRecord = symbolRecordService.findById(vo.getId());
			return new Result(symbolRecord);
		} catch (Exception e) {
			logger.error("SymbolRecord出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建产品价格记录
    * @param symbolRecord  产品价格记录
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(SymbolRecord symbolRecord) throws Exception {
		try {
			symbolRecordService.add(symbolRecord);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolRecord出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除产品价格记录
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(SymbolRecord symbolRecord) throws Exception {
		try {
			symbolRecordService.delete(symbolRecord);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolRecord出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新产品价格记录
    * @param symbolRecord 产品价格记录id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateSymbolRecord(SymbolRecord symbolRecord) throws Exception {
		try {
			symbolRecordService.update(symbolRecord);
			return new Result();
		} catch (Exception e) {
			logger.error("SymbolRecord出异常了", e);
			return new Result(e);
		}
    }

}