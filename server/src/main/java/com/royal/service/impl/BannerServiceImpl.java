package com.royal.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.json.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.Banner;
import com.royal.mapper.BannerMapper;
import com.royal.service.IBannerService;
import tk.mybatis.mapper.entity.Example;


/**
* 描述：banner图 服务实现层
* @author Royal
* @date 2018年12月06日 16:24:12
*/
@Service
@Transactional
public class BannerServiceImpl extends BaseServiceImpl<Banner> implements IBannerService {

	private BannerMapper bannerMapper;
	
	@Resource
	public void setBaseMapper(BannerMapper mapper){
		super.setBaseMapper(mapper);
		this.bannerMapper = mapper;
	}

	/**
	 * 分页获取banner图列表
	 * @param pd
	 * @return
	 */
	@Override
	public PageInfo<Banner> getPageBySort(PageData pd) {
		Page<Object> ph = PageHelper.startPage(pd.getPage(),pd.getPageSize());
		Example ex = new Example(Banner.class);
		ex.setOrderByClause("sort_no ASC");
		PageInfo<Banner> list = new PageInfo<Banner>(bannerMapper.selectByExample(ex));
		return list;
	}

}