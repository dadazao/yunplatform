/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.autocomplete.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
public class AutoComplete extends Component implements Serializable {

	private static final long serialVersionUID = 5315929081208702599L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 自动补齐文本框名称
	 */
	private String autoCompleteName = "";

	/**
	 * 自动补齐文本框宽度
	 */
	private Long autoCompleteWidth = 135L;

	/**
	 * 自动补齐文本框高度
	 */
	private Long autoCompleteHeight = 21L;

	/**
	 * 是否启用
	 */
	private Long autoCompleteEnabled = 1L;

	/**
	 * 是否启用代码的显示名称
	 */
	private String autoCompleteEnabledName = "";

	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String autoCompleteRemarks = "";

	/**
	 * 编码
	 */
	private String autoCompleteCode;
	
	/**
	 * 数据来源
	 */
	private String source;

	public AutoComplete() {
		super();
	}

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
	 * @return Returns the value of autoCompleteName field with the type String.
	 */
	public String getAutoCompleteName() {
		return autoCompleteName;
	}

	/**
	 * @param autoCompleteName. Set the value of autoCompleteName field with the type String by the autoCompleteName parameter.
	 */
	public void setAutoCompleteName(String autoCompleteName) {
		this.autoCompleteName = autoCompleteName;
	}

	/**
	 * @return Returns the value of autoCompleteWidth field with the type Long.
	 */
	public Long getAutoCompleteWidth() {
		return autoCompleteWidth;
	}

	/**
	 * @param autoCompleteWidth. Set the value of autoCompleteWidth field with the type Long by the autoCompleteWidth parameter.
	 */
	public void setAutoCompleteWidth(Long autoCompleteWidth) {
		this.autoCompleteWidth = autoCompleteWidth;
	}

	/**
	 * @return Returns the value of autoCompleteHeight field with the type Long.
	 */
	public Long getAutoCompleteHeight() {
		return autoCompleteHeight;
	}

	/**
	 * @param autoCompleteHeight. Set the value of autoCompleteHeight field with the type Long by the autoCompleteHeight parameter.
	 */
	public void setAutoCompleteHeight(Long autoCompleteHeight) {
		this.autoCompleteHeight = autoCompleteHeight;
	}

	/**
	 * @return Returns the value of autoCompleteEnabled field with the type Long.
	 */
	public Long getAutoCompleteEnabled() {
		return autoCompleteEnabled;
	}

	/**
	 * @param autoCompleteEnabled. Set the value of autoCompleteEnabled field with the type Long by the autoCompleteEnabled parameter.
	 */
	public void setAutoCompleteEnabled(Long autoCompleteEnabled) {
		this.autoCompleteEnabled = autoCompleteEnabled;
	}

	/**
	 * @return Returns the value of autoCompleteEnabledName field with the type String.
	 */
	public String getAutoCompleteEnabledName() {
		return autoCompleteEnabledName;
	}

	/**
	 * @param autoCompleteEnabledName. Set the value of autoCompleteEnabledName field with the type String by the autoCompleteEnabledName parameter.
	 */
	public void setAutoCompleteEnabledName(String autoCompleteEnabledName) {
		this.autoCompleteEnabledName = autoCompleteEnabledName;
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
	 * @return Returns the value of autoCompleteRemarks field with the type String.
	 */
	public String getAutoCompleteRemarks() {
		return autoCompleteRemarks;
	}

	/**
	 * @param autoCompleteRemarks. Set the value of autoCompleteRemarks field with the type String by the autoCompleteRemarks parameter.
	 */
	public void setAutoCompleteRemarks(String autoCompleteRemarks) {
		this.autoCompleteRemarks = autoCompleteRemarks;
	}

	/**
	 * @return Returns the value of autoCompleteCode field with the type String.
	 */
	public String getAutoCompleteCode() {
		return autoCompleteCode;
	}

	/**
	 * @param autoCompleteCode. Set the value of autoCompleteCode field with the type String by the autoCompleteCode parameter.
	 */
	public void setAutoCompleteCode(String autoCompleteCode) {
		this.autoCompleteCode = autoCompleteCode;
	}

	/**
	 * @return Returns the value of source field with the type String.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source. Set the value of source field with the type String by the source parameter.
	 */
	public void setSource(String source) {
		this.source = source;
	}
 
}
