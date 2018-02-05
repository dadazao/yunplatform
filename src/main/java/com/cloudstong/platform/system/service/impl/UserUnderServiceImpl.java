/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.UserUnderService;

/**
 * @author Allan
 * 
 *         Created on 2014-10-14
 * 
 *         Description:
 * 
 */
@Service("userUnderService")
public class UserUnderServiceImpl implements UserUnderService {

	@Override
	public List<SysUser> getMyLeaders(Long prevUserId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public Set<String> getMyUnderUserId(Long userId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public Set getMyLeader(Long userId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

}
