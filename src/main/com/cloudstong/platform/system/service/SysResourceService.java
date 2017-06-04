/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.model.SysResourceExtend;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan
 * 
 *         Created on 2014-10-16
 * 
 *         Description:
 * 
 */
public interface SysResourceService {

	@Cacheable(value = "privilegeCache",key="'queryResourceSysRole:'+#systemId+#currentUser.id")
	public List<Catalog> queryResourceSysRole(Long systemId, SysUser currentUser);
	
	@Cacheable(value = "privilegeCache")
	public List<SysResourceExtend> getUrlRightMap(Long systemId);
	
	@Cacheable(value = "privilegeCache")
	public List<SysResourceExtend> getFunctionRoleList(Long systemId);

}
