package com.royal.controller;

import com.royal.entity.SymbolOpenClose;
import com.royal.entity.enums.ResultEnum;
import com.royal.service.ISymbolOpenCloseService;
import com.royal.util.DateUtils;
import com.royal.util.MT4Utils;
import com.royal.util.Tools;
import com.royal.util.WebSocketUtil;
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
import com.royal.entity.SymbolInfo;
import com.royal.service.ISymbolInfoService;

import java.util.Date;

/**
 * 描述：交易品种控制层
 *
 * @author Royal
 * @date 2018年12月12日 17:05:27
 */
@Controller
@RequestMapping("/symbolInfo")
public class SymbolInfoController extends BaseController {

    @Autowired
    private ISymbolInfoService symbolInfoService;

    @Autowired
    private ISymbolOpenCloseService symbolOpenCloseService;

    /**
     * 描述：分页 查询
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Result getPage(SymbolInfo symbolInfo) throws Exception {
        try {
            PageData pd = this.getPageData();
            PageInfo<SymbolInfo> list = symbolInfoService.getPage(symbolInfo, pd);
            for (SymbolInfo info : list.getList()) {
                info.setTrading(MT4Utils.isOpen(info));
                SymbolOpenClose symbolOpenClose = symbolOpenCloseService.getBySymbolCode(info.getSymbolCode());
                MT4Utils.setSymbolOpenClose(symbolOpenClose,info);
            }
            return new Result(list);
        } catch (Exception e) {
            logger.error("SymbolInfo出异常了", e);
            return new Result(e);
        }

    }

    /**
     * 描述：根据Id 查询
     *
     * @param vo 交易品种id
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public Result findById(SymbolInfo vo) throws Exception {
        try {
            SymbolInfo symbolInfo = symbolInfoService.findByCode(vo.getSymbolCode());
            if(symbolInfo==null){
                return new Result(ResultEnum.UNKNOWN_INFO);
            }
            symbolInfo.setTrading(MT4Utils.isOpen(symbolInfo));
            SymbolOpenClose symbolOpenClose = symbolOpenCloseService.getBySymbolCode(symbolInfo.getSymbolCode());
            MT4Utils.setSymbolOpenClose(symbolOpenClose,symbolInfo);
            return new Result(symbolInfo);
        } catch (Exception e) {
            logger.error("SymbolInfo出异常了", e);
            return new Result(e);
        }

    }

    /**
     * 描述:创建交易品种
     *
     * @param symbolInfo 交易品种
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Result create(SymbolInfo symbolInfo) throws Exception {
        try {
            symbolInfoService.add(symbolInfo);
            return new Result();
        } catch (Exception e) {
            logger.error("SymbolInfo出异常了", e);
            return new Result(e);
        }
    }

    /**
     * 描述：删除交易品种
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result deleteById(SymbolInfo symbolInfo) throws Exception {
        try {
            symbolInfoService.delete(symbolInfo);
            return new Result();
        } catch (Exception e) {
            logger.error("SymbolInfo出异常了", e);
            return new Result(e);
        }
    }

    /**
     * 描述：更新交易品种
     *
     * @param symbolInfo 交易品种id
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Result updateSymbolInfo(SymbolInfo symbolInfo) throws Exception {
        try {
            symbolInfoService.update(symbolInfo);
            return new Result();
        } catch (Exception e) {
            logger.error("SymbolInfo出异常了", e);
            return new Result(e);
        }
    }

    /**
     * 获取websocket地址
     *
     * @return
     */
    @RequestMapping(value = "/getWebSocketAddress")
    @ResponseBody
    public Result getWebSocketAddress() {
        String sign = Tools.getRandomCode(33, 7);
        WebSocketUtil.addSet(sign, new Date().getTime());
        return new Result(sign);
    }

}