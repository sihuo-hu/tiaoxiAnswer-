package com.royal.service;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.royal.entity.User;
import com.royal.entity.json.PageData;

public interface BaseService<T> {

    public Integer add(T entity);// 保存

    public Integer deleteByIdList(List<String> ids, Class clazz);// 批量删除

    public Integer update(T entity);// 更新

    public T findById(Serializable id);// 根据主键查找

    public List<T> getAll();// 查看所有

    public PageInfo<T> getPage(T entity, PageData pd);

    public PageInfo<T> getPageOrderByCreateTime(T entity, PageData pd);

    public Integer addList(List<T> list);

    public Integer delete(T entity);

    public Integer deleteById(Integer id);

    T findByLoginName(T entity, String loginName);
}
