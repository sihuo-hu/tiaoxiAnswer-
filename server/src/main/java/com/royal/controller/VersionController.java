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
import com.royal.entity.Version;
import com.royal.service.IVersionService;

/**
* 描述：版本控制控制层
* @author Royal
* @date 2019年03月30日 22:56:30
*/
@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {

    @Autowired
    private IVersionService versionService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(Version version)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<Version> list = versionService.getPage(version, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("Version出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  版本控制id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(Version vo)throws Exception {
		try {
			Version version = versionService.findById(vo.getId());
			return new Result(version);
		} catch (Exception e) {
			logger.error("Version出异常了", e);
    		return new Result(e);
		}
    
    }

	/**
	 * 描述：根据平台及版本号查询
	 * @param vo  版本控制id
	 */
	@RequestMapping(value = "/getInfo")
	@ResponseBody
	public Result getInfo(Version vo)throws Exception {
		try {
			Version version = versionService.getInfo(vo);
			return new Result(version);
		} catch (Exception e) {
			logger.error("Version出异常了", e);
			return new Result(e);
		}

	}

    /**
    * 描述:创建版本控制
    * @param version  版本控制
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(Version version) throws Exception {
		try {
			versionService.add(version);
			return new Result();
		} catch (Exception e) {
			logger.error("Version出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除版本控制
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(Version version) throws Exception {
		try {
			versionService.delete(version);
			return new Result();
		} catch (Exception e) {
			logger.error("Version出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新版本控制
    * @param version 版本控制id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateVersion(Version version) throws Exception {
		try {
			versionService.update(version);
			return new Result();
		} catch (Exception e) {
			logger.error("Version出异常了", e);
			return new Result(e);
		}
    }

}