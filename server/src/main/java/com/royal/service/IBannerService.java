package com.royal.service;

import com.github.pagehelper.PageInfo;
import com.royal.entity.Banner;
import com.royal.entity.json.PageData;

/**
* 描述：banner图 服务实现层接口
* @author Royal
* @date 2018年12月06日 16:24:12
*/
public interface IBannerService extends BaseService<Banner> {


    PageInfo<Banner> getPageBySort(PageData pd);
}