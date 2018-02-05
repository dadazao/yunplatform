/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.model;

/**
 * @author Allan
 * 
 *         Created on 2014-10-16
 * 
 *         Description:
 * 
 */
public class SysResourceExtend extends SysResource{
	
	private String func = "";
	private String role = "";

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

}
