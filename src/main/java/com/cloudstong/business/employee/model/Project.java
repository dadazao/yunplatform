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
public class Project extends EntityBase {

	private Long id;

	/**
	 * 项目名称
	 */
	private String name;
	/**
	 * 项目金额
	 */
	private String money;
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

	public void setName(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMoney(String money){
		this.money = money;
	}
	
	public String getMoney() {
		return money;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
