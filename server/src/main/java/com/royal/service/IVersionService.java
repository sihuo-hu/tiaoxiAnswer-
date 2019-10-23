package com.royal.service;

import com.royal.entity.Version;

/**
* 描述：版本控制 服务实现层接口
* @author Royal
* @date 2019年03月30日 22:56:30
*/
public interface IVersionService extends BaseService<Version> {


    Version getInfo(Version vo);
}