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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.email.dao.MailMessageDao;
import com.cloudstong.platform.email.model.MailAttachment;
import com.cloudstong.platform.email.model.MailMessage;
import com.cloudstong.platform.email.model.mapper.MailMessageMapper;
import com.cloudstong.platform.email.service.ContactService;
import com.cloudstong.platform.email.service.MailAttachmentService;
import com.cloudstong.platform.email.service.MailMessageService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
@Service("mailMessageService")
public class MailMessageServiceImpl implements MailMessageService {

	@Resource
	private MailMessageDao mailMessageDao;

	@Resource
	private ContactService contactService;

	@Resource
	private MailAttachmentService mailAttachmentService;

	@Override
	public MailMessage findMailMessageById(Long id) {
		return mailMessageDao.selectById(id, new MailMessageMapper());
	}

	@Override
	public List<MailMessage> getAllMailMessage() {
		return mailMessageDao.getAll(new MailMessageMapper());
	}

	@Override
	public PageResult queryMailMessage(QueryCriteria queryCriteria) {
		return mailMessageDao.query(queryCriteria, new MailMessageMapper());
	}

	@Override
	public void doUpdateMailMessage(MailMessage mailMessage) {
		String sql = "update sys_outbox set tbl_cc=?,tbl_content=?,tbl_draft=?,tbl_from=?,tbl_subject=?,tbl_to=?,tbl_userid=?,comm_updateDate=?,comm_updateBy=? where id = ?";
		mailMessageDao.update(sql, new Object[] { mailMessage.getCc(), mailMessage.getContent(), mailMessage.getDraft(), mailMessage.getFrom(),
				mailMessage.getSubject(), mailMessage.getTo(), mailMessage.getUserid(), new Date(), mailMessage.getUserid(), mailMessage.getId() });
	}

	@Override
	public void doSaveMailMessage(MailMessage mailMessage) {
		Date date = new Date();
		String sql = "INSERT INTO sys_outbox (id,tbl_cc,tbl_content,tbl_draft,tbl_from,tbl_subject,tbl_to,tbl_userid,comm_createDate,comm_createBy,tbl_date) values(?,?,?,?,?,?,?,?,?,?,?)";
		mailMessageDao.update(sql, new Object[] { mailMessage.getId(), mailMessage.getCc(), mailMessage.getContent(), mailMessage.getDraft(),
				mailMessage.getFrom(), mailMessage.getSubject(), mailMessage.getTo(), mailMessage.getUserid(), date, mailMessage.getUserid(), date });
	}

	@Override
	public void doDeleteMailMessages(Long[] ids) {
		for (Long id : ids) {
			mailMessageDao.delete(id);
		}
	}

	@Override
	public void doDraftenMailMessage(MailMessage mailMessage) {
		mailMessage.setDraft(1);
		doSaveOrUpdateMailMessage(mailMessage);
	}

	@Override
	public void doSaveOrUpdateMailMessage(MailMessage mailMessage) {
		String sql = "select count(1) from sys_outbox where id = ? ";
		long num = mailMessageDao.getJdbcTemplate().queryForObject(sql, Long.class, mailMessage.getId());
		if (num > 0) {
			doUpdateMailMessage(mailMessage);
		} else {
			doSaveMailMessage(mailMessage);
		}
	}

	@Override
	public void doSendMailMessage(MailMessage mailMessage) {
		doSaveOrUpdateMailMessage(mailMessage);
		String tos = mailMessage.getTo();
		String ccs = mailMessage.getCc();
		String emails[] = (tos + ";" + (ccs == null ? "" : ccs)).split(";");
		Set<String> emailSet = new HashSet<String>();
		for (String email : emails) {
			emailSet.add(email);
		}
		contactService.doUpdateContactLastUsedTime(emailSet);
		contactService.doInsertNewContact(emailSet, mailMessage.getUserid());
	}

	@Override
	public PageResult findMailMessage(QueryCriteria queryCriteria) {
		return mailMessageDao.query(queryCriteria, new MailMessageMapper());
	}

	@Override
	public void doPutIntoDustbin(final Long[] mailMessageIDs) {
		String sql = "INSERT INTO sys_mailrubbish(id,comm_createDate,comm_updateDate,tbl_to,tbl_from,tbl_cc,tbl_subject,tbl_content,tbl_userid,"
				+ "tbl_date,tbl_draft,tbl_type,tbl_read) SELECT id,now(),now(),tbl_to,tbl_from,tbl_cc,tbl_subject,tbl_content,tbl_userid,"
				+ "tbl_date,tbl_draft,2,1 FROM sys_outbox outbox where outbox.id = ?";
		mailMessageDao.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, mailMessageIDs[i]);
			}

			@Override
			public int getBatchSize() {
				return mailMessageIDs.length;
			}
		});

		String delSql = "update sys_outbox set comm_mark_for_delete = ? where id = ?";
		mailMessageDao.batchUpdate(delSql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, 1);
				ps.setLong(2, mailMessageIDs[i]);
			}

			@Override
			public int getBatchSize() {
				return mailMessageIDs.length;
			}
		});

	}

	@Override
	public MailMessage viewMailMessage(Long id) {
		MailMessage message = findMailMessageById(id);
		// 处理附件
		List<MailAttachment> attaList = mailAttachmentService.findMailAttachmentByMailId(id);
		StringBuilder sb = new StringBuilder();
		for (MailAttachment atta : attaList) {
			sb.append("<a onclick='ns.email.downloadFile(\"" + atta.getId() + "\")' style=\"cursor:pointer;margin-right:10px;color:blue;\">"
					+ atta.getName() + "</a>");
		}
		message.setAttachments(sb.toString());
		return message;
	}

}
