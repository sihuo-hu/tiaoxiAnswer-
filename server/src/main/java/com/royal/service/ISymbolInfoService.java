package com.royal.service;

import com.royal.entity.SymbolInfo;

/**
* 描述：交易品种 服务实现层接口
* @author Royal
* @date 2018年12月12日 17:05:27
*/
public interface ISymbolInfoService extends BaseService<SymbolInfo> {


    SymbolInfo findByCode(String symbolCode);
}