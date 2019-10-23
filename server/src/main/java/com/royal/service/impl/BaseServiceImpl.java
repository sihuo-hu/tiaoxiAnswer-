package com.royal.service.impl;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.User;
import com.royal.entity.json.PageData;
import com.royal.service.BaseService;
import com.royal.util.TkMapper;

import tk.mybatis.mapper.entity.Example;

public class BaseServiceImpl<T> implements BaseService<T> {

    protected TkMapper<T> baseMapper;

    public void setBaseMapper(TkMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public Integer add(T entity) {
        return baseMapper.insertSelective(entity);
    }

    @Override
    public Integer deleteByIdList(List<String> ids, Class clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn("id", ids);
        return baseMapper.deleteByExample(example);
    }

    @Override
    public Integer update(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public T findById(Serializable id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> getAll() {
        return baseMapper.selectAll();
    }

    @Override
    public PageInfo<T> getPage(T entity, PageData pd) {
        Page<Object> ph = PageHelper.startPage(pd.getPage(),pd.getPageSize());
        PageInfo<T> list = new PageInfo<T>(baseMapper.select(entity));
        return list;
    }

    @Override
    public PageInfo<T> getPageOrderByCreateTime(T entity, PageData pd) {
        Page<Object> ph = PageHelper.startPage(pd.getPage(),pd.getPageSize());
        Example ex = new Example(entity.getClass ());
        ex.setOrderByClause ("create_time DESC");
        PageInfo<T> list = new PageInfo<T>(baseMapper.selectByExample (ex));
        return list;
    }

    @Override
	public Integer addList(List<T> list) {
		return baseMapper.insertList(list);
	}

    @Override
    public Integer delete(T entity) {
        return baseMapper.delete(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public T findByLoginName(T entity, String loginName) {
        Example ex = new Example(entity.getClass());
        ex.createCriteria().andEqualTo("loginName",loginName);
        return baseMapper.selectOneByExample(ex);
    }

}
