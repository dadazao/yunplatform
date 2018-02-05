/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.AuthCustomerHadwareDao;
import com.cloudstong.platform.system.model.AuthCustomerHadware;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
@Repository("authCustomerHadwareDao")
public class AuthCustomerHadwareDaoImpl extends BaseJdbcDaoImpl<AuthCustomerHadware, Long>
		implements AuthCustomerHadwareDao {

	@Override
	public String getTable() {
		return "sys_authcustomer_hadware";
	}

}
