/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:桌面栏目
 * 
 */
public class DesktopItem extends EntityBase {

	private static final long serialVersionUID = 7529014455842927150L;

	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 别名
	 */
	private String alias;
	/**
	 * 方法路径
	 */
	private String methodUrl;
	/**
	 * 模块路径
	 */
	private String moduleUrl;
	/**
	 * 模板Html
	 */
	private String templateHtml;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMethodUrl() {
		return methodUrl;
	}

	public void setMethodUrl(String methodUrl) {
		this.methodUrl = methodUrl;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getTemplateHtml() {
		return templateHtml;
	}

	public void setTemplateHtml(String templateHtml) {
		this.templateHtml = templateHtml;
	}

}
