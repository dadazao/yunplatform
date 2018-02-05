package com.cloudstong.platform.system.model;

import java.util.List;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:菜单
 */
public class Menu {
	/**
	 * <code>id</code> 主键.
	 */
	private Long id;
	
	/**
	 * <code>menuName</code> 菜单名称.
	 */
	private String menuName;
	
	/**
	 * <code>comment</code> 功能说明.
	 */
	private String comment;
	
	/**
	 * <code>remark</code> 备注.
	 */
	private String remark;
	
	/**
	 * <code>isDefault</code> 是否默认.
	 */
	private Integer isDefault;
	
	/**
	 * <code>menuItems</code> 菜单项.
	 */
	private List<MenuItem> menuItems;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	
}
