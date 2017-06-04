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
import com.cloudstong.platform.system.model.AuthKey;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author John
 * 
 *         Created on 2014-11-25
 * 
 *         Description:
 * 
 */
public interface AuthKeyService {
	
	public PageResult queryAuthKey(QueryCriteria queryCriteria);
	
	public List<AuthKey> queryAll();

	public String[] doSaveOrUpdate(AuthKey entity,SysUser user);

	public AuthKey findAuthKeyById(Long id);

	public void doUpdateAuthKeysUnValid(Long[] selectIds, SysUser user);

}
