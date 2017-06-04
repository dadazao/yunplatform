package com.cloudstong.platform.resource.checkbox.vo;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:复选框
 */
public class CheckBoxMgtVO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 复选框名称
	 */
	private String tbl_compname;

	/**
	 * 背景颜色
	 */
	private String tbl_bgcolor;

	/**
	 * 边框样式
	 */
	private String tbl_borderStyle;

	/**
	 * 比例
	 */
	private String tbl_bili;

	/**
	 * 边框颜色
	 */
	private String tbl_borderColor;

	/**
	 * 边框宽度
	 */
	private String tbl_borderWidth;

	public CheckBoxMgtVO() {
	}

	public CheckBoxMgtVO(String compname, String bgcolor, String borderStyle,
			String bili, String borderColor, String borderWidth) {
		super();
		this.tbl_compname = compname;
		this.tbl_bgcolor = bgcolor;
		this.tbl_borderStyle = borderStyle;
		this.tbl_bili = bili;
		this.tbl_borderColor = borderColor;
		this.tbl_borderWidth = borderWidth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTbl_compname() {
		return tbl_compname;
	}

	public void setTbl_compname(String tbl_compname) {
		this.tbl_compname = tbl_compname;
	}

	public String getTbl_bgcolor() {
		return tbl_bgcolor;
	}

	public void setTbl_bgcolor(String tbl_bgcolor) {
		this.tbl_bgcolor = tbl_bgcolor;
	}

	public String getTbl_borderStyle() {
		return tbl_borderStyle;
	}

	public void setTbl_borderStyle(String tbl_borderStyle) {
		this.tbl_borderStyle = tbl_borderStyle;
	}

	public String getTbl_bili() {
		return tbl_bili;
	}

	public void setTbl_bili(String tbl_bili) {
		this.tbl_bili = tbl_bili;
	}

	public String getTbl_borderColor() {
		return tbl_borderColor;
	}

	public void setTbl_borderColor(String tbl_borderColor) {
		this.tbl_borderColor = tbl_borderColor;
	}

	public String getTbl_borderWidth() {
		return tbl_borderWidth;
	}

	public void setTbl_borderWidth(String tbl_borderWidth) {
		this.tbl_borderWidth = tbl_borderWidth;
	}

}
