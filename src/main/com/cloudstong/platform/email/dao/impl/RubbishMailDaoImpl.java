/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.email.dao.RubbishMailDao;
import com.cloudstong.platform.email.model.RubbishMail;

/**
 * @author Jason
 * 
 *         Created on 2014-9-18
 * 
 *         Description:
 * 
 */
@Repository("rubbishMailDao")
public class RubbishMailDaoImpl extends BaseJdbcDaoImpl<RubbishMail, Long> implements RubbishMailDao {

	@Override
	public String getTable() {
		return "sys_mailrubbish";
	}

	@Override
	public int getRubbishMailTypeById(Long id) {
		String sql = "select tbl_type from sys_mailrubbish where id = ? ";
		return jdbcTemplate.queryForObject(sql, Integer.class, id);
	}
}
