/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.desktop.model.DesktopItem;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class DesktopItemMapper implements RowMapper<DesktopItem> {

	@Override
	public DesktopItem mapRow(ResultSet rs, int rowNum) throws SQLException {

		DesktopItem desktopItem = new DesktopItem();

		desktopItem.setId(rs.getLong("id"));
		desktopItem.setAlias(rs.getString("tbl_alias"));
		desktopItem.setMethodUrl(rs.getString("tbl_methodurl"));
		desktopItem.setModuleUrl(rs.getString("tbl_moduleurl"));
		desktopItem.setName(rs.getString("tbl_name"));
		desktopItem.setTemplateHtml(rs.getString("tbl_templatehtml"));

		desktopItem.setCreateBy(rs.getLong("comm_createBy"));
		desktopItem.setUpdateBy(rs.getLong("comm_updateBy"));
		desktopItem.setCreateDate(rs.getTimestamp("comm_createDate"));
		desktopItem.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return desktopItem;
	}
}
