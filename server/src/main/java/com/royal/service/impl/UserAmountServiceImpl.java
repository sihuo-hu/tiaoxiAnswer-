package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.UserAmount;
import com.royal.mapper.UserAmountMapper;
import com.royal.service.IUserAmountService;


/**
* 描述：用户资金 服务实现层
* @author Royal
* @date 2018年12月13日 17:16:02
*/
@Service
@Transactional
public class UserAmountServiceImpl extends BaseServiceImpl<UserAmount> implements IUserAmountService {

	private UserAmountMapper userAmountMapper;
	
	@Resource
	public void setBaseMapper(UserAmountMapper mapper){
		super.setBaseMapper(mapper);
		this.userAmountMapper = mapper;
	}

	@Override
	public void deleteByLoginName(String loginName) {
		userAmountMapper.deleteByLoginName(loginName);
	}
}