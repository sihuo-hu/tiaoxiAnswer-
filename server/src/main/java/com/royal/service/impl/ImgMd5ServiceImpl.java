package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.ImgMd5;
import com.royal.mapper.ImgMd5Mapper;
import com.royal.service.IImgMd5Service;


/**
* 描述：图片过滤 服务实现层
* @author Royal
* @date 2019年02月17日 18:35:52
*/
@Service
@Transactional
public class ImgMd5ServiceImpl extends BaseServiceImpl<ImgMd5> implements IImgMd5Service {

	private ImgMd5Mapper imgMd5Mapper;
	
	@Resource
	public void setBaseMapper(ImgMd5Mapper mapper){
		super.setBaseMapper(mapper);
		this.imgMd5Mapper = mapper;
	}

}