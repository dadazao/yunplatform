/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.service;

import java.util.List;

import com.cloudstong.business.employee.model.Employee;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

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
public interface EmployeeService {
	
	public PageResult queryEmployee(QueryCriteria queryCriteria);

	public List<Employee> getAllEmployee();

	public void doSaveEmployee(Employee employee);

	public void doUpdateEmployee(Employee employee);

	public Employee findEmployeeById(Long employeeId);

	public void doDeleteEmployee(Long employeeId);

	public void doDeleteEmployees(Long[] employeeIds);
}
