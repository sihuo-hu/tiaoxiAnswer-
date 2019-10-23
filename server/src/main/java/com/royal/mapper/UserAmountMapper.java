package com.royal.mapper;

import com.royal.entity.UserAmount;
import com.royal.util.TkMapper;

public interface UserAmountMapper extends TkMapper<UserAmount> {


    void deleteByLoginName(String loginName);
}