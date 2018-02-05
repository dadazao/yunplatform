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

import com.cloudstong.platform.system.model.SysPrivilege;
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
public class SysprivilegeRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		SysPrivilege sysPrivilege = new SysPrivilege();
		sysPrivilege.setId(rs.getLong("id"));
		sysPrivilege.setPrivilegeName(rs.getString("tbl_privilegename"));
		sysPrivilege.setCode(rs.getString("tbl_code"));
		sysPrivilege.setComment(rs.getString("tbl_comment"));
		sysPrivilege.setEnabled(rs.getInt("tbl_enabled"));
		sysPrivilege.setModule(rs.getLong("tbl_module"));
		sysPrivilege.setButtonId(rs.getLong("tbl_buttonid"));
		sysPrivilege.setCreateBy(rs.getLong("comm_createBy"));
		sysPrivilege.setCreateDate(rs.getTimestamp("comm_createDate"));
		sysPrivilege.setUpdateBy(rs.getLong("comm_updateBy"));
		sysPrivilege.setUpdateDate(rs.getTimestamp("comm_updateDate"));
		sysPrivilege.setOptCounter(rs.getLong("comm_opt_counter"));
		sysPrivilege.setDeleted(rs.getBoolean("comm_mark_for_delete"));
		return sysPrivilege;
	}

}
