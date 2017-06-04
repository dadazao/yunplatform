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

import com.cloudstong.platform.email.model.MailAccount;

/**
 * @author Jason
 * 
 *         Created on 2014-9-13
 * 
 *         Description:
 * 
 */
public class MailAccountMapper implements RowMapper<MailAccount> {

	@Override
	public MailAccount mapRow(ResultSet rs, int rowNum) throws SQLException {

		MailAccount mailAccount = new MailAccount();

		mailAccount.setId(rs.getLong("id"));
		mailAccount.setAddress(rs.getString("tbl_address"));
		mailAccount.setDeflt(rs.getInt("tbl_deflt"));
		mailAccount.setLastReceiveDate(rs.getTimestamp("tbl_lastreceivedate"));
		mailAccount.setMailType(rs.getString("tbl_mailtype"));
		mailAccount.setName(rs.getString("tbl_name"));
		mailAccount.setPassword(rs.getString("tbl_password"));
		mailAccount.setPopHost(rs.getString("tbl_pophost"));
		mailAccount.setPopPort(rs.getInt("tbl_popport"));
		mailAccount.setSmtpHost(rs.getString("tbl_smtphost"));
		mailAccount.setSmtpPort(rs.getInt("tbl_smtpport"));
		mailAccount.setUserId(rs.getLong("tbl_userid"));

		mailAccount.setCreateBy(rs.getLong("comm_createBy"));
		mailAccount.setUpdateBy(rs.getLong("comm_updateBy"));
		mailAccount.setCreateDate(rs.getTimestamp("comm_createDate"));
		mailAccount.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return mailAccount;
	}
}