/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserOrg;

/**
 * @author Allan
 * 
 *         Created on 2014-9-10
 * 
 *         Description:
 * 
 */
public interface SysUserOrgService {

	List<SysUser> getLeaderUserByUserId(Long startUserId);

	List<SysUserOrg> getUserByOrgIds(String cmpIds);

	List<SysUserOrg> getChargeByOrgId(Long orgId);

	List getLeaderUserByOrgId(Long id, boolean b);

	List getOrgByUserId(Long startUserId);

	List getByOrgId(Long orgId);

	String getLeaderPosByUserId(Long userId);

	List<TaskExecutor> getLeaderByUserId(Long userId);

	List<TaskExecutor> getLeaderByOrgId(Long orgId);

}
