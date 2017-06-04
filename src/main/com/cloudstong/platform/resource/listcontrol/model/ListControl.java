package com.cloudstong.platform.resource.listcontrol.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.operationmanage.model.OperationManage;
import com.cloudstong.platform.resource.ordermanage.model.OrderManage;
import com.cloudstong.platform.resource.pagemanage.model.PageManage;
import com.cloudstong.platform.resource.selectmanage.model.SelectManage;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表组件
 */
public class ListControl implements Serializable {

	private static final long serialVersionUID = 5851943456669211611L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 列表组件名称
	 */
	private String listControlName;
	/**
	 * 选择列ID
	 */
	private String selectId;
	/**
	 * 序号列ID
	 */
	private String orderId;
	/**
	 * 操作列ID
	 */
	private String operationId;
	/**
	 * 分页构件ID
	 */
	private String pageId;
	/**
	 * 功能说明
	 */
	private String comment;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 选择列
	 */
	private SelectManage selectManage;

	/**
	 * 序号列
	 */
	private OrderManage orderManage;

	/**
	 * 操作列
	 */
	private OperationManage operationManage;

	/**
	 * 分页构件
	 */
	private PageManage pageManage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getListControlName() {
		return listControlName;
	}

	public void setListControlName(String listControlName) {
		this.listControlName = listControlName;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
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

	public SelectManage getSelectManage() {
		return selectManage;
	}

	public void setSelectManage(SelectManage selectManage) {
		this.selectManage = selectManage;
	}

	public OrderManage getOrderManage() {
		return orderManage;
	}

	public void setOrderManage(OrderManage orderManage) {
		this.orderManage = orderManage;
	}

	public OperationManage getOperationManage() {
		return operationManage;
	}

	public void setOperationManage(OperationManage operationManage) {
		this.operationManage = operationManage;
	}

	public PageManage getPageManage() {
		return pageManage;
	}

	public void setPageManage(PageManage pageManage) {
		this.pageManage = pageManage;
	}

	public ListControl() {
		super();
	}

}
