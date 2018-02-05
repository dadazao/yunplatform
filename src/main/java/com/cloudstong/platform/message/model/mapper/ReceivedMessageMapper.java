/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.message.model.ReceivedMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class ReceivedMessageMapper implements RowMapper<ReceivedMessage> {

	@Override
	public ReceivedMessage mapRow(ResultSet rs, int rowNum) throws SQLException {

		ReceivedMessage receivedMessage = new ReceivedMessage();

		receivedMessage.setId(rs.getLong("id"));
		receivedMessage.setContent(rs.getString("tbl_content"));
		receivedMessage.setDate(rs.getTimestamp("tbl_date"));
		receivedMessage.setRead(rs.getBoolean("tbl_read"));
		receivedMessage.setReceiver(rs.getLong("tbl_receiver"));
		receivedMessage.setReplyState(rs.getInt("tbl_replystate"));
		receivedMessage.setSender(rs.getLong("tbl_sender"));
		receivedMessage.setSenderName(rs.getString("tbl_sendername"));
		receivedMessage.setSentMessageId(rs.getLong("tbl_sentmessageid"));
		receivedMessage.setSubject(rs.getString("tbl_subject"));
		receivedMessage.setType(rs.getString("tbl_type"));

		receivedMessage.setCreateBy(rs.getLong("comm_createBy"));
		receivedMessage.setUpdateBy(rs.getLong("comm_updateBy"));
		receivedMessage.setCreateDate(rs.getTimestamp("comm_createDate"));
		receivedMessage.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return receivedMessage;
	}
}