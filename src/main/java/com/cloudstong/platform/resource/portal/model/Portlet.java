package com.cloudstong.platform.resource.portal.model;

import java.util.List;

import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;

/**
 * 门户组件类 一个门户中包含多个门户组件，每个门户组件关联一个列表
 * 
 * @author Jason 2012-10-29下午06:27:56
 */
public class Portlet {
	/**
	 * 组件id
	 */
	private Long id;
	/**
	 * 组件名称
	 */
	private String name;
	/**
	 * 组件关联的列表
	 */
	private Long listId;
	/**
	 * 显示顺序
	 */
	private Integer showOrder;
	/**
	 * 是否滚动
	 */
	private String isScroll;
	/**
	 * 显示记录条数
	 */
	private Integer recordCount;
	/**
	 * 包含的Tabulation
	 */
	private Tabulation tabulation;
	/**
	 * 关联的表单id
	 */
	private Long formId;
	/**
	 * 最大高度
	 */
	private String maxHeight;
	/**
	 * 最小高度
	 */
	private String minHeight;
	/**
	 * 所在区域
	 */
	private String belongArea;

	/**
	 * 组件列表
	 */
	private List<Domain> domainList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public List<Domain> getDomainList() {
		return domainList;
	}

	public void setDomainList(List<Domain> domainList) {
		this.domainList = domainList;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public Tabulation getTabulation() {
		return tabulation;
	}

	public void setTabulation(Tabulation tabulation) {
		this.tabulation = tabulation;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getIsScroll() {
		return isScroll;
	}

	public void setIsScroll(String isScroll) {
		this.isScroll = isScroll;
	}

	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(String minHeight) {
		this.minHeight = minHeight;
	}

	public String getBelongArea() {
		return belongArea;
	}

	public void setBelongArea(String belongArea) {
		this.belongArea = belongArea;
	}
	
}
