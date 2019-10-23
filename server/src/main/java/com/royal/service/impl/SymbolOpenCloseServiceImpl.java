package com.royal.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.royal.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.SymbolOpenClose;
import com.royal.mapper.SymbolOpenCloseMapper;
import com.royal.service.ISymbolOpenCloseService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


/**
 * 描述：产品昨收今开 服务实现层
 *
 * @author Royal
 * @date 2019年01月07日 12:32:36
 */
@Service
@Transactional
public class SymbolOpenCloseServiceImpl extends BaseServiceImpl<SymbolOpenClose> implements ISymbolOpenCloseService {

    private SymbolOpenCloseMapper symbolOpenCloseMapper;

    @Resource
    public void setBaseMapper(SymbolOpenCloseMapper mapper) {
        super.setBaseMapper(mapper);
        this.symbolOpenCloseMapper = mapper;
    }

    /**
     * 根据编码及日期获取昨收今开
     *
     * @param symbolCode
     * @return
     */
    @Override
    public SymbolOpenClose getBySymbolCode(String symbolCode) {
        Example ex = new Example(SymbolOpenClose.class);
        ex.setOrderByClause("create_time DESC");
        ex.createCriteria().andEqualTo("symbolCode", symbolCode).andIsNotNull("open").andIsNotNull("close").andIsNotNull("minPrice").andIsNotNull("maxPrice");
        Page<Object> ph = PageHelper.startPage(1, 1);
        return symbolOpenCloseMapper.selectOneByExample(ex);
    }

    /**
     * 获取最后一天记录的时间
     *
     * @return
     */
    @Override
    public String getLastCreateTime() {
        return symbolOpenCloseMapper.getLastCreateTime();
    }

    @Override
    public List<SymbolOpenClose> findByCreateTime(String createTime) {
        Example ex = new Example(SymbolOpenClose.class);
        ex.createCriteria().andEqualTo("createTime", createTime);
        return symbolOpenCloseMapper.selectByExample(ex);
    }

    @Override
    public SymbolOpenClose getBySymbolCodeAndDate(String symbolCode) {
        Example ex = new Example(SymbolOpenClose.class);
        ex.setOrderByClause("create_time DESC");
        ex.createCriteria().andEqualTo("symbolCode", symbolCode).andEqualTo("createTime",DateUtils.getFormatDate(new Date()));
        Page<Object> ph = PageHelper.startPage(1, 1);
        return symbolOpenCloseMapper.selectOneByExample(ex);
    }
}