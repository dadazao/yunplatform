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
public class SysUserRole extends EntityBase {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户
	 */
	private SysUser sysUser;	
	/**
	 * 角色
	 */
	private SysRole sysRole;
	/**
	 * Description:Constructor for SysUserRole class.
	 */
	public SysUserRole() {
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
	 * @return Returns the value of sysUser field with the type SysUser.
	 */
	public SysUser getSysUser() {
		return sysUser;
	}
	/**
	 * @param sysUser. Set the value of sysUser field with the type SysUser by the sysUser parameter.
	 */
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	/**
	 * @return Returns the value of sysRole field with the type SysRole.
	 */
	public SysRole getSysRole() {
		return sysRole;
	}
	/**
	 * @param sysRole. Set the value of sysRole field with the type SysRole by the sysRole parameter.
	 */
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	
}
