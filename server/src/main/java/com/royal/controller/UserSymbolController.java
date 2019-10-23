package com.royal.controller;

import com.royal.entity.SymbolInfo;
import com.royal.entity.SymbolOpenClose;
import com.royal.entity.enums.ResultEnum;
import com.royal.service.ISymbolOpenCloseService;
import com.royal.util.JwtUtil;
import com.royal.util.MT4Utils;
import com.royal.util.Tools;
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
import com.royal.entity.UserSymbol;
import com.royal.service.IUserSymbolService;

/**
 * 描述：自选产品控制层
 *
 * @author Royal
 * @date 2019年01月02日 12:48:23
 */
@Controller
@RequestMapping("/userSymbol")
public class UserSymbolController extends BaseController {

    @Autowired
    private IUserSymbolService userSymbolService;
    @Autowired
    private ISymbolOpenCloseService symbolOpenCloseService;

    /**
     * 描述：分页 查询
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Result getPage(UserSymbol userSymbol) throws Exception {
        try {
            PageData pd = this.getPageData ();
            String loginName = JwtUtil.getUser (this.getRequest ());
            pd.put ("loginName", loginName);
            PageInfo<SymbolInfo> list = userSymbolService.getMyPage (pd);
            for (SymbolInfo info : list.getList ()) {
                info.setTrading(MT4Utils.isOpen(info));
                SymbolOpenClose symbolOpenClose = symbolOpenCloseService.getBySymbolCode(info.getSymbolCode());
                MT4Utils.setSymbolOpenClose(symbolOpenClose,info);
            }
            return new Result (list);
        } catch (Exception e) {
            logger.error ("UserSymbol出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述：根据Id 查询
     *
     * @param vo 自选产品id
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public Result findById(UserSymbol vo) throws Exception {
        try {
            UserSymbol userSymbol = userSymbolService.findById (vo.getId ());
            return new Result (userSymbol);
        } catch (Exception e) {
            logger.error ("UserSymbol出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述:创建自选产品
     *
     * @param userSymbol 自选产品
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Result create(UserSymbol userSymbol) throws Exception {
        try {
            if (Tools.isEmpty (userSymbol.getSymbolCode ())) {
                return new Result (ResultEnum.PARAMETER_ERROR);
            }
            String loginName = JwtUtil.getUser (this.getRequest ());
            userSymbol.setLoginName (loginName);
            UserSymbol us = userSymbolService.findByCode (userSymbol);
            if (us != null && us.getId () != null) {
                return new Result (ResultEnum.REPETITIVE_OPERATION_ERROR);
            }
            userSymbolService.add (userSymbol);
            return new Result (true);
        } catch (Exception e) {
            logger.error ("UserSymbol出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：删除自选产品
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result deleteById(UserSymbol userSymbol) throws Exception {
        try {
            if (Tools.isEmpty (userSymbol.getSymbolCode ())) {
                return new Result (ResultEnum.PARAMETER_ERROR);
            }
            String loginName = JwtUtil.getUser (this.getRequest ());
            userSymbol.setLoginName (loginName);
            UserSymbol us = userSymbolService.findByCode (userSymbol);
            userSymbolService.delete (us);
            return new Result (true);
        } catch (Exception e) {
            logger.error ("UserSymbol出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：更新自选产品
     *
     * @param userSymbol 自选产品id
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Result updateUserSymbol(UserSymbol userSymbol) throws Exception {
        try {
            userSymbolService.update (userSymbol);
            return new Result ();
        } catch (Exception e) {
            logger.error ("UserSymbol出异常了", e);
            return new Result (e);
        }
    }

}