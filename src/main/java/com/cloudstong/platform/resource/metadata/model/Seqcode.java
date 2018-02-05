package com.cloudstong.platform.resource.metadata.model;

import com.cloudstong.platform.core.model.EntityBase;

public class Seqcode extends EntityBase{
	private Long id;
	
	/** 
	 * 编码名称
	 */
	private String tblName;
	
	/**
	 * 编码所属系统元素
	 */
	private String tblSystematom;
	
	/**
	 * 基本码
	 */
	private String tblValue;
	
	/**
	 * 扩展码
	 */
	private String tblExpand;
	
	/**
	 * 是否被权限引用
	 */
	private String tblUsestatus;
	
	/**
	 * 上级基本码
	 */
	private String tblParentid;
	
	/**
	 * 编码分类标识
	 */
	private String tblType;
	
	/**
	 * 所属编码引用,如果为字段,那么就为字段的ID
	 */
	private String tblObject;
	
	/**
	 * 所属引用对象ID
	 */
	private String tblTablename;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTblName() {
		return tblName;
	}
	public void setTblName(String tblName) {
		this.tblName = tblName;
	}
	public String getTblSystematom() {
		return tblSystematom;
	}
	public void setTblSystematom(String tblSystematom) {
		this.tblSystematom = tblSystematom;
	}
	public String getTblValue() {
		return tblValue;
	}
	public void setTblValue(String tblValue) {
		this.tblValue = tblValue;
	}
	public String getTblUsestatus() {
		return tblUsestatus;
	}
	public void setTblUsestatus(String tblUsestatus) {
		this.tblUsestatus = tblUsestatus;
	}
	public String getTblExpand() {
		return tblExpand;
	}
	public void setTblExpand(String tblExpand) {
		this.tblExpand = tblExpand;
	}
	public String getTblParentid() {
		return tblParentid;
	}
	public void setTblParentid(String tblParentid) {
		this.tblParentid = tblParentid;
	}
	public String getTblType() {
		return tblType;
	}
	public void setTblType(String tblType) {
		this.tblType = tblType;
	}
	public String getTblObject() {
		return tblObject;
	}
	public void setTblObject(String tblObject) {
		this.tblObject = tblObject;
	}
	public String getTblTablename() {
		return tblTablename;
	}
	public void setTblTablename(String tblTablename) {
		this.tblTablename = tblTablename;
	}
}
