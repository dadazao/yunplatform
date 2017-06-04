/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
public class SysResource extends EntityBase {

	private static final long serialVersionUID = -7178441955425114484L;
	
	/**
	 * 资源ID
	 */
	private Long id;
	
	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 资源URL
	 */
	private String resourceUrl;
	
	/**
	 * 资源描述
	 */
	private String comment;
	
	/**
	 * 资源类型
	 */
	private String type;
	
	/**
	 * 是否启用
	 */
	private Integer enabled = 1;
	
	/**
	 * 所属模块
	 */
	private Long module;
	
	/**
	 * 父模块
	 */
	private Long parentModule;
	
	
	/**
	 * @return Returns the value of id field with the type Long.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id. Set the value of id field with the type Long by the id parameter.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the value of resourceName field with the type String.
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName. Set the value of resourceName field with the type String by the resourceName parameter.
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return Returns the value of resourceUrl field with the type String.
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}

	/**
	 * @param resourceUrl. Set the value of resourceUrl field with the type String by the resourceUrl parameter.
	 */
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	/**
	 * @return Returns the value of comment field with the type String.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment. Set the value of comment field with the type String by the comment parameter.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return Returns the value of type field with the type String.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type. Set the value of type field with the type String by the type parameter.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the value of enabled field with the type Integer.
	 */
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled. Set the value of enabled field with the type Integer by the enabled parameter.
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return Returns the value of module field with the type Long.
	 */
	public Long getModule() {
		return module;
	}

	/**
	 * @param module. Set the value of module field with the type Long by the module parameter.
	 */
	public void setModule(Long module) {
		this.module = module;
	}

	public Long getParentModule() {
		return parentModule;
	}

	public void setParentModule(Long parentModule) {
		this.parentModule = parentModule;
	}
	
}
