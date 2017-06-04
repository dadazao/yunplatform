/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Allan
 * Created on 2014-10-10 11:35:19  
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class Detail extends EntityBase {

	private Long id;

	/**
	 * 地址
	 */
	private String location;
	/**
	 * 工号
	 */
	private String cardnum;
	/**
	 * 父类对象id
	 */
	private Long employeeId;
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setLocation(String location){
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setCardnum(String cardnum){
		this.cardnum = cardnum;
	}
	
	public String getCardnum() {
		return cardnum;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
