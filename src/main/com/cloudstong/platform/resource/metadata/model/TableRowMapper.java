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
public class TableRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Table u = new Table();
		u.setId(rs.getLong("id"));
		u.setTableFunction(rs.getString("tbl_tableFunction"));
		u.setTableName(rs.getString("tbl_tableName"));
		u.setTableZhName(rs.getString("tbl_tableZhName"));
		u.setTableEnName(rs.getString("tbl_tableEnName"));
		u.setTableSchema(rs.getString("tbl_tableSchema"));
		u.setTableCode(rs.getString("tbl_tableCode"));
		u.setTableType(rs.getString("tbl_tableType"));
		u.setTableRelationType(rs.getString("tbl_tableRelationType"));
		u.setPrimaryKey(rs.getString("tbl_primaryKey"));
		u.setHasForeignKey(rs.getInt("tbl_hasForeignKey"));
		u.setHasIndex(rs.getInt("tbl_hasIndex"));
		u.setStatus(rs.getInt("comm_status"));
		u.setCreateBy(rs.getLong("comm_createBy"));
		u.setCreateDate(rs.getDate("comm_createDate"));
		u.setUpdateBy(rs.getLong("comm_updateBy"));
		u.setUpdateDate(rs.getDate("comm_updateDate"));
		u.setRemark(rs.getString("tbl_remark"));
		return u;
	}
}
