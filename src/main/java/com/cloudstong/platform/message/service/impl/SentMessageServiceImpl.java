/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.message.dao.SentMessageDao;
import com.cloudstong.platform.message.model.SentMessage;
import com.cloudstong.platform.message.model.mapper.SentMessageMapper;
import com.cloudstong.platform.message.service.SentMessageService;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
@Service("sentMessageService")
public class SentMessageServiceImpl implements SentMessageService {

	@Resource
	private SentMessageDao sentMessageDao;

	@Resource
	private SysUserDao sysUserDao;

	@Override
	public PageResult querySentMessage(QueryCriteria queryCriteria) {
		return sentMessageDao.query(queryCriteria, new SentMessageMapper());
	}

	@Override
	public List<Long> doSendMessage(SentMessage sentMessage) {
		String receivers = sentMessage.getReceiver();
		String receiverGroups = sentMessage.getReceiveGroups();
		Set<Long> receiversSet = new HashSet<Long>();
		StringBuilder receiversName = new StringBuilder();
		if (receivers != null && receivers.trim().length() > 0) {
			String[] receiverArray = receivers.split(",");
			for (String re : receiverArray) {
				receiversSet.add(Long.valueOf(re));
				receiversName.append(
						sentMessageDao.getJdbcTemplate().queryForObject("select tbl_yonghuxingming from sys_user where id = ?", String.class,
								Long.valueOf(re))).append(";");
			}
			receiversName.deleteCharAt(receiversName.length() - 1);
		}
		StringBuilder orgName = new StringBuilder();
		if (receiverGroups != null && receiverGroups.trim().length() > 0) {
			String[] groups = receiverGroups.split(",");
			for (String group : groups) {
				List<Long> useridList = sysUserDao.getUserIdsByOrgId(Long.valueOf(group));
				receiversSet.addAll(useridList);
				orgName.append(
						sentMessageDao.getJdbcTemplate().queryForObject("select tbl_name from sys_org where id= ? ", String.class,
								Long.valueOf(group))).append(";");
			}
			orgName.deleteCharAt(orgName.length() - 1);
		}

		sentMessage.setReceiver(receiversName.toString());
		sentMessage.setReceiveGroups(orgName.toString());

		final List<Long> receiversList = new ArrayList<Long>(receiversSet.size());
		for (Long re : receiversSet) {
			if (re == null) {
				continue;
			}
			receiversList.add(re);
		}
		this.sentMessage(sentMessage, receiversList);// Collections.list(Collections.enumeration(receiversSet)));
		return receiversList;
	}

	private void sentMessage(final SentMessage sentMessage, final List<Long> receiversList) {
		if (receiversList != null && !receiversList.isEmpty()) {

			if (sentMessage.getId() == null) {
				sentMessage.setId(UniqueIdUtil.genId());
			}

			Date date = new Date();

			// 保存到发送的消息表中
			String sqlSent = "insert into sys_sentmessage ( id,tbl_content,tbl_date,tbl_needreply,tbl_receivegroups,tbl_receiver,tbl_sender,tbl_sendername,tbl_subject,tbl_type,"
					+ "comm_createDate,comm_updateDate,comm_createBy,comm_updateBy,tbl_replymessageid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			sentMessageDao.update(
					sqlSent,
					new Object[] { sentMessage.getId(), sentMessage.getContent(), date, sentMessage.isNeedReply(), sentMessage.getReceiveGroups(),
							sentMessage.getReceiver(), sentMessage.getSender(), sentMessage.getSenderName(), sentMessage.getSubject(),
							sentMessage.getType(), date, date, sentMessage.getSender(), sentMessage.getSender(), sentMessage.getReplyMessageId() });

			final java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

			// 向收件箱 插入消息
			String sqlRec = "insert into sys_receivedmessage ( id,tbl_content,tbl_date,tbl_read,tbl_receiver,tbl_replystate,tbl_sender,tbl_sentmessageid,tbl_subject,tbl_type,tbl_sendername)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?)";
			sentMessageDao.batchUpdate(sqlRec, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, UniqueIdUtil.genId());
					ps.setString(2, sentMessage.getContent());
					ps.setTimestamp(3, timestamp);
					ps.setBoolean(4, false); // read
					ps.setLong(5, receiversList.get(i));
					ps.setInt(6, sentMessage.isNeedReply() ? 1 : 0);
					ps.setLong(7, sentMessage.getSender());
					ps.setLong(8, sentMessage.getId());
					ps.setString(9, sentMessage.getSubject());
					ps.setString(10, sentMessage.getType());
					ps.setString(11, sentMessage.getSenderName());
				}

				@Override
				public int getBatchSize() {
					return receiversList.size();
				}
			});
		}
	}

	@Override
	public SentMessage findSentMessageById(Long id) {
		return sentMessageDao.selectById(id, new SentMessageMapper());
	}

	@Override
	public List<Map<String, Object>> getReaderList(Long id) {
		return sentMessageDao.getReaderList(id);
	}

	@Override
	public List<Map<String, Object>> getReplyedMessage(Long id, SysUser user) {
		return sentMessageDao.getReplyedMessageList(id, user);
	}

	@Override
	public void doDeleteSentMessage(Long[] sentMessageIDs) {
		for (Long id : sentMessageIDs) {
			sentMessageDao.update("delete from sys_sentmessage where id = ? ", new Object[] { id });
		}
	}
}
