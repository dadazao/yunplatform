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

import com.cloudstong.platform.email.model.MailMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class MailMessageMapper implements RowMapper<MailMessage> {

	@Override
	public MailMessage mapRow(ResultSet rs, int rowNum) throws SQLException {

		MailMessage mailMessage = new MailMessage();

		mailMessage.setId(rs.getLong("id"));
		mailMessage.setCc(rs.getString("tbl_cc"));
		mailMessage.setContent(rs.getString("tbl_content"));
		mailMessage.setDate(rs.getTimestamp("tbl_date"));
		mailMessage.setDraft(rs.getInt("tbl_draft"));
		mailMessage.setFrom(rs.getString("tbl_from"));
		mailMessage.setSubject(rs.getString("tbl_subject"));
		mailMessage.setTo(rs.getString("tbl_to"));
		mailMessage.setUserid(rs.getLong("tbl_userid"));

		mailMessage.setCreateBy(rs.getLong("comm_createBy"));
		mailMessage.setUpdateBy(rs.getLong("comm_updateBy"));
		mailMessage.setCreateDate(rs.getTimestamp("comm_createDate"));
		mailMessage.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return mailMessage;
	}
}
