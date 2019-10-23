package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.Switch;
import com.royal.mapper.SwitchMapper;
import com.royal.service.ISwitchService;


/**
* 描述：开关 服务实现层
* @author Royal
* @date 2019年02月21日 16:36:03
*/
@Service
@Transactional
public class SwitchServiceImpl extends BaseServiceImpl<Switch> implements ISwitchService {

	private SwitchMapper switchMapper;
	
	@Resource
	public void setBaseMapper(SwitchMapper mapper){
		super.setBaseMapper(mapper);
		this.switchMapper = mapper;
	}

	@Override
	public Switch selectByPlatform(Switch vo) {
		return switchMapper.selectOne(vo);
	}
}