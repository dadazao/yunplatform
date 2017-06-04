package com.cloudstong.platform.system.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.enterinfo.model.EnterpriseInfo;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主题
 */
public class Theme implements Serializable{

	/**
	 * <code>id</code> 主键.
	 */
	public Long id;

	/**
	 * <code>themeName</code> 主题名称.
	 */
	public String themeName;

	/**
	 * <code>themeCode</code> 主题编码.
	 */
	public String themeCode;

	/**
	 * <code>defaultLogo</code> 缺省logo.
	 */
	public Logo defaultLogo;

	/**
	 * <code>function</code> 功能说明.
	 */
	public String function;

	/**
	 * <code>comment</code> 备注.
	 */
	public String comment;

	/**
	 * <code>isDefault</code> 是否默认.
	 */
	public Integer isDefault;
	
	public EnterpriseInfo enterpriseInfo;
	
	public EnterpriseInfo getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getThemeCode() {
		return themeCode;
	}

	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}

	public Logo getDefaultLogo() {
		return defaultLogo;
	}

	public void setDefaultLogo(Logo defaultLogo) {
		this.defaultLogo = defaultLogo;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}
