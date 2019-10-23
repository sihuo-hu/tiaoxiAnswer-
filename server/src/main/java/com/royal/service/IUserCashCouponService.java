package com.royal.service;

import com.github.pagehelper.PageInfo;
import com.royal.entity.TransactionRecord;
import com.royal.entity.UserCashCoupon;
import com.royal.entity.json.PageData;

import java.util.List;

/**
* 描述：用户代金券 服务实现层接口
* @author Royal
* @date 2019年05月23日 19:41:18
*/
public interface IUserCashCouponService extends BaseService<UserCashCoupon> {


    PageInfo<UserCashCoupon> getMyPage(UserCashCoupon userCashCoupon, PageData pd);

    List<UserCashCoupon> getUsableList(TransactionRecord transactionRecord);
}