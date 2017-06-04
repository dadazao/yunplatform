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

import com.cloudstong.platform.desktop.model.DesktopLayout;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class DesktopLayoutMapper implements RowMapper<DesktopLayout> {

	@Override
	public DesktopLayout mapRow(ResultSet rs, int rowNum) throws SQLException {

		DesktopLayout desktopLayout = new DesktopLayout();

		desktopLayout.setId(rs.getLong("id"));
		desktopLayout.setColumnNum(rs.getInt("tbl_columnnum"));
		desktopLayout.setColumnWidths(rs.getString("tbl_columnwidths"));
		desktopLayout.setDeflt(rs.getBoolean("tbl_deflt"));
		desktopLayout.setDescription(rs.getString("tbl_description"));
		desktopLayout.setItems(rs.getString("tbl_items"));
		desktopLayout.setName(rs.getString("tbl_name"));

		desktopLayout.setCreateBy(rs.getLong("comm_createBy"));
		desktopLayout.setUpdateBy(rs.getLong("comm_updateBy"));
		desktopLayout.setCreateDate(rs.getTimestamp("comm_createDate"));
		desktopLayout.setUpdateDate(rs.getTimestamp("comm_updateDate"));

		return desktopLayout;
	}
}
