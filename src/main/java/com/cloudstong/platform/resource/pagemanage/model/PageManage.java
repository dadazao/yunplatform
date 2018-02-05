package com.cloudstong.platform.resource.pagemanage.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分页构件
 */
public class PageManage implements Serializable {

	private static final long serialVersionUID = -2154948262596523109L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 分页构件名称
	 */
	private String pageManageName;

	/**
	 * 每页记录数
	 */
	private Integer pagesize;

	/**
	 * 分页组件显示的页码数
	 */
	private Integer showPageNumberCount;

	/**
	 * 功能说明
	 */
	private String comment;

	/**
	 * 备注
	 */
	private String remark;

	public PageManage() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPageManageName() {
		return pageManageName;
	}

	public void setPageManageName(String pageManageName) {
		this.pageManageName = pageManageName;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getShowPageNumberCount() {
		return showPageNumberCount;
	}

	public void setShowPageNumberCount(Integer showPageNumberCount) {
		this.showPageNumberCount = showPageNumberCount;
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
