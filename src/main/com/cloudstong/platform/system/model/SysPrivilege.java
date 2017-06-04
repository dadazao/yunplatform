/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
public class SysPrivilege extends EntityBase {
	
	private static final long serialVersionUID = 2438725426315551094L;
	
	/**
	 * 权限ID
	 */
	private Long id;
	
	/**
	 * 权限名称
	 */
	private String privilegeName;
	
	/**
	 * 权限代码
	 */
	private String code;
	
	/**
	 * 权限描述
	 */
	private String comment;
	
	/**
	 * 是否启用
	 */
	private Integer enabled = 1;
	
	/**
	 * 所属模块
	 */
	private Long module;
	
	/**
	 * 配置按钮ID
	 */
	private Long buttonId;
	
	/**
	 * 是否被选择
	 */
	private boolean isSelected;
	
	/**
	 * Description:Constructor for SysPrivilege class.
	 */
	public SysPrivilege() {
		super();
	}
	/**
	 * @return Returns the value of id field with the type Long.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id. Set the value of id field with the type Long by the id parameter.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return Returns the value of privilegeName field with the type String.
	 */
	public String getPrivilegeName() {
		return privilegeName;
	}
	/**
	 * @param privilegeName. Set the value of privilegeName field with the type String by the privilegeName parameter.
	 */
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	/**
	 * @return Returns the value of code field with the type String.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code. Set the value of code field with the type String by the code parameter.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the value of comment field with the type String.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment. Set the value of comment field with the type String by the comment parameter.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the value of enabled field with the type String.
	 */
	public Integer getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled. Set the value of enabled field with the type String by the enabled parameter.
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return Returns the value of module field with the type String.
	 */
	public Long getModule() {
		return module;
	}
	/**
	 * @param module. Set the value of module field with the type String by the module parameter.
	 */
	public void setModule(Long module) {
		this.module = module;
	}
	/**
	 * @return Returns the value of buttonId field with the type Long.
	 */
	public Long getButtonId() {
		return buttonId;
	}
	/**
	 * @param buttonId. Set the value of buttonId field with the type Long by the buttonId parameter.
	 */
	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	@Override
	public boolean equals(Object obj) {
		if(this.id.equals(((SysPrivilege)obj).getId())) {
			return true;
		}else{
			return false;
		}
	}
	
}
