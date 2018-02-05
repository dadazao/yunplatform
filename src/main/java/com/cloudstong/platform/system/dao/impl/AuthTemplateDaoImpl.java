/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.AuthTemplateDao;
import com.cloudstong.platform.system.model.AuthTemplate;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
@Repository("authTemplateDao")
public class AuthTemplateDaoImpl extends BaseJdbcDaoImpl<AuthTemplate, Long> implements
		AuthTemplateDao {

	@Override
	public String getTable() {
		return "sys_authtemplate";
	}

}
