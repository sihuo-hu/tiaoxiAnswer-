package com.royal.service.impl;

import javax.annotation.Resource;

import com.royal.util.Constants;
import com.royal.util.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.User;
import com.royal.mapper.UserMapper;
import com.royal.service.IUserService;
import tk.mybatis.mapper.entity.Example;


/**
 * 描述：用户 服务实现层
 *
 * @author Royal
 * @date 2018年12月04日 14:19:32
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    private UserMapper userMapper;

    @Resource
    public void setBaseMapper(UserMapper mapper) {
        super.setBaseMapper(mapper);
        this.userMapper = mapper;
    }

    /**
     * 验证密码
     *
     * @param user
     * @return
     */
    @Override
    public boolean verificationPassword(User user) {
        Example ex = new Example(User.class);
        ex.createCriteria().andEqualTo("loginName",
                user.getLoginName()).andEqualTo("password", user.getPassword());
        User u = userMapper.selectOneByExample(ex);
        if (u != null && !Tools.isEmpty(u.getId())) {
            return true;
        }
        return false;
    }

    /**
     * 根据loginName更新不为空的字段
     *
     * @param user
     * @return
     */
    @Override
    public Integer updateByLoginName(User user) {
        Example ex = new Example(User.class);
        ex.createCriteria().andEqualTo("loginName", user.getLoginName());
        return userMapper.updateByExampleSelective(user, ex);
    }
}