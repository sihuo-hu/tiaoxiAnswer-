package com.royal.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.SymbolInfo;
import com.royal.entity.json.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.UserSymbol;
import com.royal.mapper.UserSymbolMapper;
import com.royal.service.IUserSymbolService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
* 描述：自选产品 服务实现层
* @author Royal
* @date 2019年01月02日 12:48:23
*/
@Service
@Transactional
public class UserSymbolServiceImpl extends BaseServiceImpl<UserSymbol> implements IUserSymbolService {

	private UserSymbolMapper userSymbolMapper;
	
	@Resource
	public void setBaseMapper(UserSymbolMapper mapper){
		super.setBaseMapper(mapper);
		this.userSymbolMapper = mapper;
	}

	@Override
	public PageInfo<SymbolInfo> getMyPage(PageData pd) {
		Page<Object> ph = PageHelper.startPage(pd.getPage(),pd.getPageSize());
		List<SymbolInfo> list = userSymbolMapper.getMyPage(pd);
		return new PageInfo<SymbolInfo>(list);
	}

	@Override
	public UserSymbol findByCode(UserSymbol userSymbol) {
		Example ex = new Example (UserSymbol.class);
		ex.createCriteria ().andEqualTo ("loginName",userSymbol.getLoginName ()).andEqualTo ("symbolCode",
				userSymbol.getSymbolCode ());
		return userSymbolMapper.selectOneByExample (ex);
	}


}