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

import com.cloudstong.platform.email.model.MailAttachment;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class MailAttachmentMapper implements RowMapper<MailAttachment> {

	@Override
	public MailAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {

		MailAttachment mailAttachment = new MailAttachment();

		mailAttachment.setId(rs.getLong("id"));
		mailAttachment.setMailId(rs.getLong("tbl_mailid"));
		mailAttachment.setName(rs.getString("tbl_name"));
		mailAttachment.setPath(rs.getString("tbl_path"));

		mailAttachment.setCreateBy(rs.getLong("comm_createBy"));
		mailAttachment.setUpdateBy(rs.getLong("comm_updateBy"));
		mailAttachment.setCreateDate(rs.getTimestamp("comm_createDate"));
		mailAttachment.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return mailAttachment;
	}
}
