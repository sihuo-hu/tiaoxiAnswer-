package ${package_name}.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 描述：${table_annotation}模型
* @author ${author}
* @date ${date}
*/
@Table(name="${table_name_small}")
public class ${table_name} implements Serializable {

    <#if model_column?exists>
        <#list model_column as model>
    /**
    *${model.columnComment!}
    */
    <#if (model.columnName = 'id')>
   	@Id
	@GeneratedValue(generator = "JDBC")
    </#if>
    <#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT' || model.columnType = 'MEDIUMTEXT')>
    @Column(name = "${model.columnName}")
    private String ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'TIMESTAMP' || model.columnType = 'DATETIME'>
    @Column(name = "${model.columnName}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'BIGINT' >
    @Column(name = "${model.columnName}")
    private Long ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'INT' || model.columnType = 'TINYINT' || model.columnType = 'BIT'>
    @Column(name = "${model.columnName}")
    private Integer ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'DECIMAL' >
    @Column(name = "${model.columnName}")
    private BigDecimal ${model.changeColumnName?uncap_first};
    </#if>
        </#list>
    </#if>

<#if model_column?exists>
<#list model_column as model>

<#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT' || model.columnType = 'MEDIUMTEXT')>
     public String get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(String ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if model.columnType = 'TIMESTAMP' || model.columnType = 'DATETIME'>
     public Date get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Date ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if model.columnType = 'BIGINT' >
    public Long get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Long ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if model.columnType = 'INT' || model.columnType = 'TINYINT' || model.columnType = 'BIT'>
    public Integer get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(Integer ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>
    <#if model.columnType = 'DECIMAL' >
     public BigDecimal get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(BigDecimal ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    </#if>

</#list>
</#if>

}