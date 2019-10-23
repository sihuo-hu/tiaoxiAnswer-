package com.royal.service;

import com.royal.entity.UserAmount;
import com.royal.service.BaseService;

/**
* 描述：用户资金 服务实现层接口
* @author Royal
* @date 2018年12月13日 17:16:02
*/
public interface IUserAmountService extends BaseService<UserAmount> {


    void deleteByLoginName(String loginName);
}