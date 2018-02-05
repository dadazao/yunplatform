/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.email.dao.MailAccountDao;
import com.cloudstong.platform.email.model.MailAccount;

/**
 * @author Jason
 * 
 *         Created on 2014-9-13
 * 
 *         Description:
 * 
 */
@Repository("mailAccountDao")
public class MailAccountDaoImpl extends BaseJdbcDaoImpl<MailAccount, Long> implements MailAccountDao {

	@Override
	public String getTable() {
		return "sys_mailaccount";
	}

	@Override
	public void deleteCascade(Long id, Long userId) {

		// 查询出需要删除的与该账号关联的发件箱id和收件箱id
		String idout = "select id from sys_outbox where tbl_from = ( select tbl_address from sys_mailaccount where id = ? ) and tbl_userid = ? ";
		final List<Long> outboxIds = jdbcTemplate.queryForList(idout, new Object[] { id, userId }, Long.class);

		String idin = "select id from sys_inbox where tbl_useremail = (select tbl_address from sys_mailaccount where id = ? ) and tbl_userid =? ";
		final List<Long> inboxIds = jdbcTemplate.queryForList(idin, new Object[] { id, userId }, Long.class);

		// 删除发件箱(包括草稿箱)和收件箱的记录
		jdbcTemplate.batchUpdate("delete from sys_inbox where id = ?", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, inboxIds.get(i));
			}

			@Override
			public int getBatchSize() {
				return inboxIds.size();
			}
		});

		jdbcTemplate.batchUpdate("delete from sys_outbox where id = ?", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, outboxIds.get(i));
			}

			@Override
			public int getBatchSize() {
				return outboxIds.size();
			}
		});

		// 删除垃圾箱
		final List<Long> totalIdList = new ArrayList<Long>(outboxIds);
		totalIdList.addAll(inboxIds);

		System.out.println(totalIdList.toString());
		jdbcTemplate.batchUpdate("delete from sys_mailrubbish where id = ?", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, totalIdList.get(i));
			}

			@Override
			public int getBatchSize() {
				return totalIdList.size();
			}
		});

		// 删除附件表
		jdbcTemplate.batchUpdate("delete from sys_mailattachment where tbl_mailid = ?", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, totalIdList.get(i));
			}

			@Override
			public int getBatchSize() {
				return totalIdList.size();
			}
		});

		// 最后删除邮箱账号
		jdbcTemplate.update("delete from sys_mailaccount where id= ?", new Object[] { id });
	}
}
