package com.royal.controller;

import com.royal.entity.enums.ResultEnum;
import com.royal.util.JwtUtil;
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
import com.royal.entity.TransactionRecord;
import com.royal.service.ITransactionRecordService;

/**
 * 描述：交易记录表控制层
 *
 * @author Royal
 * @date 2018年12月13日 22:47:22
 */
@Controller
@RequestMapping("/transactionRecord")
public class TransactionRecordController extends BaseController {


    @Autowired
    private ITransactionRecordService transactionRecordService;

    /**
     * 描述：分页 查询
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public Result getPage(TransactionRecord transactionRecord) throws Exception {
        try {
            PageData pd = this.getPageData ();
            PageInfo<TransactionRecord> list = transactionRecordService.getMyPage (transactionRecord, pd);
            return new Result (list);
        } catch (Exception e) {
            logger.error ("TransactionRecord出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述：根据Id 查询
     *
     * @param vo 交易记录表id
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public Result findById(TransactionRecord vo) throws Exception {
        try {
            TransactionRecord transactionRecord = transactionRecordService.findById (vo.getId ());
            return new Result (transactionRecord);
        } catch (Exception e) {
            logger.error ("TransactionRecord出异常了", e);
            return new Result (e);
        }

    }

    /**
     * 描述:创建交易记录表
     *
     * @param transactionRecord 交易记录表
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Result create(TransactionRecord transactionRecord) throws Exception {
        try {
            transactionRecordService.add (transactionRecord);
            return new Result ();
        } catch (Exception e) {
            logger.error ("TransactionRecord出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：删除交易记录表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result deleteById(TransactionRecord transactionRecord) throws Exception {
        try {
            transactionRecordService.delete (transactionRecord);
            return new Result ();
        } catch (Exception e) {
            logger.error ("TransactionRecord出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 描述：更新交易记录表
     *
     * @param transactionRecord 交易记录表id
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Result updateTransactionRecord(TransactionRecord transactionRecord) throws Exception {
        try {
            transactionRecordService.update (transactionRecord);
            return new Result ();
        } catch (Exception e) {
            logger.error ("TransactionRecord出异常了", e);
            return new Result (e);
        }
    }

    /**
     * 设置是否过夜
     *
     * @param transactionRecord
     * @return
     */
    @RequestMapping(value = "/updateOvernight")
    @ResponseBody
    public Result updateOvernight(TransactionRecord transactionRecord) throws Exception {
        String loginName = JwtUtil.getUser (getRequest ());
        TransactionRecord tr = transactionRecordService.findById (transactionRecord.getId ());
        if (!loginName.equals (tr.getLoginName ())) {
            return new Result (ResultEnum.ILLEGALITY);
        }
        if(transactionRecord.getIsOvernight()==1){
            return transactionRecordService.overnight(tr);
        }else if(transactionRecord.getIsOvernight()==2){
            return transactionRecordService.notOvernight(tr);
        }else{
            return new Result (ResultEnum.PARAMETER_ERROR);
        }
    }

}