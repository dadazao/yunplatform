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
import com.cloudstong.platform.system.model.AuthTemplate;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
public interface AuthTemplateService {

	public PageResult queryAuthTemplate(QueryCriteria queryCriteria);

	public Long doSaveOrUpdate(AuthTemplate entity,SysUser user);

	public AuthTemplate findAuthTemplateById(Long id);

	public void doDeleteAuthTemplates(Long[] selectIds);
	
	public List<AuthTemplate> findAuthTemplateByPId(Long id);
}
