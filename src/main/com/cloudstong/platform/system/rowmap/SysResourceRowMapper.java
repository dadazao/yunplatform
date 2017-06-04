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

import com.cloudstong.platform.system.model.SysResource;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
@SuppressWarnings("rawtypes")
public class SysResourceRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		SysResource sysResource = new SysResource();
		sysResource.setId(rs.getLong("id"));
		sysResource.setResourceName(rs.getString("tbl_resourcename"));
		sysResource.setResourceUrl(rs.getString("tbl_resourceurl"));
		sysResource.setComment(rs.getString("tbl_comment"));
		sysResource.setType(rs.getString("tbl_type"));
		sysResource.setEnabled(rs.getInt("tbl_enabled"));
		sysResource.setModule(rs.getLong("tbl_module"));
		sysResource.setCreateBy(rs.getLong("comm_createBy"));
		sysResource.setCreateDate(rs.getTimestamp("comm_createDate"));
		sysResource.setUpdateBy(rs.getLong("comm_updateBy"));
		sysResource.setUpdateDate(rs.getTimestamp("comm_updateDate"));
		sysResource.setOptCounter(rs.getLong("comm_opt_counter"));
		sysResource.setDeleted(rs.getBoolean("comm_mark_for_delete"));
		return sysResource;
	}

}
