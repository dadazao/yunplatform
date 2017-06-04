/*******************************************************************************
 * Licensed Materials - Property of ${globalVariables.company?cap_first} 
 * 
 * (C) Copyright ${globalVariables.company?cap_first} Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package ${mainTable.variables.package}.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
<#if mainTable.sub>
import javax.persistence.JoinColumn;
	<#if mainTable.relation=="1:n">
import javax.persistence.ManyToOne;
	</#if>
</#if>
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Entity
@Table(name="${mainTable.tableName}")
public class ${mainTable.variables.class} extends EntityBase{

	@Id
	private Long id;
	
	<#list mainTable.columns as col>
	<#if col.type=="Date">
	/**
	 * ${col.chName}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="${col.name}")
	private ${col.type} ${col.colName};
	<#else>
	/**
	 * ${col.chName}
	 */
	@Column(name="${col.name}")
	private ${col.type} ${col.colName};
	</#if>
	
	</#list>
	<#if mainTable.sub==false>
	<#list mainTable.subTables as subTable>
		<#switch subTable.relation>
			<#case "1:1">
	@OneToOne(cascade = {CascadeType.ALL},optional = true, mappedBy = "${mainTable.variables.class?uncap_first}")
	private ${subTable.variables.class} ${subTable.variables.class?uncap_first};
				<#break>
			<#case "1:n">
	@OneToMany(mappedBy = "${mainTable.variables.class?uncap_first}", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<${subTable.variables.class}> ${subTable.variables.class?uncap_first}Set;
				<#break>
		</#switch>
		
	</#list>
	<#else>
		<#if mainTable.relation=="1:1">
		
	@OneToOne(optional = false)
	@JoinColumn(name = "${mainTable.foreignKey}", referencedColumnName = "id", unique = true)
	private ${mainTable.parentClassName} ${mainTable.parentClassName?uncap_first};
		<#elseif mainTable.relation=="1:n">
		
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "${mainTable.foreignKey}")
	private ${mainTable.parentClassName} ${mainTable.parentClassName?uncap_first};
		</#if>
	</#if>
	
	public Long getId() {
		return id;
	}
	
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
	<#if mainTable.sub==false>
	<#list mainTable.subTables as subTable>
		<#switch subTable.relation>
			<#case "1:1">
	public void set${subTable.variables.class}(${subTable.variables.class} ${subTable.variables.class?uncap_first}){
		this.${subTable.variables.class?uncap_first} = ${subTable.variables.class?uncap_first};
	}
	
	public ${subTable.variables.class} get${subTable.variables.class}(){
		return ${subTable.variables.class?uncap_first};
	}
				<#break>
			<#case "1:n">
	public void set${subTable.variables.class}Set(Set<${subTable.variables.class}> ${subTable.variables.class?uncap_first}Set){
		this.${subTable.variables.class?uncap_first}Set = ${subTable.variables.class?uncap_first}Set;
	}
	
	public Set<${subTable.variables.class}> get${subTable.variables.class}Set(){
		return ${subTable.variables.class?uncap_first}Set;
	}
				<#break>
		</#switch>
		
	</#list>
	<#else>
	
	public ${mainTable.parentClassName} get${mainTable.parentClassName}() {
		return ${mainTable.parentClassName?uncap_first};
	}

	public void set${mainTable.parentClassName}(${mainTable.parentClassName} ${mainTable.parentClassName?uncap_first}) {
		this.${mainTable.parentClassName?uncap_first} = ${mainTable.parentClassName?uncap_first};
	}
	</#if>
}