/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.product.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Allan
 * Created on 2016-4-21 20:36:10  
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class Product extends EntityBase {

	private Long id;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 价格
	 */
	private String price;
	/**
	 * 描述
	 */
	private String description;
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPrice(String price){
		this.price = price;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
