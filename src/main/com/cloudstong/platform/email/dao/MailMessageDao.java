/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.email.model.MailMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
public interface MailMessageDao extends BaseJdbcDao<MailMessage, Long> {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	JdbcTemplate getJdbcTemplate();

}
