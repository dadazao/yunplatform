/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.useinfo.model;

import java.io.Serializable;

/**
 * @author Allan
 * Created on 2014-3-22
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class ChartComp implements Serializable {
	
	/**
	 * 组件名称
	 */
	private String name;
	
	/**
	 * 数量
	 */
	private int value;
	
	/**
	 * 颜色
	 */
	private String color;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
