package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.TradingOpportunities;
import com.royal.mapper.TradingOpportunitiesMapper;
import com.royal.service.ITradingOpportunitiesService;


/**
* 描述：交易机会 服务实现层
* @author Royal
* @date 2019年02月14日 21:09:16
*/
@Service
@Transactional
public class TradingOpportunitiesServiceImpl extends BaseServiceImpl<TradingOpportunities> implements ITradingOpportunitiesService {

	private TradingOpportunitiesMapper tradingOpportunitiesMapper;
	
	@Resource
	public void setBaseMapper(TradingOpportunitiesMapper mapper){
		super.setBaseMapper(mapper);
		this.tradingOpportunitiesMapper = mapper;
	}

}