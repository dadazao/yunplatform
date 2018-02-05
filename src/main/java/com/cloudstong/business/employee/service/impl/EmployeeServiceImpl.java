/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.employee.dao.EmployeeDao;
import com.cloudstong.business.employee.model.Employee;
import com.cloudstong.business.employee.service.EmployeeService;
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
@Repository("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeDao employeeDao;

	@Override
	public List<Employee> getAllEmployee() {
		return employeeDao.getAll();
	}

	@Override
	public void doSaveEmployee(Employee employee) {
		employeeDao.saveOrUpdate(employee);
	}

	@Override
	public void doDeleteEmployee(Long employeeId) {
		employeeDao.delById(employeeId);
	}

	@Override
	public Employee findEmployeeById(Long employeeId) {
		return (Employee)employeeDao.getById(employeeId);
	}

	@Override
	public void doUpdateEmployee(Employee employee) {
		employeeDao.update(employee);
	}

	@Override
	public PageResult queryEmployee(QueryCriteria queryCriteria){
		return employeeDao.query(queryCriteria);
	}

	@Override
	public void doDeleteEmployees(Long[] employeeIds) {
		for (Long id : employeeIds) {
			doDeleteEmployee(id);
		}
	}
}
