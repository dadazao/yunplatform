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
 * Description:权限临时缓存表的映射对象
 */
@SuppressWarnings("serial")
public class FunctionTmp extends EntityBase{
	
	/**
	 * 缓存表ID
	 */
	private Long id;
	
	/**
	 * 目录ID
	 */
	private String tblCatalogs;
	
	/**
	 * 编码ID
	 */
	private String tblResources;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTblCatalogs() {
		return tblCatalogs;
	}
	public void setTblCatalogs(String tblCatalogs) {
		this.tblCatalogs = tblCatalogs;
	}
	public String getTblResources() {
		return tblResources;
	}
	public void setTblResources(String tblResources) {
		this.tblResources = tblResources;
	}

	
}
