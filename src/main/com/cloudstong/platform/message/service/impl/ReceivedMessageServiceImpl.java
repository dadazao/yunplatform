/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.service.impl;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.message.dao.ReceivedMessageDao;
import com.cloudstong.platform.message.model.ReceivedMessage;
import com.cloudstong.platform.message.model.mapper.ReceivedMessageMapper;
import com.cloudstong.platform.message.service.ReceivedMessageService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
@Service("receivedMessageService")
public class ReceivedMessageServiceImpl implements ReceivedMessageService {

	@Resource
	private ReceivedMessageDao receivedMessageDao;

	@Override
	public PageResult queryReceivedMessage(QueryCriteria queryCriteria) {
		return receivedMessageDao.query(queryCriteria, new ReceivedMessageMapper());
	}

	@Override
	public void doSaveMessage(ReceivedMessage receivedMessage) {
		receivedMessage.setId(UniqueIdUtil.genId());
		String sql = "insert into sys_receivedmessage ( id,tbl_content,tbl_date,tbl_read,tbl_receiver,tbl_replystate,tbl_sender,tbl_sentmessageid,tbl_subject,tbl_type) values(?,?,?,?,?,?,?,?,?,?)";
		receivedMessageDao.update(sql, new Object[] { receivedMessage.getId(), receivedMessage.getContent(), receivedMessage.getDate(),
				receivedMessage.isRead(), receivedMessage.getReceiver(), receivedMessage.getReplyState(), receivedMessage.getSender(),
				receivedMessage.getSentMessageId(), receivedMessage.getSubject(), receivedMessage.getType() });

	}

	@Override
	public void doReadMessage(ReceivedMessage receivedMessage) {
		receivedMessage.setRead(true);
		String sql = "update sys_receivedmessage set tbl_read = ? where id = ?";
		receivedMessageDao.update(sql, new Object[] { receivedMessage.isRead(), receivedMessage.getId() });
	}

	@Override
	public void doDeleteMessage(Long[] ids) {
		for (Long id : ids) {
			receivedMessageDao.delete(id);
		}
	}

	@Override
	public void doSaveMessages(Set<ReceivedMessage> receivedMessageSet) {
		for (ReceivedMessage r : receivedMessageSet) {
			doSaveMessage(r);
		}
	}

	@Override
	public ReceivedMessage findReceivedMessageById(Long id) {
		return receivedMessageDao.selectById(id, new ReceivedMessageMapper());
	}

	@Override
	public Integer countLastestUnreadMessage(SysUser user) {
		return receivedMessageDao.count("select count(1) from sys_receivedmessage where tbl_receiver = ? and tbl_read = ?",
				new Object[] { user.getId(), false });
	}

	@Override
	public ReceivedMessage doViewReceivedMessage(Long id) {
		String sql = "update sys_receivedmessage set tbl_read = ?,comm_updateDate = ? where id = ? ";
		receivedMessageDao.update(sql, new Object[] { true, new Date(), id });
		return findReceivedMessageById(id);
	}
}
