/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.email.model.InboxMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class InboxMessageMapper implements RowMapper<InboxMessage> {

	@Override
	public InboxMessage mapRow(ResultSet rs, int rowNum) throws SQLException {

		InboxMessage inboxMessage = new InboxMessage();

		inboxMessage.setId(rs.getLong("id"));
		inboxMessage.setCc(rs.getString("tbl_cc"));
		inboxMessage.setContent(rs.getString("tbl_content"));
		inboxMessage.setDate(rs.getTimestamp("tbl_date"));
		inboxMessage.setFrom(rs.getString("tbl_from"));
		inboxMessage.setMimeMessageId(rs.getString("tbl_mimemessageid"));
		inboxMessage.setRead(rs.getBoolean("tbl_read"));
		inboxMessage.setSubject(rs.getString("tbl_subject"));
		inboxMessage.setTo(rs.getString("tbl_to"));
		inboxMessage.setUseremail(rs.getString("tbl_useremail"));
		inboxMessage.setUserid(rs.getLong("tbl_userid"));

		inboxMessage.setCreateBy(rs.getLong("comm_createBy"));
		inboxMessage.setUpdateBy(rs.getLong("comm_updateBy"));
		inboxMessage.setCreateDate(rs.getTimestamp("comm_createDate"));
		inboxMessage.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return inboxMessage;
	}
}
