/**
 * 
 */
package com.cloudstong.platform.system.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author liuqi
 * Created on 2014-8-23
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
public class SysUserOrg extends EntityBase {
	private static final long serialVersionUID = 8976803169763386803L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 用户
	 */
	private SysUser sysUser;
	
	/**
	 * 机构
	 */
	private SysOrg sysOrg;
	
	/**
	 * 是否为主机构
	 */
	private boolean zhujigou=false;
	
	/**
	 * 是否为主要负责人
	 */
	private boolean fuzeren=false;

	public SysUserOrg() {
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
	 * @return Returns the value of sysOrg field with the type SysOrg.
	 */
	public SysOrg getSysOrg() {
		return sysOrg;
	}

	/**
	 * @param sysOrg. Set the value of sysOrg field with the type SysOrg by the sysOrg parameter.
	 */
	public void setSysOrg(SysOrg sysOrg) {
		this.sysOrg = sysOrg;
	}

	/**
	 * @return the zhujigou
	 */
	public boolean isZhujigou() {
		return zhujigou;
	}

	/**
	 * @param zhujigou the zhujigou to set
	 */
	public void setZhujigou(boolean zhujigou) {
		this.zhujigou = zhujigou;
	}

	/**
	 * @return the fuzeren
	 */
	public boolean isFuzeren() {
		return fuzeren;
	}

	/**
	 * @param fuzeren the fuzeren to set
	 */
	public void setFuzeren(boolean fuzeren) {
		this.fuzeren = fuzeren;
	}

	public Long getOrgId() {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}
	
}
