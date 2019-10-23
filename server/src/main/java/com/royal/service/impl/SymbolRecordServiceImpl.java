package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.SymbolRecord;
import com.royal.mapper.SymbolRecordMapper;
import com.royal.service.ISymbolRecordService;


/**
* 描述：产品价格记录 服务实现层
* @author Royal
* @date 2018年12月25日 21:45:16
*/
@Service
@Transactional
public class SymbolRecordServiceImpl extends BaseServiceImpl<SymbolRecord> implements ISymbolRecordService {

	private SymbolRecordMapper symbolRecordMapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordMapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordMapper = mapper;
	}

}