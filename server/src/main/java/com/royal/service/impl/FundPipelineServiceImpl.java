package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.FundPipeline;
import com.royal.mapper.FundPipelineMapper;
import com.royal.service.IFundPipelineService;


/**
* 描述：资金流水日志 服务实现层
* @author Royal
* @date 2019年06月18日 16:36:40
*/
@Service
@Transactional
public class FundPipelineServiceImpl extends BaseServiceImpl<FundPipeline> implements IFundPipelineService {

	private FundPipelineMapper fundPipelineMapper;
	
	@Resource
	public void setBaseMapper(FundPipelineMapper mapper){
		super.setBaseMapper(mapper);
		this.fundPipelineMapper = mapper;
	}

}