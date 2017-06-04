/*******************************************************************************
 * Licensed Materials - Property of ${globalVariables.company?cap_first} 
 * 
 * (C) Copyright ${globalVariables.company?cap_first} Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package ${mainTable.variables.package}.service;

import java.util.List;

import ${mainTable.variables.package}.model.${mainTable.variables.class};
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
public interface ${mainTable.variables.class}Service {
	
	public PageResult query${mainTable.variables.class}(QueryCriteria queryCriteria);

	public List<${mainTable.variables.class}> getAll${mainTable.variables.class}();

	public void doSave${mainTable.variables.class}(${mainTable.variables.class} ${mainTable.variables.class?uncap_first});

	public void doUpdate${mainTable.variables.class}(${mainTable.variables.class} ${mainTable.variables.class?uncap_first});

	public ${mainTable.variables.class} find${mainTable.variables.class}ById(Long ${mainTable.variables.class?uncap_first}Id);

	public void doDelete${mainTable.variables.class}(Long ${mainTable.variables.class?uncap_first}Id);

	public void doDelete${mainTable.variables.class}s(Long[] ${mainTable.variables.class?uncap_first}Ids);
	<#if mainTable.sub>
	public ${mainTable.variables.class} find${mainTable.variables.class}By${mainTable.parentClassName}Id(Long ${mainTable.parentClassName?uncap_first}Id);
	</#if>
}
