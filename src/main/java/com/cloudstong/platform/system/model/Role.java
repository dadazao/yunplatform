package com.cloudstong.platform.system.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author sam
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:角色与权限组表的映射对象
 */
@SuppressWarnings("serial")
public class Role extends EntityBase {

	/**
	 * 角色ID
	 */
	private Long id;

	/**
	 * 角色名称 
	 */
	private String tblRolename;

	/**
	 * 功能说明
	 */
	private String tblRolecomment;

	/**
	 * 备注 
	 */
	private String tblRoleremark;

	/**
	 * 类别
	 */
	private String tblRoletype;

	/**
	 * 顺序 
	 */
	private Long tblOrdernum;
	
	/**
	 * 所属目录
	 */
	private String tblCatalog;
	
	/**
	 * 所属目录名称
	 */
	private String tblCatalogName;
	
	/**
	 * 所属目录缓存 
	 */
	private String tblCatalogs;
	
	/**
	 * 角色所属组
	 */
	private String tblSystemteam;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTblRolename() {
		return tblRolename;
	}

	public void setTblRolename(String tblRolename) {
		this.tblRolename = tblRolename;
	}

	public String getTblRolecomment() {
		return tblRolecomment;
	}

	public void setTblRolecomment(String tblRolecomment) {
		this.tblRolecomment = tblRolecomment;
	}

	public String getTblRoleremark() {
		return tblRoleremark;
	}

	public void setTblRoleremark(String tblRoleremark) {
		this.tblRoleremark = tblRoleremark;
	}

	public String getTblRoletype() {
		return tblRoletype;
	}

	public void setTblRoletype(String tblRoletype) {
		this.tblRoletype = tblRoletype;
	}

	public Long getTblOrdernum() {
		return tblOrdernum;
	}

	public void setTblOrdernum(Long tblOrdernum) {
		this.tblOrdernum = tblOrdernum;
	}
	
	public String getTblCatalog() {
		return tblCatalog;
	}

	public void setTblCatalog(String tblCatalog) {
		this.tblCatalog = tblCatalog;
	}

	public String getTblCatalogName() {
		return tblCatalogName;
	}

	public void setTblCatalogName(String tblCatalogName) {
		this.tblCatalogName = tblCatalogName;
	}

	public String getTblCatalogs() {
		return tblCatalogs;
	}

	public void setTblCatalogs(String tblCatalogs) {
		this.tblCatalogs = tblCatalogs;
	}

	public String getTblSystemteam() {
		return tblSystemteam;
	}

	public void setTblSystemteam(String tblSystemteam) {
		this.tblSystemteam = tblSystemteam;
	}	
}
