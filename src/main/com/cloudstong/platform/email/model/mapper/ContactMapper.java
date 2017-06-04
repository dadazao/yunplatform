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

import com.cloudstong.platform.email.model.Contact;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class ContactMapper implements RowMapper<Contact> {

	@Override
	public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {

		Contact contact = new Contact();

		contact.setId(rs.getLong("id"));
		contact.setEmail(rs.getString("tbl_email"));
		contact.setLastUsedTime(rs.getTimestamp("tbl_lastusedtime"));
		contact.setUserId(rs.getLong("tbl_userid"));

		contact.setCreateBy(rs.getLong("comm_createBy"));
		contact.setUpdateBy(rs.getLong("comm_updateBy"));
		contact.setCreateDate(rs.getTimestamp("comm_createDate"));
		contact.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return contact;
	}
}
