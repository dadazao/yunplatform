/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserOrg;
import com.cloudstong.platform.system.service.SysUserOrgService;

/**
 * @author Allan
 * 
 *         Created on 2014-9-25
 * 
 *         Description:
 * 
 */
@Repository("sysUserOrgService")
public class SysUserOrgServiceImpl implements SysUserOrgService {

	@Override
	public List<SysUser> getLeaderUserByUserId(Long startUserId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List<SysUserOrg> getUserByOrgIds(String cmpIds) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List<SysUserOrg> getChargeByOrgId(Long orgId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List getLeaderUserByOrgId(Long id, boolean b) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List getOrgByUserId(Long startUserId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List getByOrgId(Long orgId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public String getLeaderPosByUserId(Long userId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List<TaskExecutor> getLeaderByUserId(Long userId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public List<TaskExecutor> getLeaderByOrgId(Long orgId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

}
