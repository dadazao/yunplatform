package com.cloudstong.platform.resource.metadata.model;

public class DataSources {
	
	/**
	 * 数据源ID
	 */
	private Long id;
	
	/**
	 * 数据库类型
	 */
	private String tblDstype;
	
	/**
	 * 数据库名称
	 */
	private String tblDsname;
	
	/**
	 * 数据库驱动
	 */
	private String tblDsdriver;
	
	/**
	 * 连接字串 
	 */
	private String tblDsurl;
	
	/**
	 * 缺省Schema
	 */
	private String tblDsschema;
	
	/**
	 *  缺省Catalog
	 */
	private String tblDscatalog;
	
	/**
	 * 用户名
	 */
	private String tblDsuser;
	
	/**
	 * 密码 
	 */
	private String tblDspasswd;
	
	/**
	 * 连接编码 
	 */
	private String tblDsencoding;
	
	/**
	 * 是否默认 
	 */
	private String tblDsenable;
	
	/**
	 * 是否默认 
	 */
	private String tblDsstatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTblDstype() {
		return tblDstype;
	}

	public void setTblDstype(String tblDstype) {
		this.tblDstype = tblDstype;
	}

	public String getTblDsname() {
		return tblDsname;
	}

	public void setTblDsname(String tblDsname) {
		this.tblDsname = tblDsname;
	}

	public String getTblDsdriver() {
		return tblDsdriver;
	}

	public void setTblDsdriver(String tblDsdriver) {
		this.tblDsdriver = tblDsdriver;
	}

	public String getTblDsurl() {
		return tblDsurl;
	}

	public void setTblDsurl(String tblDsurl) {
		this.tblDsurl = tblDsurl;
	}

	public String getTblDsschema() {
		return tblDsschema;
	}

	public void setTblDsschema(String tblDsschema) {
		this.tblDsschema = tblDsschema;
	}

	public String getTblDscatalog() {
		return tblDscatalog;
	}

	public void setTblDscatalog(String tblDscatalog) {
		this.tblDscatalog = tblDscatalog;
	}

	public String getTblDsuser() {
		return tblDsuser;
	}

	public void setTblDsuser(String tblDsuser) {
		this.tblDsuser = tblDsuser;
	}

	public String getTblDspasswd() {
		return tblDspasswd;
	}

	public void setTblDspasswd(String tblDspasswd) {
		this.tblDspasswd = tblDspasswd;
	}

	public String getTblDsencoding() {
		return tblDsencoding;
	}

	public void setTblDsencoding(String tblDsencoding) {
		this.tblDsencoding = tblDsencoding;
	}

	public String getTblDsenable() {
		return tblDsenable;
	}

	public void setTblDsenable(String tblDsenable) {
		this.tblDsenable = tblDsenable;
	}

	public String getTblDsstatus() {
		return tblDsstatus;
	}

	public void setTblDsstatus(String tblDsstatus) {
		this.tblDsstatus = tblDsstatus;
	}
}
