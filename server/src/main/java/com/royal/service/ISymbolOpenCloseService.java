package com.royal.service;

import com.royal.entity.SymbolOpenClose;

import java.util.List;

/**
* 描述：产品昨收今开 服务实现层接口
* @author Royal
* @date 2019年01月07日 12:32:36
*/
public interface ISymbolOpenCloseService extends BaseService<SymbolOpenClose> {


    SymbolOpenClose getBySymbolCode(String symbolCode);

    String getLastCreateTime();

    List<SymbolOpenClose> findByCreateTime(String createTime);

    SymbolOpenClose getBySymbolCodeAndDate(String symbolCode);
}