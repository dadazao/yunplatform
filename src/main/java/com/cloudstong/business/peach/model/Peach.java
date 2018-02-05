/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.peach.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Allan
 * Created on 2015-6-11 12:28:44  
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class Peach extends EntityBase {

	private Long id;

	/**
	 * 价格
	 */
	private Integer price;
	/**
	 * 功效
	 */
	private String gongxiao;
	/**
	 * 品种
	 */
	private String pinzhong;
	/**
	 * 成熟时间
	 */
	private Date maturetime;
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setPrice(Integer price){
		this.price = price;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setGongxiao(String gongxiao){
		this.gongxiao = gongxiao;
	}
	
	public String getGongxiao() {
		return gongxiao;
	}
	
	public void setPinzhong(String pinzhong){
		this.pinzhong = pinzhong;
	}
	
	public String getPinzhong() {
		return pinzhong;
	}
	
	public void setMaturetime(Date maturetime){
		this.maturetime = maturetime;
	}
	
	public Date getMaturetime() {
		return maturetime;
	}
	
}
