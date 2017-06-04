/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.email.dao.ContactDao;
import com.cloudstong.platform.email.model.Contact;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
@Repository("contactDao")
public class ContactDaoImpl extends BaseJdbcDaoImpl<Contact, Long> implements ContactDao {

	@Override
	public String getTable() {
		return "sys_contact";
	}

}
