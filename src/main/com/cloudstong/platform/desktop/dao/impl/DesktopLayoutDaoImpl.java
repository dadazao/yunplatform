/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.desktop.dao.DesktopLayoutDao;
import com.cloudstong.platform.desktop.model.DesktopLayout;
import com.cloudstong.platform.desktop.model.mapper.DesktopLayoutMapper;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
@Repository("desktopLayoutDao")
public class DesktopLayoutDaoImpl extends BaseJdbcDaoImpl<DesktopLayout, Long> implements DesktopLayoutDao {

	@Override
	public String getTable() {
		return "sys_desktoplayout";
	}

	@Override
	public List<DesktopLayout> getAllOrderBy(String orderField, String direction) {
		String sql = "select * from " + getTable() + " order by " + orderField + " " + direction;
		return jdbcTemplate.query(sql, new DesktopLayoutMapper());
	}
}
