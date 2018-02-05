package com.cloudstong.platform.resource.searchcombox.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:搜索下拉框
 */
public class SearchCombox extends Component implements Serializable {

	private static final long serialVersionUID = 6179050965481131141L;
	
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 搜索下拉框名称
	 */
	private String searchComboxName = "";

	/**
	 * 搜索下拉框宽度
	 */
	private Long searchComboxWidth = 135L;

	/**
	 * 搜索下拉框高度
	 */
	private Long searchComboxHeight = 21L;

	/**
	 * 是否启用
	 */
	private Long searchComboxEnabled = 1L;

	/**
	 * 是否启用代码的显示名称
	 */
	private String searchComboxEnabledName = "";

	/**
	 * 搜索下拉框类型
	 */
	private Long searchComboxType = 1L;

	/**
	 * 搜索下拉框类型代码的显示名称
	 */
	private String searchComboxTypeName = "";

	/**
	 * 删除标识 0：未删除 1：已删除
	 */
	private Integer status;

	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String searchComboxRemarks = "";

	/**
	 * 编码
	 */
	private String searchComboxCode;

	public SearchCombox() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearchComboxName() {
		return searchComboxName;
	}

	public void setSearchComboxName(String searchComboxName) {
		this.searchComboxName = searchComboxName;
	}

	public Long getSearchComboxWidth() {
		return searchComboxWidth;
	}

	public void setSearchComboxWidth(Long searchComboxWidth) {
		this.searchComboxWidth = searchComboxWidth;
	}

	public Long getSearchComboxHeight() {
		return searchComboxHeight;
	}

	public void setSearchComboxHeight(Long searchComboxHeight) {
		this.searchComboxHeight = searchComboxHeight;
	}

	public Long getSearchComboxEnabled() {
		return searchComboxEnabled;
	}

	public void setSearchComboxEnabled(Long searchComboxEnabled) {
		this.searchComboxEnabled = searchComboxEnabled;
	}

	public String getSearchComboxEnabledName() {
		return searchComboxEnabledName;
	}

	public void setSearchComboxEnabledName(String searchComboxEnabledName) {
		this.searchComboxEnabledName = searchComboxEnabledName;
	}

	public Long getSearchComboxType() {
		return searchComboxType;
	}

	public void setSearchComboxType(Long searchComboxType) {
		this.searchComboxType = searchComboxType;
	}

	public String getSearchComboxTypeName() {
		return searchComboxTypeName;
	}

	public void setSearchComboxTypeName(String searchComboxTypeName) {
		this.searchComboxTypeName = searchComboxTypeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSearchComboxRemarks() {
		return searchComboxRemarks;
	}

	public void setSearchComboxRemarks(String searchComboxRemarks) {
		this.searchComboxRemarks = searchComboxRemarks;
	}

	public String getSearchComboxCode() {
		return searchComboxCode;
	}

	public void setSearchComboxCode(String searchComboxCode) {
		this.searchComboxCode = searchComboxCode;
	}
	
}
