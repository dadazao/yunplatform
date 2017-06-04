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
import com.cloudstong.platform.email.dao.InboxMessageDao;
import com.cloudstong.platform.email.model.InboxMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
@Repository("inboxMessageDao")
public class InboxMessageDaoImpl extends BaseJdbcDaoImpl<InboxMessage, Long> implements InboxMessageDao {

	@Override
	public String getTable() {
		return "sys_inbox";
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
