package com.royal.service;

import com.royal.entity.User;
import com.royal.service.BaseService;

/**
* 描述：用户 服务实现层接口
* @author Royal
* @date 2018年12月04日 14:19:32
*/
public interface IUserService extends BaseService<User> {


    boolean verificationPassword(User user);

    Integer updateByLoginName(User user);
}