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

import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Allan
 * 
 *         Created on 2014-10-14
 * 
 *         Description:
 * 
 */
public class SysUserRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		SysUser sysUser = new SysUser();
		sysUser.setId(rs.getLong("id"));
		sysUser.setUsername(rs.getString("tbl_username"));
		sysUser.setPassword(rs.getString("tbl_password"));
		sysUser.setFullname(rs.getString("tbl_yonghuxingming"));
		sysUser.setMobile(rs.getString("tbl_mobile"));
		sysUser.setEmail(rs.getString("tbl_email"));
		sysUser.setPhone(rs.getString("tbl_phone"));
		sysUser.setSex(rs.getString("tbl_sex"));
		sysUser.setActive(rs.getInt("tbl_jihuo"));
		sysUser.setLock(rs.getInt("tbl_suoding"));
		sysUser.setOverdue(rs.getInt("tbl_guoqi"));
		return sysUser;
	}

}
