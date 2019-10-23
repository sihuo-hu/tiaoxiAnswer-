package com.royal.service;

import com.royal.entity.Switch;

/**
* 描述：开关 服务实现层接口
* @author Royal
* @date 2019年02月21日 16:36:03
*/
public interface ISwitchService extends BaseService<Switch> {


    Switch selectByPlatform(Switch vo);
}