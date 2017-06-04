/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.AuthCustomerDao;
import com.cloudstong.platform.system.model.AuthCustomer;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
@Repository("authCustomerDao")
public class AuthCustomerDaoImpl extends BaseJdbcDaoImpl<AuthCustomer, Long> implements
		AuthCustomerDao {

	@Override
	public String getTable() {
		return "sys_authcustomer";
	}

}
