/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.SysRoleDao;
import com.cloudstong.platform.system.model.SysResourceExtend;
import com.cloudstong.platform.system.model.SysRole;

/**
 * @author liuqi
 * 
 *         Created on 2014-8-28
 * 
 *         Description:
 * 
 */
@Repository("sysRoleDao")
public class SysRoleDaoImpl extends BaseJdbcDaoImpl<SysRole, Long> implements SysRoleDao {

	@Override
	public String getTable() {
		return "sys_role";
	}

	@Override
	public SysRole getById(Long id) {
		return super.selectById(id, new RowMapper<SysRole>() {
			@Override
			public SysRole mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysRole sr = new SysRole();
				sr.setRoleName(rs.getString("tbl_rolename"));
				return sr;
			}
		});
	}

	@Override
	public List<SysRole> getByUserId(Long lUserId) {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public void deleteMiddleTable(Long id) {
		String sql = "delete from sys_user_role where tbl_roleid = ?";
		Object[]args=new Object[]{id};
		this.jdbcTemplate.update(sql,args);
	}

	@Override
	public List<String> selectRole(String sql, Object[] objects, RowMapper<String> rowMapper) {
		return this.jdbcTemplate.query(sql,objects,rowMapper);
	}

}
