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
import com.cloudstong.platform.system.model.AuthProduct;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
public interface AuthProductService {

	public PageResult queryAuthProduct(QueryCriteria queryCriteria);
	
	public List<AuthProduct> queryAll();

	public Long doSaveOrUpdate(AuthProduct entity,SysUser user);

	public AuthProduct findAuthProductById(Long id);

	public void doDeleteAuthProducts(Long[] selectIds);
	
}
