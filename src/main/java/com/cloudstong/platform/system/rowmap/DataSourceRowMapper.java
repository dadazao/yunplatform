/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.system.model.DataSourcePojo;

/**
 * @author liuqi
 * 
 *         Created on 2014-10-9
 * 
 *         Description:
 * 
 */
@SuppressWarnings("rawtypes")
public class DataSourceRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DataSourcePojo dataSource = new DataSourcePojo();
		dataSource.setId(rs.getLong("id"));
		dataSource.setDsName(rs.getString("tbl_dsName"));
		dataSource.setDsDriver(rs.getString("tbl_dsDriver"));
		dataSource.setDsUrl(rs.getString("tbl_dsUrl"));
		dataSource.setDsUser(rs.getString("tbl_dsUser"));
		dataSource.setDsPasswd(rs.getString("tbl_dsPasswd"));
		dataSource.setDsEncoding(rs.getString("tbl_dsEncoding"));
		dataSource.setDsType(rs.getString("tbl_dsType"));
		dataSource.setDsStatus(rs.getString("tbl_dsStatus"));
		dataSource.setComment(rs.getString("tbl_comment"));
		dataSource.setRemark(rs.getString("tbl_remark"));
		dataSource.setCreateBy(rs.getLong("comm_createBy"));
		dataSource.setCreateDate(rs.getTimestamp("comm_createDate"));
		dataSource.setUpdateBy(rs.getLong("comm_updateBy"));
		dataSource.setUpdateDate(rs.getTimestamp("comm_updateDate"));
		dataSource.setDeleted(rs.getBoolean("comm_mark_for_delete"));
		return dataSource;
	}
	
}
