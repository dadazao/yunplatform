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

import com.cloudstong.platform.message.model.SentMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class SentMessageMapper implements RowMapper<SentMessage> {

	@Override
	public SentMessage mapRow(ResultSet rs, int rowNum) throws SQLException {

		SentMessage sentMessage = new SentMessage();

		sentMessage.setId(rs.getLong("id"));
		sentMessage.setContent(rs.getString("tbl_content"));
		sentMessage.setDate(rs.getTimestamp("tbl_date"));
		sentMessage.setNeedReply(rs.getBoolean("tbl_needreply"));
		sentMessage.setReceiveGroups(rs.getString("tbl_receivegroups"));
		sentMessage.setReceiver(rs.getString("tbl_receiver"));
		sentMessage.setReplyMessageId(rs.getLong("tbl_replymessageid"));
		sentMessage.setSender(rs.getLong("tbl_sender"));
		sentMessage.setSenderName(rs.getString("tbl_sendername"));
		sentMessage.setSubject(rs.getString("tbl_subject"));
		sentMessage.setType(rs.getString("tbl_type"));

		sentMessage.setCreateBy(rs.getLong("comm_createBy"));
		sentMessage.setUpdateBy(rs.getLong("comm_updateBy"));
		sentMessage.setCreateDate(rs.getTimestamp("comm_createDate"));
		sentMessage.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return sentMessage;
	}
}
