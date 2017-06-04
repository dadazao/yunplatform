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
import com.cloudstong.platform.system.model.AuthCustomerHadware;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
public interface AuthCustomerHadwareService {

	public PageResult queryAuthCustomerHadware(QueryCriteria queryCriteria);
	
	public List<AuthCustomerHadware> queryAll();

	public Long doSaveOrUpdate(AuthCustomerHadware entity,SysUser user);

	public AuthCustomerHadware findAuthCustomerHadwareById(Long id);

	public void doDeleteAuthCustomerHadwares(Long[] selectIds);
	
}
