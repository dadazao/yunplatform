package com.cloudstong.platform.system.model;


/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:用户角色表的映射对象
 */
public class UserRole{
	/**
	 * 用户角色ID
	 */
	private Long id;
	
	/**
	 * 用户ID
	 */
	private String tblMainid;
	
	/**
	 * 角色ID
	 */
	private String tblSubid;
	
	private String fullname;
	
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
}
