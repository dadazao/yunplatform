/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.metadata.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Allan
 * Created on 2014-1-15
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public class ColumnRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Column u = new Column();
		u.setId(rs.getLong("id"));
		u.setColumnName(rs.getString("tbl_columnName"));
		u.setColumnZhName(rs.getString("tbl_columnZhName"));
		u.setColumnEnName(rs.getString("tbl_columnEnName"));
		u.setComment(rs.getString("tbl_comment"));
		u.setDataType(rs.getString("tbl_dataType"));
		u.setBelongTable(rs.getString("tbl_belongTable"));
		u.setDefaultValue(rs.getString("tbl_defaultValue"));
		u.setLength(rs.getInt("tbl_length"));
		u.setIsPrimaryKey(rs.getInt("tbl_isPrimaryKey"));
		u.setIsNullable(rs.getInt("tbl_isNullable"));
		u.setIsPublish(rs.getInt("tbl_isPublish"));
		u.setCreateBy(rs.getLong("comm_createBy"));
		u.setCreateDate(rs.getTimestamp("comm_createDate"));
		u.setUpdateBy(rs.getLong("comm_updateBy"));
		u.setUpdateDate(rs.getTimestamp("comm_updateDate"));
		u.setStatus(rs.getInt("comm_status"));
		u.setTableId(rs.getLong("tbl_tableId"));
		u.setTableZhName(rs.getString("tbl_tableZhName"));
		u.setUserName(rs.getString("tbl_createname"));
		u.setRemark(rs.getString("tbl_remark"));
		return u;
	}
}
