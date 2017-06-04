/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.metadata.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Jason
 * 
 *         Created on 2014-9-10
 * 
 *         Description:
 * 
 */
public class ConfigColumn extends EntityBase {

	private static final long serialVersionUID = -413170366159814489L;

	private Long id;

	private String zhName;

	private String enName;

	private Integer formOrder = 0;

	private Integer listOrder = 0;

	private Integer isEdit;

	private Integer isView;

	private Integer notNull;

	private Integer isQuery;

	private Integer isInList;

	private Object defaultValue;

	private Integer inputType;

	private String belongTable;

	/*
	 * @see com.cloudstongplatform.core.model.EntityBase#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * @see com.cloudstongplatform.core.model.EntityBase#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public Integer getFormOrder() {
		return formOrder;
	}

	public void setFormOrder(Integer formOrder) {
		this.formOrder = formOrder;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public Integer getNotNull() {
		return notNull;
	}

	public void setNotNull(Integer notNull) {
		this.notNull = notNull;
	}

	public Integer getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(Integer isQuery) {
		this.isQuery = isQuery;
	}

	public Integer getIsInList() {
		return isInList;
	}

	public void setIsInList(Integer isInList) {
		this.isInList = isInList;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getInputType() {
		return inputType;
	}

	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}

	public String getBelongTable() {
		return belongTable;
	}

	public void setBelongTable(String belongTable) {
		this.belongTable = belongTable;
	}

}
