/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.email.dao.MailAttachmentDao;
import com.cloudstong.platform.email.model.MailAttachment;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
@Repository("mailAttachmentDao")
public class MailAttachmentDaoImpl extends BaseJdbcDaoImpl<MailAttachment, Long> implements MailAttachmentDao {

	@Override
	public String getTable() {
		return "sys_mailattachment";
	}

}
