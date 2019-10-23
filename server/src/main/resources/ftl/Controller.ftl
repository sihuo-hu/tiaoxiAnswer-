package ${package_name}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${package_name}.entity.json.Result;
import ${package_name}.entity.json.PageData;
import ${package_name}.entity.${table_name};
import ${package_name}.service.I${table_name}Service;

/**
* 描述：${table_annotation}控制层
* @author ${author}
* @date ${date}
*/
@Controller
@RequestMapping("/${table_name?uncap_first}")
public class ${table_name}Controller extends BaseController {

    @Autowired
    private I${table_name}Service ${table_name?uncap_first}Service;
    
     /**
    * 描述：分页 查询
    */
    @RequestMapping(value = "/getList")
	@ResponseBody
    public Result getPage(${table_name} ${table_name?uncap_first})throws Exception {
		try {
			PageData pd = this.getPageData();
			PageInfo<${table_name}> list = ${table_name?uncap_first}Service.getPage(${table_name?uncap_first}, pd);
			return new Result(list);
		} catch (Exception e) {
			logger.error("${table_name}出异常了", e);
			return new Result(e);
		}
    
    }

    /**
    * 描述：根据Id 查询
    * @param vo  ${table_annotation}id
    */
    @RequestMapping(value = "/get")
	@ResponseBody
    public Result findById(${table_name} vo)throws Exception {
		try {
			${table_name} ${table_name?uncap_first} = ${table_name?uncap_first}Service.findById(vo.getId());
			return new Result(${table_name?uncap_first});
		} catch (Exception e) {
			logger.error("${table_name}出异常了", e);
    		return new Result(e);
		}
    
    }

    /**
    * 描述:创建${table_annotation}
    * @param ${table_name?uncap_first}  ${table_annotation}
    */
    @RequestMapping(value = "/save")
	@ResponseBody
    public Result create(${table_name} ${table_name?uncap_first}) throws Exception {
		try {
			${table_name?uncap_first}Service.add(${table_name?uncap_first});
			return new Result();
		} catch (Exception e) {
			logger.error("${table_name}出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：删除${table_annotation}
    */
     @RequestMapping(value = "/delete")
	@ResponseBody
    public Result deleteById(${table_name} ${table_name?uncap_first}) throws Exception {
		try {
			${table_name?uncap_first}Service.delete(${table_name?uncap_first});
			return new Result();
		} catch (Exception e) {
			logger.error("${table_name}出异常了", e);
			return new Result(e);
		}
    }

    /**
    * 描述：更新${table_annotation}
    * @param ${table_name?uncap_first} ${table_annotation}id
    */
    @RequestMapping(value = "/edit")
	@ResponseBody
    public Result update${table_name}(${table_name} ${table_name?uncap_first}) throws Exception {
		try {
			${table_name?uncap_first}Service.update(${table_name?uncap_first});
			return new Result();
		} catch (Exception e) {
			logger.error("${table_name}出异常了", e);
			return new Result(e);
		}
    }

}