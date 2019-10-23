package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.SymbolRecordM1;
import com.royal.mapper.SymbolRecordM1Mapper;
import com.royal.service.ISymbolRecordM1Service;


/**
* 描述：产品历史记录 服务实现层
* @author Royal
* @date 2019年07月01日 16:39:01
*/
@Service
@Transactional
public class SymbolRecordM1ServiceImpl extends BaseServiceImpl<SymbolRecordM1> implements ISymbolRecordM1Service {

	private SymbolRecordM1Mapper symbolRecordM1Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordM1Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordM1Mapper = mapper;
	}

}