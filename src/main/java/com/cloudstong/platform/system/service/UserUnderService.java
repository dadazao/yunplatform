/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;
import java.util.Set;

import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan
 * 
 *         Created on 2014-9-10
 * 
 *         Description:
 * 
 */
public interface UserUnderService {

	List<SysUser> getMyLeaders(Long prevUserId);

	Set<String> getMyUnderUserId(Long userId);

	Set getMyLeader(Long userId);

}
