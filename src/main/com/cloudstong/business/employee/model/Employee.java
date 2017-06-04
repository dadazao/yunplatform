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
public class Employee extends EntityBase {

	private Long id;

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 手机
	 */
	private String phone;
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
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
}
