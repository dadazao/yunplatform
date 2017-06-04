package com.cloudstong.platform.resource.radio.vo;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:单选框
 */
public class RadioMgtVO implements java.io.Serializable {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 单选框名称
	 */
	private String tbl_compname;

	/**
	 * 背景颜色
	 */
	private String tbl_bgColor;

	/**
	 * 边框样式
	 */
	private String tbl_borderStyle;

	/**
	 * 比例
	 */
	private String tbl_zoom;

	/**
	 * 边框颜色
	 */
	private String tbl_borderColor;

	/**
	 * 边框宽度
	 */
	private String tbl_borderWidth;

	public RadioMgtVO() {
	}

	public RadioMgtVO(String compname, String bgColor, String borderStyle,
			String zoom, String borderColor, String borderWidth) {
		super();
		this.tbl_compname = compname;
		this.tbl_bgColor = bgColor;
		this.tbl_borderStyle = borderStyle;
		this.tbl_zoom = zoom;
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

	public String getTbl_bgColor() {
		return tbl_bgColor;
	}

	public void setTbl_bgColor(String tbl_bgColor) {
		this.tbl_bgColor = tbl_bgColor;
	}

	public String getTbl_borderStyle() {
		return tbl_borderStyle;
	}

	public void setTbl_borderStyle(String tbl_borderStyle) {
		this.tbl_borderStyle = tbl_borderStyle;
	}

	public String getTbl_zoom() {
		return tbl_zoom;
	}

	public void setTbl_zoom(String tbl_zoom) {
		this.tbl_zoom = tbl_zoom;
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
