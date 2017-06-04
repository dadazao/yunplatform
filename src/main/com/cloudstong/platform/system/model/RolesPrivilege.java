package com.cloudstong.platform.system.model;

/**
 * @author sam
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:角色权限表的映射对象
 */
public class RolesPrivilege{
	
	/**
	 * 角色权限ID
	 */
	private Long id;
	
	/**
	 * 角色ID
	 */
	private String tblMainid;
	
	/**
	 * 权限ID
	 */
	private String tblSubid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTblMainid() {
		return tblMainid;
	}
	public void setTblMainid(String tblMainid) {
		this.tblMainid = tblMainid;
	}
	public String getTblSubid() {
		return tblSubid;
	}
	public void setTblSubid(String tblSubid) {
		this.tblSubid = tblSubid;
	}
	
	
}
