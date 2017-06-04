/**
 * 
 */
package com.cloudstong.platform.system.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author liuqi
 * Created on 2014-8-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
public class SysUserPosition extends EntityBase {
	private static final long serialVersionUID = 3891299982293193972L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户
	 */
	private SysUser sysUser;	
	/**
	 * 岗位
	 */
	private SysPosition sysPosition;
	
	/**
	 * 是否是主岗位
	 */
	private boolean zhugangwei=false;
	/**
	 * 
	 */
	public SysUserPosition() {
		super();
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the sysUser
	 */
	public SysUser getSysUser() {
		return sysUser;
	}
	/**
	 * @param sysUser the sysUser to set
	 */
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	/**
	 * @return the sysPosition
	 */
	public SysPosition getSysPosition() {
		return sysPosition;
	}
	/**
	 * @param sysPosition the sysPosition to set
	 */
	public void setSysPosition(SysPosition sysPosition) {
		this.sysPosition = sysPosition;
	}
	/**
	 * @return the zhugangwei
	 */
	public boolean isZhugangwei() {
		return zhugangwei;
	}
	/**
	 * @param zhugangwei the zhugangwei to set
	 */
	public void setZhugangwei(boolean zhugangwei) {
		this.zhugangwei = zhugangwei;
	}
	
}
