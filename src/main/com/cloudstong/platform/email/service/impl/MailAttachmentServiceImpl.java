/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.email.dao.MailAttachmentDao;
import com.cloudstong.platform.email.model.MailAttachment;
import com.cloudstong.platform.email.model.mapper.MailAttachmentMapper;
import com.cloudstong.platform.email.service.MailAttachmentService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
@Service("mailAttachmentService")
public class MailAttachmentServiceImpl implements MailAttachmentService {

	@Resource
	private MailAttachmentDao mailAttachmentDao;

	@Override
	public MailAttachment findMailAttachmentById(Long id) {
		return mailAttachmentDao.selectById(id, new MailAttachmentMapper());
	}

	@Override
	public void doSaveMailAttachments(final List<MailAttachment> mailAttachments) {
		String sql = "insert into sys_mailattachment(id,tbl_name,tbl_path,tbl_mailid,comm_createDate) values (?,?,?,?,?) ";
		final Date date = new Date();
		mailAttachmentDao.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MailAttachment atta = mailAttachments.get(i);
				atta.setId(UniqueIdUtil.genId());
				ps.setLong(1, atta.getId());
				ps.setString(2, atta.getName());
				ps.setString(3, atta.getPath());
				ps.setLong(4, atta.getMailId());
				ps.setTimestamp(5, new java.sql.Timestamp(date.getTime()));
			}

			@Override
			public int getBatchSize() {
				return mailAttachments.size();
			}
		});
	}

	@Override
	public List<MailAttachment> findMailAttachmentByMailId(Long id) {
		String sql = "select * from sys_mailattachment where tbl_mailid = ?";
		return mailAttachmentDao.select(sql, new Object[] { id }, new MailAttachmentMapper());
	}
}
