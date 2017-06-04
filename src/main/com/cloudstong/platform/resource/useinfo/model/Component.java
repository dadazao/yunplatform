package com.cloudstong.platform.resource.useinfo.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Allan
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:组件基类
 */
public class Component extends EntityBase {
	
	private Long id;
	
	/**
	 * 组件名称
	 */
	private String compName;
	
	/**
	 * 数量
	 */
	private Integer count;
	
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
