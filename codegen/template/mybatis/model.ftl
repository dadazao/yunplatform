/*******************************************************************************
 * Licensed Materials - Property of ${globalVariables.company?cap_first} 
 * 
 * (C) Copyright ${globalVariables.company?cap_first} Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package ${mainTable.variables.package}.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

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
public class ${mainTable.variables.class} extends EntityBase {

	private Long id;

	<#list mainTable.columns as col>
	/**
	 * ${col.chName}
	 */
	private ${col.type} ${col.colName};
	</#list>
	<#if mainTable.sub>
	/**
	 * 父类对象id
	 */
	private Long ${mainTable.parentClassName?uncap_first}Id;
	</#if>
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	<#list mainTable.columns as col>
	public void set${col.colName?cap_first}(${col.type} ${col.colName}){
		this.${col.colName} = ${col.colName};
	}
	
	public ${col.type} get${col.colName?cap_first}() {
		return ${col.colName};
	}
	
	</#list>
	<#if mainTable.sub>
	public Long get${mainTable.parentClassName}Id() {
		return ${mainTable.parentClassName?uncap_first}Id;
	}

	public void set${mainTable.parentClassName}Id(Long ${mainTable.parentClassName?uncap_first}Id) {
		this.${mainTable.parentClassName?uncap_first}Id = ${mainTable.parentClassName?uncap_first}Id;
	}
	</#if>
}
