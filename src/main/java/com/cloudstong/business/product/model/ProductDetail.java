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
public class ProductDetail extends EntityBase {

	private Long id;

	/**
	 * 规格
	 */
	private String guige;
	/**
	 * 说明
	 */
	private String resume;
	/**
	 * 父类对象id
	 */
	private Long productId;
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setGuige(String guige){
		this.guige = guige;
	}
	
	public String getGuige() {
		return guige;
	}
	
	public void setResume(String resume){
		this.resume = resume;
	}
	
	public String getResume() {
		return resume;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
