/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.userChiose.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.userChiose.dao.UserChioseDao;
import com.cloudstong.platform.resource.userChiose.service.UserChioseService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-12-25
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("userChioseService")
public class UserChioseServiceImpl implements UserChioseService {

	@Resource
	private UserChioseDao userChioseDao;
	
	@Override
	public List<SysUser> getUsers() {
		return userChioseDao.getUsers();
	}

}
