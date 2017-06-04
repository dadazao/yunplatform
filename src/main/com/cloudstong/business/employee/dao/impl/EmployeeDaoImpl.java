/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.business.employee.dao.EmployeeDao;
import com.cloudstong.business.employee.model.Employee;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;

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
@Repository("employeeDao")
public class EmployeeDaoImpl extends BaseMyBatisDaoImpl<Employee, Long> implements EmployeeDao  {

}
