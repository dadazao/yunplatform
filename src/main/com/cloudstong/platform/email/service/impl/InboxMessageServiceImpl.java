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

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.email.dao.InboxMessageDao;
import com.cloudstong.platform.email.dao.MailAccountDao;
import com.cloudstong.platform.email.model.InboxMessage;
import com.cloudstong.platform.email.model.MailAttachment;
import com.cloudstong.platform.email.model.mapper.InboxMessageMapper;
import com.cloudstong.platform.email.service.InboxMessageService;
import com.cloudstong.platform.email.service.MailAttachmentService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
@Service("inboxMessageService")
public class InboxMessageServiceImpl implements InboxMessageService {

	@Resource
	private InboxMessageDao inboxMessageDao;

	@Resource
	private MailAccountDao mailAccountDao;

	@Resource
	private MailAttachmentService mailAttachmentService;

	@Override
	public PageResult findInboxMessage(QueryCriteria queryCriteria) {
		return inboxMessageDao.query(queryCriteria, new InboxMessageMapper());
	}

	@Override
	public void doSaveInboxMessage(final List<InboxMessage> messageList, final SysUser user) {
		String sql = "INSERT INTO sys_inbox ( id,tbl_cc,tbl_content,tbl_date,tbl_from,tbl_subject,tbl_to,comm_createDate,comm_createBy,tbl_userid,tbl_read,tbl_mimemessageid,tbl_useremail) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Date date = new Date();
		final java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
		inboxMessageDao.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InboxMessage m = messageList.get(i);
				ps.setLong(1, m.getId());
				ps.setString(2, m.getCc());
				ps.setString(3, m.getContent());
				ps.setTimestamp(4, new java.sql.Timestamp(m.getDate().getTime()));
				ps.setString(5, m.getFrom());
				ps.setString(6, m.getSubject());
				ps.setString(7, m.getTo());
				ps.setTimestamp(8, time);
				ps.setLong(9, user.getId());
				ps.setLong(10, user.getId());
				ps.setBoolean(11, m.isRead());
				ps.setString(12, m.getMimeMessageId());
				ps.setString(13, m.getUseremail());
			}

			@Override
			public int getBatchSize() {
				return messageList.size();
			}
		});
		// 更新收取邮件时间
		if (!messageList.isEmpty()) {
			String updateSql = "update sys_mailaccount set tbl_lastreceivedate = ? where tbl_address = ?";
			mailAccountDao.update(updateSql, new Object[] { new Date(date.getTime() - 2000), messageList.get(0).getTo() });
		}
	}

	@Override
	public void doSaveInboxMessage(InboxMessage m) {
		m.setId(UniqueIdUtil.genId());
		String sql = "INSERT INTO sys_inbox ( id,tbl_cc,tbl_content,tbl_date,tbl_from,tbl_subject,tbl_to,comm_createDate,comm_createBy,tbl_userid,tbl_read,tbl_mimemessageid,tbl_useremail) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		final java.sql.Timestamp time = new java.sql.Timestamp(new Date().getTime());
		inboxMessageDao.update(
				sql,
				new Object[] { m.getId(), m.getCc(), m.getContent(), m.getDate(), m.getFrom(), m.getSubject(), m.getTo(), time, m.getUserid(),
						m.getUserid(), m.isRead(), m.getMimeMessageId(), m.getUseremail() });
	}

	@Override
	public void doPutIntoDustbin(Long id) {
		String sql = "INSERT INTO sys_mailrubbish(id,comm_createDate,comm_updateDate,tbl_to,tbl_from,tbl_cc,tbl_subject,tbl_content,tbl_attachments,tbl_userid,"
				+ "tbl_date,tbl_read,tbl_mimemessageid,tbl_typ,tbl_draft) SELECT id,now(),now(),tbl_to,tbl_from,tbl_cc,tbl_subject,tbl_content,tbl_attachments,tbl_userid,"
				+ "tbl_date,tbl_read,tbl_mimemessageid,1,0 FROM sys_inbox inbox where inbox.id = ?";
		inboxMessageDao.update(sql, new Object[] { id });
		String delSql = "update sys_inbox set comm_mark_for_delete = ? where id = ?";
		inboxMessageDao.update(delSql, new Object[] { 1, id });
	}

	@Override
	public void doPutIntoDustbin(final Long[] ids) {
		String sql = "INSERT INTO sys_mailrubbish(id,comm_createDate,comm_updateDate,tbl_to,tbl_from,tbl_cc,tbl_subject,tbl_content,tbl_userid,"
				+ "tbl_date,tbl_read,tbl_mimemessageid,tbl_type,tbl_draft) SELECT id,now(),now(),tbl_to,tbl_from,tbl_cc,tbl_subject,tbl_content,tbl_userid,"
				+ "tbl_date,tbl_read,tbl_mimemessageid,1,0 FROM sys_inbox inbox where inbox.id = ?";
		inboxMessageDao.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, ids[i]);
			}

			@Override
			public int getBatchSize() {
				return ids.length;
			}
		});

		String delSql = "update sys_inbox set comm_mark_for_delete = ? where id = ?";
		inboxMessageDao.batchUpdate(delSql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, 1);
				ps.setLong(2, ids[i]);
			}

			@Override
			public int getBatchSize() {
				return ids.length;
			}
		});

	}

	@Override
	public InboxMessage findInboxMessageById(Long id) {
		return inboxMessageDao.selectById(id,new InboxMessageMapper());
	}

	@Override
	public InboxMessage doViewInboxMessage(Long id) {
		String sql = "update sys_inbox set tbl_read = ? where id = ? ";
		inboxMessageDao.update(sql, new Object[] { 1, id });
		InboxMessage inboxMessage = inboxMessageDao.selectById(id,new InboxMessageMapper());
		// 处理附件
		List<MailAttachment> attaList = mailAttachmentService.findMailAttachmentByMailId(id);
		StringBuilder sb = new StringBuilder();
		for (MailAttachment atta : attaList) {
			sb.append("<a onclick='ns.email.downloadFile(\"" + atta.getId() + "\")' style=\"cursor:pointer;margin-right:10px;color:blue;\">"
					+ atta.getName() + "</a>");
		}
		inboxMessage.setAttachments(sb.toString());
		return inboxMessage;
	}

}
