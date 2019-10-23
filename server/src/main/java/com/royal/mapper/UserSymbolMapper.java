package com.royal.mapper;

import com.royal.entity.SymbolInfo;
import com.royal.entity.UserSymbol;
import com.royal.entity.json.PageData;
import com.royal.util.TkMapper;

import java.util.List;

public interface UserSymbolMapper extends TkMapper<UserSymbol> {


    List<SymbolInfo> getMyPage(PageData pd);
}