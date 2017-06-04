/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.AuthCustomer;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
public interface AuthCustomerService {

	public PageResult queryAuthCustomer(QueryCriteria queryCriteria);
	
	public List<AuthCustomer> queryAll();

	public Long doSaveOrUpdate(AuthCustomer entity,SysUser user);

	public AuthCustomer findAuthCustomerById(Long id);

	public void doDeleteAuthCustomers(Long[] selectIds);
}
