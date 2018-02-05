package com.cloudstong.platform.resource.date.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:日期组件
 */
public class DateControl implements Serializable {

	private static final long serialVersionUID = -1584849951399968657L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 日期组件名称
	 */
	private String dateName = "";

	/**
	 * 日期组件宽度
	 */
	private Integer dateWidth = 135;

	/**
	 * 是否启用 1:启用 0:不启用
	 */
	private Integer dateEnabled = 1;

	/**
	 * 日期组件样式
	 */
	private String dateStyle;

	/**
	 * 编码
	 */
	private String dateCode = "";

	/**
	 * 备注
	 */
	private String dateDesc;

	/**
	 * 状态-(删除标记)
	 */
	private Integer status = -1;

	/**
	 * 创建人
	 */
	private String createBy = "-1";

	/**
	 * 创建时间
	 */
	private java.util.Date createDate;

	/**
	 * 修改人
	 */
	private String updateBy = "-1";

	/**
	 * 修改日期
	 */
	private java.util.Date updateDate;

	/**
	 * 功能说明
	 */
	private String dateRemarks = "";

	public DateControl() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateName() {
		return dateName;
	}

	public void setDateName(String dateName) {
		this.dateName = dateName;
	}

	public Integer getDateWidth() {
		return dateWidth;
	}

	public void setDateWidth(Integer dateWidth) {
		this.dateWidth = dateWidth;
	}

	public Integer getDateEnabled() {
		return dateEnabled;
	}

	public void setDateEnabled(Integer dateEnabled) {
		this.dateEnabled = dateEnabled;
	}

	public String getDateStyle() {
		return dateStyle;
	}

	public void setDateStyle(String dateStyle) {
		this.dateStyle = dateStyle;
	}

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDateRemarks() {
		return dateRemarks;
	}

	public void setDateRemarks(String dateRemarks) {
		this.dateRemarks = dateRemarks;
	}

}
