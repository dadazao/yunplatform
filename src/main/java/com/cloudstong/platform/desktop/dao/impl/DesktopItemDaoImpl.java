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
import com.cloudstong.platform.desktop.dao.DesktopItemDao;
import com.cloudstong.platform.desktop.model.DesktopItem;
import com.cloudstong.platform.desktop.model.mapper.DesktopItemMapper;

/**
 * @author Jason
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
@Repository("desktopItemDao")
public class DesktopItemDaoImpl extends BaseJdbcDaoImpl<DesktopItem, Long> implements DesktopItemDao {

	@Override
	public String getTable() {
		return "sys_desktopitem";
	}

	/*
	 * @see
	 * com.cloudstongplatform.desktop.dao.DesktopItemDao#getAllByOrder(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public List<DesktopItem> getAllByOrder(String orderField, String direction) {
		String sql = "select * from " + getTable() + " order by " + orderField + " " + direction;
		return jdbcTemplate.query(sql, new DesktopItemMapper());
	}

}
