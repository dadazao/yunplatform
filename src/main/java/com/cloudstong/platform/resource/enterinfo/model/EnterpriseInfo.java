package com.cloudstong.platform.resource.enterinfo.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:企业信息
 */
public class EnterpriseInfo implements Serializable {

	private static final long serialVersionUID = 1180309243973335660L;
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 企业名称
	 */
	private String name;
	/**
	 * 年度
	 */
	private String copyrightYear;
	/**
	 * 系统中文名称
	 */
	private String systemCnName;
	/**
	 * 系统英文名称
	 */
	private String systemEnName;
	/**
	 * 企业简介
	 */
	private String enterAbout;
	
	/**
	 * 系统版本
	 */
	private String version;
	
	public EnterpriseInfo() {
		super();
	}

	
	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCopyrightYear() {
		return copyrightYear;
	}

	public void setCopyrightYear(String copyrightYear) {
		this.copyrightYear = copyrightYear;
	}

	public String getSystemCnName() {
		return systemCnName;
	}

	public void setSystemCnName(String systemCnName) {
		this.systemCnName = systemCnName;
	}

	public String getSystemEnName() {
		return systemEnName;
	}

	public void setSystemEnName(String systemEnName) {
		this.systemEnName = systemEnName;
	}

	public String getEnterAbout() {
		return enterAbout;
	}

	public void setEnterAbout(String enterAbout) {
		this.enterAbout = enterAbout;
	}
	
}
