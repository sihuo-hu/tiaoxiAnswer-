package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.Version;
import com.royal.mapper.VersionMapper;
import com.royal.service.IVersionService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * 描述：版本控制 服务实现层
 *
 * @author Royal
 * @date 2019年03月30日 22:56:30
 */
@Service
@Transactional
public class VersionServiceImpl extends BaseServiceImpl<Version> implements IVersionService {

    private VersionMapper versionMapper;

    @Resource
    public void setBaseMapper(VersionMapper mapper) {
        super.setBaseMapper(mapper);
        this.versionMapper = mapper;
    }

    @Override
    public Version getInfo(Version vo) {
        Example ex = new Example(vo.getClass());
        ex.createCriteria().andEqualTo("platform", vo.getPlatform()).andGreaterThan("version", vo.getVersion());
        ex.setOrderByClause("version DESC");
        List<Version> list = versionMapper.selectByExample(ex);
        vo.setUploadType("0");
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Version version = list.get(i);
                if (i == 0) {
                    vo = version;
                }else{
                    if(version.getUploadType().equals("1")){
                        vo.setUploadType("1");
                    }
                }
            }
        }
        return vo;
    }
}