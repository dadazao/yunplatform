/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.system.model.UserRole;

/**
 * @author Allan
 * 
 *         Created on 2014-9-10
 * 
 *         Description:
 * 
 */
public interface SysUserRoleService {

	List<UserRole> getUserByRoleIds(String cmpIds);

}
