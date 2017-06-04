package com.cloudstong.platform.system.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:LOGO
 */
public class Logo implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * logo名称
	 */
	private String logoName;

	/**
	 * logo所在路径
	 */
	private String logoPath;

	/**
	 * 背景颜色
	 */
	private String backgroundColor;

	/**
	 * 是否默认  0：否 1：是
	 */
	private Integer isDefault;

	/**
	 * 主题
	 */
	private String theme;

	public Logo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
