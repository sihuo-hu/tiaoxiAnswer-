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
import com.royal.entity.Switch;
import com.royal.service.ISwitchService;

/**
 * 描述：开关控制层
 *
 * @author Royal
 * @date 2019年02月21日 16:36:03
 */
@Controller
@RequestMapping("/switch")
public class SwitchController extends BaseController {

    @Autowired
    private ISwitchService switchService;

    /**
     * 描述：分页 查询
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Result getPage(Switch sw) throws Exception {
        try {
            PageData pd = this.getPageData ();
            PageInfo<Switch> list = switchService.getPage (sw, pd);
            return new Result (list);
        } catch (Exception e) {
            logger.error ("Switch出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述：根据Id 查询
     *
     * @param vo 开关id
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public Result findById(Switch vo) throws Exception {
        try {
            Switch sw = switchService.findById (vo.getId ());
            return new Result (sw);
        } catch (Exception e) {
            logger.error ("Switch出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述：根据Id 查询
     *
     * @param vo 开关id
     */
    @RequestMapping(value = "/getInfo")
    @ResponseBody
    public Result getInfo(Switch vo) throws Exception {
        try {
            Switch sw = switchService.selectByPlatform(vo);
            return new Result (sw);
        } catch (Exception e) {
            logger.error ("Switch出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述:创建开关
     *
     * @param sw 开关
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Result create(Switch sw) throws Exception {
        try {
            switchService.add (sw);
            return new Result ();
        } catch (Exception e) {
            logger.error ("Switch出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：删除开关
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result deleteById(Switch sw) throws Exception {
        try {
            switchService.delete (sw);
            return new Result ();
        } catch (Exception e) {
            logger.error ("Switch出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：更新开关
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Result updateSwitch(Switch sw) throws Exception {
        try {
            switchService.update (sw);
            return new Result ();
        } catch (Exception e) {
            logger.error ("Switch出异常了", e);
            return new Result (e);
        }
    }

}