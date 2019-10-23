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
import com.royal.entity.Banner;
import com.royal.service.IBannerService;

/**
* 描述：banner图控制层
* @author Royal
* @date 2018年12月06日 16:24:12
*/
@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {

    @Autowired
    private IBannerService bannerService;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(Banner banner)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<Banner> list = bannerService.getPageBySort(pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("Banner出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  banner图id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(Banner vo)throws Exception {
		try {
			Banner banner = bannerService.findById(vo.getId());
			return new Result(banner);
		} catch (Exception e) {
			logger.error("Banner出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建banner图
    * @param banner  banner图
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(Banner banner) throws Exception {
		try {
			bannerService.add(banner);
			return new Result();
		} catch (Exception e) {
			logger.error("Banner出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除banner图
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(Banner banner) throws Exception {
		try {
			bannerService.delete(banner);
			return new Result();
		} catch (Exception e) {
			logger.error("Banner出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新banner图
    * @param banner banner图id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateBanner(Banner banner) throws Exception {
		try {
			bannerService.update(banner);
			return new Result();
		} catch (Exception e) {
			logger.error("Banner出异常了", e);
			return new Result(e);
		}
    }

}