package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.SymbolInfo;
import com.royal.mapper.SymbolInfoMapper;
import com.royal.service.ISymbolInfoService;
import tk.mybatis.mapper.entity.Example;


/**
* 描述：交易品种 服务实现层
* @author Royal
* @date 2018年12月12日 17:05:27
*/
@Service
@Transactional
public class SymbolInfoServiceImpl extends BaseServiceImpl<SymbolInfo> implements ISymbolInfoService {

	private SymbolInfoMapper symbolInfoMapper;
	
	@Resource
	public void setBaseMapper(SymbolInfoMapper mapper){
		super.setBaseMapper(mapper);
		this.symbolInfoMapper = mapper;
	}

	@Override
	public SymbolInfo findByCode(String symbolCode) {
		Example ex= new Example(SymbolInfo.class);
		ex.createCriteria().andEqualTo("symbolCode",symbolCode);
		return symbolInfoMapper.selectOneByExample(ex);
	}
}