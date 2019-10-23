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
import com.royal.entity.ImgMd5;
import com.royal.service.IImgMd5Service;

/**
* 描述：图片过滤控制层
* @author Royal
* @date 2019年02月17日 18:35:52
*/
@Controller
@RequestMapping("/imgMd5")
public class ImgMd5Controller extends BaseController {

    @Autowired
    private IImgMd5Service imgMd5Service;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(ImgMd5 imgMd5)throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<ImgMd5> list = imgMd5Service.getPage(imgMd5, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("ImgMd5出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  图片过滤id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(ImgMd5 vo)throws Exception {
		try {
			ImgMd5 imgMd5 = imgMd5Service.findById(vo.getImgId ());
			return new Result(imgMd5);
		} catch (Exception e) {
			logger.error("ImgMd5出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建图片过滤
    * @param imgMd5  图片过滤
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(ImgMd5 imgMd5) throws Exception {
		try {
			imgMd5Service.add(imgMd5);
			return new Result();
		} catch (Exception e) {
			logger.error("ImgMd5出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除图片过滤
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(ImgMd5 imgMd5) throws Exception {
		try {
			imgMd5Service.delete(imgMd5);
			return new Result();
		} catch (Exception e) {
			logger.error("ImgMd5出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新图片过滤
    * @param imgMd5 图片过滤id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result updateImgMd5(ImgMd5 imgMd5) throws Exception {
		try {
			imgMd5Service.update(imgMd5);
			return new Result();
		} catch (Exception e) {
			logger.error("ImgMd5出异常了", e);
			return new Result(e);
		}
    }

}