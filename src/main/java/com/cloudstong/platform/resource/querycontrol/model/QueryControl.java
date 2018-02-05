package com.cloudstong.platform.resource.querycontrol.model;

import java.io.Serializable;

/**
 * @author michael 
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:查询组件
 */
public class QueryControl implements Serializable {

	private static final long serialVersionUID = 1051248405525616573L;

	/**
	 * 主键
	 */
	protected Long id;

	/**
	 * 查询组件名称
	 */
	protected String queryControlName;

	/**
	 * 宽度
	 */
	protected Integer queryControlWidth;

	/**
	 * 高度
	 */
	protected Integer queryControlHeight;

	/**
	 * 是否有查询按钮
	 */
	protected Integer isHasButton;

	/**
	 * 是否有高级查询按钮
	 */
	protected Integer isHasGjbutton;

	/**
	 * 功能说明
	 */
	protected String comment;

	/**
	 * 备注
	 */
	protected String remark;

	public QueryControl() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQueryControlName() {
		return queryControlName;
	}

	public void setQueryControlName(String queryControlName) {
		this.queryControlName = queryControlName;
	}

	public Integer getQueryControlWidth() {
		return queryControlWidth;
	}

	public void setQueryControlWidth(Integer queryControlWidth) {
		this.queryControlWidth = queryControlWidth;
	}

	public Integer getQueryControlHeight() {
		return queryControlHeight;
	}

	public void setQueryControlHeight(Integer queryControlHeight) {
		this.queryControlHeight = queryControlHeight;
	}

	public Integer getIsHasButton() {
		return isHasButton;
	}

	public void setIsHasButton(Integer isHasButton) {
		this.isHasButton = isHasButton;
	}

	public Integer getIsHasGjbutton() {
		return isHasGjbutton;
	}

	public void setIsHasGjbutton(Integer isHasGjbutton) {
		this.isHasGjbutton = isHasGjbutton;
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
