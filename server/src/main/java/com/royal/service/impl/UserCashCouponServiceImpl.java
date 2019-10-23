package com.royal.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.TransactionRecord;
import com.royal.entity.json.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.UserCashCoupon;
import com.royal.mapper.UserCashCouponMapper;
import com.royal.service.IUserCashCouponService;

import java.util.List;


/**
* 描述：用户代金券 服务实现层
* @author Royal
* @date 2019年05月23日 19:41:18
*/
@Service
@Transactional
public class UserCashCouponServiceImpl extends BaseServiceImpl<UserCashCoupon> implements IUserCashCouponService {

	private UserCashCouponMapper userCashCouponMapper;
	
	@Resource
	public void setBaseMapper(UserCashCouponMapper mapper){
		super.setBaseMapper(mapper);
		this.userCashCouponMapper = mapper;
	}

	@Override
	public PageInfo<UserCashCoupon> getMyPage(UserCashCoupon userCashCoupon, PageData pd) {
		Page<Object> ph = PageHelper.startPage(pd.getPage(),pd.getPageSize());
		PageInfo<UserCashCoupon> list = new PageInfo<UserCashCoupon>(userCashCouponMapper.selectUserCashList(userCashCoupon));
		return list;
	}

	@Override
	public List<UserCashCoupon> getUsableList(TransactionRecord transactionRecord) {
		return userCashCouponMapper.getUsableList(transactionRecord);
	}
}