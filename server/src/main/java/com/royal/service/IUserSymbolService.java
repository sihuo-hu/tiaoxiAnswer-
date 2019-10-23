package com.royal.service;

import com.github.pagehelper.PageInfo;
import com.royal.entity.SymbolInfo;
import com.royal.entity.UserSymbol;
import com.royal.entity.json.PageData;

/**
* 描述：自选产品 服务实现层接口
* @author Royal
* @date 2019年01月02日 12:48:23
*/
public interface IUserSymbolService extends BaseService<UserSymbol> {


    PageInfo<SymbolInfo> getMyPage(PageData pd);

    UserSymbol findByCode(UserSymbol userSymbol);
}