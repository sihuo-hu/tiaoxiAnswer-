package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.CashCouponPushLog;
import com.royal.mapper.CashCouponPushLogMapper;
import com.royal.service.ICashCouponPushLogService;


/**
* 描述：代金券发放日志 服务实现层
* @author Royal
* @date 2019年06月27日 09:53:36
*/
@Service
@Transactional
public class CashCouponPushLogServiceImpl extends BaseServiceImpl<CashCouponPushLog> implements ICashCouponPushLogService {

	private CashCouponPushLogMapper cashCouponPushLogMapper;
	
	@Resource
	public void setBaseMapper(CashCouponPushLogMapper mapper){
		super.setBaseMapper(mapper);
		this.cashCouponPushLogMapper = mapper;
	}

}