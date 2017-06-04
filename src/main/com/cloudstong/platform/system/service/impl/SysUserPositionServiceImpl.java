/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.system.model.UserPosition;
import com.cloudstong.platform.system.service.SysUserPositionService;

/**
 * @author Allan
 * 
 *         Created on 2014-9-25
 * 
 *         Description:
 * 
 */
@Repository("sysUserPositionService")
public class SysUserPositionServiceImpl implements SysUserPositionService {

	@Override
	public List<UserPosition> getUserByPosIds(String cmpIds) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

}
