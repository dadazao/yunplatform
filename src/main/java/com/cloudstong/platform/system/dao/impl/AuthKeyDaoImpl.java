/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.AuthKeyDao;
import com.cloudstong.platform.system.model.AuthKey;

/**
 * @author John
 * 
 *         Created on 2014-11-25
 * 
 *         Description:
 * 
 */
@Repository("authKeyDao")
public class AuthKeyDaoImpl extends BaseJdbcDaoImpl<AuthKey, Long> implements
		AuthKeyDao {

	@Override
	public String getTable() {
		return "sys_authkey";
	}

}
