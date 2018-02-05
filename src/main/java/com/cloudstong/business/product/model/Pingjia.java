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
public class Pingjia extends EntityBase {

	private Long id;

	/**
	 * 分数
	 */
	private String scorce;
	/**
	 * 详细描述
	 */
	private String xxms;
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

	public void setScorce(String scorce){
		this.scorce = scorce;
	}
	
	public String getScorce() {
		return scorce;
	}
	
	public void setXxms(String xxms){
		this.xxms = xxms;
	}
	
	public String getXxms() {
		return xxms;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
