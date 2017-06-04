/*******************************************************************************
 * Licensed Materials - Property of ${globalVariables.company?cap_first} 
 * 
 * (C) Copyright ${globalVariables.company?cap_first} Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package ${mainTable.variables.package}.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import ${mainTable.variables.package}.dao.${mainTable.variables.class}Dao;
import ${mainTable.variables.package}.model.${mainTable.variables.class};
import ${mainTable.variables.package}.service.${mainTable.variables.class}Service;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author ${globalVariables.developer}
 * Created on ${.now}
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("${mainTable.variables.class?uncap_first}Service")
public class ${mainTable.variables.class}ServiceImpl implements ${mainTable.variables.class}Service {

	@Resource
	private ${mainTable.variables.class}Dao ${mainTable.variables.class?uncap_first}Dao;

	@Override
	public List<${mainTable.variables.class}> getAll${mainTable.variables.class}() {
		return ${mainTable.variables.class?uncap_first}Dao.getAll();
	}

	@Override
	public void doSave${mainTable.variables.class}(${mainTable.variables.class} ${mainTable.variables.class?uncap_first}) {
		${mainTable.variables.class?uncap_first}Dao.saveOrUpdate(${mainTable.variables.class?uncap_first});
	}

	@Override
	public void doDelete${mainTable.variables.class}(Long ${mainTable.variables.class?uncap_first}Id) {
		${mainTable.variables.class?uncap_first}Dao.remove(${mainTable.variables.class?uncap_first}Id);
	}

	@Override
	public ${mainTable.variables.class} find${mainTable.variables.class}ById(Long ${mainTable.variables.class?uncap_first}Id) {
		return (${mainTable.variables.class})${mainTable.variables.class?uncap_first}Dao.findById(${mainTable.variables.class}.class, ${mainTable.variables.class?uncap_first}Id);
	}

	@Override
	public void doUpdate${mainTable.variables.class}(${mainTable.variables.class} ${mainTable.variables.class?uncap_first}) {
		${mainTable.variables.class?uncap_first}Dao.update(${mainTable.variables.class?uncap_first});
	}

	@Override
	public PageResult query${mainTable.variables.class}(QueryCriteria queryCriteria){
		return ${mainTable.variables.class?uncap_first}Dao.query(queryCriteria);
	}

	@Override
	public void doDelete${mainTable.variables.class}s(Long[] ${mainTable.variables.class?uncap_first}Ids) {
		for (Long id : ${mainTable.variables.class?uncap_first}Ids) {
			doDelete${mainTable.variables.class}(id);
		}
	}
	
	<#if mainTable.sub>
	@Override
	public ${mainTable.variables.class} find${mainTable.variables.class}By${mainTable.parentClassName}Id(Long ${mainTable.parentClassName?uncap_first}Id) {
		List<${mainTable.variables.class}> list = ${mainTable.variables.class?uncap_first}Dao.find("from ${mainTable.variables.class} o where o.${mainTable.parentClassName?uncap_first}.id = ?", ${mainTable.parentClassName?uncap_first}Id);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	</#if>
}
