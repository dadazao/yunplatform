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

import com.cloudstong.platform.desktop.model.DesktopDesign;

/**
 * @author Jason
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class DesktopDesignMapper implements RowMapper<DesktopDesign> {

	@Override
	public DesktopDesign mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DesktopDesign desktopDesign = new DesktopDesign();
		
		desktopDesign.setId(rs.getLong("id"));
		desktopDesign.setColumnNum(rs.getInt("tbl_columnnum"));
		desktopDesign.setColumnWidths(rs.getString("tbl_columnwidths"));
		desktopDesign.setLayoutItemIds(rs.getString("tbl_layoutitemids"));
		desktopDesign.setUserid(rs.getLong("tbl_userid"));
		
		desktopDesign.setCreateBy(rs.getLong("comm_createBy"));
		desktopDesign.setUpdateBy(rs.getLong("comm_updateBy"));
		desktopDesign.setCreateDate(rs.getTimestamp("comm_createDate"));
		desktopDesign.setUpdateDate(rs.getTimestamp("comm_updateDate"));
		
		return desktopDesign;
	}
}
