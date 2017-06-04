/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.test.service;

import com.cloudstong.business.employee.model.Detail;
import com.cloudstong.business.employee.model.Employee;
import com.cloudstong.business.employee.service.DetailService;
import com.cloudstong.business.employee.service.EmployeeService;

/**
 * @author Jason
 * 
 *         Created on 2013-8-27
 * 
 *         Description:
 * 
 */
public class DetailServiceTestCase extends BaseServiceTestCase {

	private DetailService detailService;
	private EmployeeService employeeService;

	public DetailService getDetailService() {
		return detailService;
	}

	public void setDetailService(DetailService detailService) {
		this.detailService = detailService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void testInsert() {

		Detail d = new Detail();
		d.setLocation("asdfasdf");
		d.setCardnum("223233");

		//Employee e = employeeService.findEmployeeById("402881f640bf8b140140bf8b17fc0000");
		
		//d.setEmployee(e);
		//detailService.doSaveDetail(d);
		//System.out.println(d.getId());
	}

	public void testDelete(){
		//detailService.doDeleteDetail("402881f640bf545e0140bf5461be0000");
	}

}
