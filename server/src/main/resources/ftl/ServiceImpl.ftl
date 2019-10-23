package ${package_name}.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package_name}.entity.${table_name};
import ${package_name}.mapper.${table_name}Mapper;
import ${package_name}.service.I${table_name}Service;


/**
* 描述：${table_annotation} 服务实现层
* @author ${author}
* @date ${date}
*/
@Service
@Transactional
public class ${table_name}ServiceImpl extends BaseServiceImpl<${table_name}> implements I${table_name}Service {

	private ${table_name}Mapper ${table_name?uncap_first}Mapper;
	
	@Resource
	public void setBaseMapper(${table_name}Mapper mapper){
		super.setBaseMapper(mapper);
		this.${table_name?uncap_first}Mapper = mapper;
	}

}