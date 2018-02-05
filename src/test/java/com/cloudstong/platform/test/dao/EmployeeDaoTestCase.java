/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.test.dao;

import java.util.Date;

import com.cloudstong.business.employee.dao.EmployeeDao;
import com.cloudstong.business.employee.model.Employee;

/**
 * @author Jason
 * 
 *         Created on 2013-8-27
 * 
 *         Description:
 * 
 */
public class EmployeeDaoTestCase extends BaseDaoTestCase {

	private EmployeeDao employeeDao;

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	
	
	public void testInsert(){
		Employee e = new Employee();
		e.setBirthday(new Date());
		e.setName("myname");
		employeeDao.save(e);
	}
}
