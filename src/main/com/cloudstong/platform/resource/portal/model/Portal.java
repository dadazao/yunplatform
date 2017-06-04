package com.cloudstong.platform.resource.portal.model;

import java.util.List;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * 门户类，每个门户对应一个类
 * 
 * @author Jason 2012-10-29下午06:27:22
 */
public class Portal extends EntityBase {
	private static final long serialVersionUID = 1L;

	// 门户id
	private Long id;

	// 门户名称
	private String name;

	// 备注信息
	private String desc;

	// 样式
	private String style;

	// 门户对应的portlets
	private List<Portlet> portletList;

	// 是否启用
	private Integer isInUse;

	public Integer getIsInUse() {
		return isInUse;
	}

	public void setIsInUse(Integer isInUse) {
		this.isInUse = isInUse;
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<Portlet> getPortletList() {
		return portletList;
	}

	public void setPortletList(List<Portlet> portletList) {
		this.portletList = portletList;
	}

}
