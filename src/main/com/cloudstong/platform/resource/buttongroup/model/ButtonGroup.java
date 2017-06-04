package com.cloudstong.platform.resource.buttongroup.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:按钮组
 */
public class ButtonGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 按钮组名称
	 */
	private String buttonGroupName = ""; 

	/**
	 * 启用状态
	 */
	private long buttonGroupEnabled = 1;

	/**
	 * 启用状态名称
	 */
	private String buttonGroupEnabledName = "";

	/**
	 * 按钮最大换行个数
	 */
	private long buttonGroupMaxCount = 10;

	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String buttonGroupRemarks = "";

	/**
	 * 删除标志 0：未删除   1：已删除
	 */
	private long status = -1;

	/**
	 *  创建人
	 */
	private String createBy = "-1";

	/**
	 * 创建时间
	 */
	private java.sql.Timestamp createDate = null;

	/**
	 * 修改人
	 */
	private String updateBy = "-1";

	/**
	 * 修改时间
	 */
	private java.sql.Timestamp updateDate = null;

	/**
	 * 按钮组中的按钮的集合
	 */
	private List<ButtonAndGroup> buttonAndGroups;

	/**
	 * 按钮组类型
	 */
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getButtonGroupName() {
		return buttonGroupName;
	}

	public void setButtonGroupName(String buttonGroupName) {
		this.buttonGroupName = buttonGroupName;
	}

	public long getButtonGroupEnabled() {
		return buttonGroupEnabled;
	}

	public void setButtonGroupEnabled(long buttonGroupEnabled) {
		this.buttonGroupEnabled = buttonGroupEnabled;
	}

	public long getButtonGroupMaxCount() {
		return buttonGroupMaxCount;
	}

	public void setButtonGroupMaxCount(long buttonGroupMaxCount) {
		this.buttonGroupMaxCount = buttonGroupMaxCount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getButtonGroupRemarks() {
		return buttonGroupRemarks;
	}

	public void setButtonGroupRemarks(String buttonGroupRemarks) {
		this.buttonGroupRemarks = buttonGroupRemarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getButtonGroupEnabledName() {
		return buttonGroupEnabledName;
	}

	public void setButtonGroupEnabledName(String buttonGroupEnabledName) {
		this.buttonGroupEnabledName = buttonGroupEnabledName;
	}

	public List<ButtonAndGroup> getButtonAndGroups() {
		return buttonAndGroups;
	}

	public void setButtonAndGroups(List<ButtonAndGroup> buttonAndGroups) {
		this.buttonAndGroups = buttonAndGroups;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
