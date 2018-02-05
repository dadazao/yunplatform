/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.email.dao.MailMessageDao;
import com.cloudstong.platform.email.model.MailMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
@Repository("mailMessageDao")
public class MailMessageDaoImpl extends BaseJdbcDaoImpl<MailMessage, Long> implements MailMessageDao {

	@Override
	public String getTable() {
		return "sys_outbox";
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
