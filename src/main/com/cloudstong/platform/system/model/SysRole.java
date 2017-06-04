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
 *         Created on 2014-8-27
 * 
 *         Description:
 * 
 */
public class SysRole extends EntityBase {

	private static final long serialVersionUID = -2937966250686807099L;

	/**
	 * 角色ID
	 */
	private Long id;

	/**
	 * 角色名称 
	 */
	private String roleName;
	
	/**
	 * 角色别名
	 */
	private String roleAliss;
	
	/**
	 * 角色说明
	 */
	private String comment;
	
	/**
	 * 允许删除
	 */
	private Integer canDelete = 1;
	
	/**
	 * 允许编辑
	 */
	private Integer canEdit = 1;

	/**
	 * 是否启用
	 */
	private Integer enable = 1;
	
	/**
	 * Description:Constructor for SysRole class.
	 */
	public SysRole() {
		super();
	}

	/**
	 * @return Returns the value of id field with the type String.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id. Set the value of id field with the type String by the id parameter.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the value of roleName field with the type String.
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName. Set the value of roleName field with the type String by the roleName parameter.
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return Returns the value of roleAliss field with the type String.
	 */
	public String getRoleAliss() {
		return roleAliss;
	}

	/**
	 * @param roleAliss. Set the value of roleAliss field with the type String by the roleAliss parameter.
	 */
	public void setRoleAliss(String roleAliss) {
		this.roleAliss = roleAliss;
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
	 * @return Returns the value of canDelete field with the type Integer.
	 */
	public Integer getCanDelete() {
		return canDelete;
	}

	/**
	 * @param canDelete. Set the value of canDelete field with the type Integer by the canDelete parameter.
	 */
	public void setCanDelete(Integer canDelete) {
		this.canDelete = canDelete;
	}

	/**
	 * @return Returns the value of canEdit field with the type Integer.
	 */
	public Integer getCanEdit() {
		return canEdit;
	}

	/**
	 * @param canEdit. Set the value of canEdit field with the type Integer by the canEdit parameter.
	 */
	public void setCanEdit(Integer canEdit) {
		this.canEdit = canEdit;
	}

	/**
	 * @return Returns the value of enable field with the type Integer.
	 */
	public Integer getEnable() {
		return enable;
	}

	/**
	 * @param enable. Set the value of enable field with the type Integer by the enable parameter.
	 */
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getAlias() {
		return null;
	}

}
