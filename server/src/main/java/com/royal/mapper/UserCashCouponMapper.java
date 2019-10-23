package com.royal.mapper;

import com.royal.entity.TransactionRecord;
import com.royal.entity.UserCashCoupon;
import com.royal.util.TkMapper;

import java.util.List;

public interface UserCashCouponMapper extends TkMapper<UserCashCoupon> {


    List<UserCashCoupon> selectUserCashList(UserCashCoupon userCashCoupon);

    List<UserCashCoupon> getUsableList(TransactionRecord transactionRecord);
}