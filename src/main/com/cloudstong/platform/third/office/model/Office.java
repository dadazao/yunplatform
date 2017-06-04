package com.cloudstong.platform.third.office.model;

import java.io.Serializable;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:字处理组件表映射类
 */
public class Office implements Serializable {

	private static final long serialVersionUID = 1426185360420744056L;
	
	/**
	 * 字处理组件ID
	 */
	private Long id;
	
	/**
	 * 字处理组件名称
	 */
	private String officeName;
	
	/**
	 * 默认调用方法 
	 */
	private String defaultMethod;
	
	/**
	 * 功能说明 
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String remark;

	public Office() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getDefaultMethod() {
		return defaultMethod;
	}

	public void setDefaultMethod(String defaultMethod) {
		this.defaultMethod = defaultMethod;
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
	
}
