package com.cloudstong.platform.resource.ordermanage.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:序号列
 */
public class OrderManage implements Serializable {

	private static final long serialVersionUID = -7930738707804357095L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 序号列名称
	 */
	private String orderManageName;

	/**
	 * 宽度
	 */
	private Integer orderManageWidth;

	/**
	 * 单位
	 */
	private String orderManageUnit;

	/**
	 * 标题显示名称
	 */
	private String orderManageShowName;

	/**
	 * 标题位置
	 */
	private String orderManagePosition;

	/**
	 * 功能说明
	 */
	private String comment;

	/**
	 * 备注
	 */
	private String remark;

	public OrderManage() {
		super();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOrderManageName() {
		return orderManageName;
	}

	public void setOrderManageName(String orderManageName) {
		this.orderManageName = orderManageName;
	}

	public Integer getOrderManageWidth() {
		return orderManageWidth;
	}

	public void setOrderManageWidth(Integer orderManageWidth) {
		this.orderManageWidth = orderManageWidth;
	}

	public String getOrderManageUnit() {
		return orderManageUnit;
	}

	public void setOrderManageUnit(String orderManageUnit) {
		this.orderManageUnit = orderManageUnit;
	}

	public String getOrderManageShowName() {
		return orderManageShowName;
	}

	public void setOrderManageShowName(String orderManageShowName) {
		this.orderManageShowName = orderManageShowName;
	}

	public String getOrderManagePosition() {
		return orderManagePosition;
	}

	public void setOrderManagePosition(String orderManagePosition) {
		this.orderManagePosition = orderManagePosition;
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
