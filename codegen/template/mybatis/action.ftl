/*******************************************************************************
 * Licensed Materials - Property of ${globalVariables.company?cap_first} 
 * 
 * (C) Copyright ${globalVariables.company?cap_first} Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package ${mainTable.variables.package}.action;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import ${mainTable.variables.package}.model.${mainTable.variables.class};
import ${mainTable.variables.package}.service.${mainTable.variables.class}Service;
<#if mainTable.sub>
import ${mainTable.variables.package}.service.${mainTable.parentClassName}Service;
</#if>
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;

/**
 * @author ${globalVariables.developer}
 * 
 *         Created on ${.now}
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
<#if mainTable.sub>
@Namespace("/${mainTable.namespace}/${mainTable.variables.class?uncap_first}")
	<#if mainTable.relation=="1:1">
@Results(value = { @Result(name = "edit", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}Edit.jsp"),
		@Result(name = "view", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}View.jsp") })
	<#elseif mainTable.relation=="1:n">
@Results(value = {
		@Result(name = "edit", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}Edit.jsp"),
		@Result(name = "sublist", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}List.jsp"), })
	</#if>
<#else><#--主表-->
@Namespace("/${mainTable.namespace}")
@Results(value = { 
	@Result(name = "list", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}List.jsp"),
	@Result(name = "add", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}Edit.jsp"),
	@Result(name = "edit", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}Edit.jsp"),
	@Result(name = "view", location = "/${mainTable.variables.page}/${mainTable.variables.class?uncap_first}View.jsp")
})
</#if>
public class ${mainTable.variables.class}Action extends BaseAction {

	@Resource
	private ${mainTable.variables.class}Service ${mainTable.variables.class?uncap_first}Service;
	
	private ${mainTable.variables.class} ${mainTable.variables.class?uncap_first};
	
	private Long[] ${mainTable.variables.class?uncap_first}IDs;
	
	<#if mainTable.sub>
	@Resource
	private ${mainTable.parentClassName}Service ${mainTable.parentClassName?uncap_first}Service;
	
	private Long ${mainTable.parentClassName?uncap_first}Id;
		<#if mainTable.relation=="1:1"><#-- 无需特殊处理 -->
		<#elseif mainTable.relation=="1:n">
	private Long ${mainTable.variables.class?uncap_first}Id;
		</#if>
	</#if>
	<#-- 保存方法-->
	<#if mainTable.sub><#--子表-->
	@Action("save")
	public String save() throws Exception {
		try {
			${mainTable.variables.class?uncap_first}.set${mainTable.parentClassName}Id(${mainTable.parentClassName?uncap_first}Id);
			${mainTable.variables.class?uncap_first}Service.doSave${mainTable.variables.class}(${mainTable.variables.class?uncap_first});
			<#if mainTable.relation=="1:1">
			printSuccess(${mainTable.variables.class?uncap_first});
			<#elseif mainTable.relation=="1:n">
			printJSON("success", false, String.valueOf(${mainTable.variables.class?uncap_first}.getId()));
			</#if>
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	<#else><#--主表-->
	@Action("save")
	public String save() throws Exception{
	    try {
			${mainTable.variables.class?uncap_first}Service.doSave${mainTable.variables.class}(${mainTable.variables.class?uncap_first});
			printSuccess(${mainTable.variables.class?uncap_first});
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
	    return NONE;
	}
	</#if>
	<#--编辑方法-->
	<#if mainTable.sub>
		<#if mainTable.relation=="1:1">
	@Action("edit")
	public String edit() {
		${mainTable.variables.class?uncap_first} = ${mainTable.variables.class?uncap_first}Service.find${mainTable.variables.class}By${mainTable.parentClassName}Id(${mainTable.parentClassName?uncap_first}Id);
		if (${mainTable.variables.class?uncap_first} == null) {
			${mainTable.variables.class?uncap_first} = new ${mainTable.variables.class}();
		}
		return "edit";
	}
		<#elseif mainTable.relation=="1:n">
	@Action("edit")
	public String edit() {
		${mainTable.variables.class?uncap_first} = ${mainTable.variables.class?uncap_first}Service.find${mainTable.variables.class}ById(${mainTable.variables.class?uncap_first}Id);
		return "edit";
	}
		</#if>
	<#else>
	@Action("edit")
	public String edit() {
	    this.${mainTable.variables.class?uncap_first} = ${mainTable.variables.class?uncap_first}Service.find${mainTable.variables.class}ById(${mainTable.variables.class?uncap_first}.getId());
		return "edit";
	}
	</#if>
	<#--删除方法-->
	<#if (mainTable.sub&&mainTable.relation=='1:n')||mainTable.sub==false><#--一对多或者是主表-->
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (${mainTable.variables.class?uncap_first}IDs != null) {
				${mainTable.variables.class?uncap_first}Service.doDelete${mainTable.variables.class}s(${mainTable.variables.class?uncap_first}IDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	</#if>
	<#--查看方法-->
	<#if mainTable.sub&&mainTable.relation=="1:1">
	@Action("view")
	public String view() {
		${mainTable.variables.class?uncap_first} = ${mainTable.variables.class?uncap_first}Service.find${mainTable.variables.class}By${mainTable.parentClassName}Id(${mainTable.parentClassName?uncap_first}Id);
		return "view";
	}
	<#elseif mainTable.sub==false>
	@Action("view")
	public String view() {
		this.${mainTable.variables.class?uncap_first} = ${mainTable.variables.class?uncap_first}Service.find${mainTable.variables.class}ById(${mainTable.variables.class?uncap_first}.getId());
		return "view";
	}
	</#if>
	<#-- 列表方法--->
	<#if mainTable.sub&&mainTable.relation="1:n">
	@SuppressWarnings("unchecked")
	@Action("sublist")
	public String sublist() {

		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		this.numPerPage = 10;
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		// 列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		// 将主记录id作为查询条件
		queryCriteria.getQueryCondition().put("${mainTable.parentClassName?uncap_first}Id", ${mainTable.parentClassName?uncap_first}Id);

		pageResult = ${mainTable.variables.class?uncap_first}Service.query${mainTable.variables.class}(queryCriteria);

		return "sublist";
	}
	<#elseif mainTable.sub==false>
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if(${mainTable.variables.class?uncap_first} != null) {
		<#list mainTable.columns as col>
			<#if col.type=="String">
			if(${mainTable.variables.class?uncap_first}.get${col.colName?cap_first}() != null && !"".equals(${mainTable.variables.class?uncap_first}.get${col.colName?cap_first}())) {
				queryCriteria.addQueryCondition("${col.colName}", "%" + ${mainTable.variables.class?uncap_first}.get${col.colName?cap_first}() + "%");
			}
			<#else>
			if(${mainTable.variables.class?uncap_first}.get${col.colName?cap_first}() != null && !"".equals(${mainTable.variables.class?uncap_first}.get${col.colName?cap_first}())) {
				queryCriteria.addQueryCondition("${col.colName}", ${mainTable.variables.class?uncap_first}.get${col.colName?cap_first}());
			}
			</#if>
		</#list>
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		
		//列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		
		this.pageResult = ${mainTable.variables.class?uncap_first}Service.query${mainTable.variables.class}(queryCriteria);
		
		return "list";
	}
	</#if>
	<#--增加方法-->
	<#if mainTable.sub==false>
	@Action("add")
	public String add() {
		${mainTable.variables.class?uncap_first} = new ${mainTable.variables.class}();
		return "add";
	}
	</#if>
	
	public ${mainTable.variables.class} get${mainTable.variables.class}() {
		return ${mainTable.variables.class?uncap_first};
	}

	public void set${mainTable.variables.class}(${mainTable.variables.class} ${mainTable.variables.class?uncap_first}) {
		this.${mainTable.variables.class?uncap_first} = ${mainTable.variables.class?uncap_first};
	}
	
	public Long[] get${mainTable.variables.class}IDs() {
		return ${mainTable.variables.class?uncap_first}IDs;
	}

	public void set${mainTable.variables.class}IDs(Long[] ${mainTable.variables.class?uncap_first}IDs) {
		this.${mainTable.variables.class?uncap_first}IDs = ${mainTable.variables.class?uncap_first}IDs;
	}
	<#if mainTable.sub>
	public Long get${mainTable.parentClassName}Id() {
		return ${mainTable.parentClassName?uncap_first}Id;
	}

	public void set${mainTable.parentClassName}Id(Long ${mainTable.parentClassName?uncap_first}Id) {
		this.${mainTable.parentClassName?uncap_first}Id = ${mainTable.parentClassName?uncap_first}Id;
	}
		<#if mainTable.relation=="1:n">
	public Long get${mainTable.variables.class}Id() {
		return ${mainTable.variables.class?uncap_first}Id;
	}

	public void set${mainTable.variables.class}Id(Long ${mainTable.variables.class?uncap_first}Id) {
		this.${mainTable.variables.class?uncap_first}Id = ${mainTable.variables.class?uncap_first}Id;
	}
		</#if>
	</#if>

}
