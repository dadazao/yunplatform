package com.cloudstong.platform.system.model;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:菜单项
 */
public class MenuItem {
	
	/**
	 * <code>id</code> 主键.
	 */
	private Long id;
	
	/**
	 * <code>menuItemName</code> 菜单项名称.
	 */
	private String menuItemName;
	
	/**
	 * <code>href</code> 菜单项链接.
	 */
	private String href;
	
	/**
	 * <code>catalogId</code> 目录ID.
	 */
	private String catalogId;
	
	/**
	 * <code>catalogCode</code> 目录编码.
	 */
	private String catalogCode;
	
	/**
	 * <code>order</code> 顺序.
	 */
	private Integer order;
	
	/**
	 * <code>fontFamily</code> 字体.
	 */
	private String fontFamily;
	
	/**
	 * <code>fontSize</code> 字号.
	 */
	private Integer fontSize;
	
	/**
	 * <code>color</code> 颜色.
	 */
	private String color;
	
	/**
	 * <code>comment</code> 功能说明.
	 */
	private String comment;
	
	/**
	 * <code>remark</code> 备注.
	 */
	private String remark;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

}
