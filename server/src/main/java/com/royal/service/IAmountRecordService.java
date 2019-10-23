package com.royal.service;

import com.github.pagehelper.PageInfo;
import com.royal.entity.AmountRecord;
import com.royal.entity.json.PageData;

import java.util.Date;

/**
* 描述：资金记录 服务实现层接口
* @author Royal
* @date 2019年03月05日 13:28:09
*/
public interface IAmountRecordService extends BaseService<AmountRecord> {


    PageInfo<AmountRecord> getPageByAmount(AmountRecord vo, PageData pd);

    void updatePayStatus(String date);

    void selectPayStatus();
}