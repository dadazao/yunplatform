/**
 * 
 */
package com.cloudstong.platform.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserOrg;
import com.cloudstong.platform.system.rowmap.SysUserRowMapper;

/**
 * @author liuqi Created on 2014-8-23
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:
 */
@Repository("sysUserDao")
public class SysUserDaoImpl extends BaseJdbcDaoImpl<SysUser, Long> implements SysUserDao {

	@Override
	public String getTable() {
		return "sys_user";
	}

	@Override
	public SysUser getById(Long id) {
		return super.selectById(id, new RowMapper<SysUser>() {
			@Override
			public SysUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysUser su = new SysUser();
				su.setUsername(rs.getString("tbl_username"));
				return su;
			}
		});
	}

	@Override
	public SysUser getByAccount(String username) {
		String sql = "select * from sys_user where tbl_username = ?";
		return (SysUser)jdbcTemplate.queryForObject(sql, new Object[]{username}, new SysUserRowMapper());
	}

	@Override
	public List getByOrgId(Long orgId) {
		return null;
	}

	@Override
	public List getByPosId(Long posId) {
		return null;
	}

	@Override
	public List getByRoleId(Long roleId) {
		return null;
	}

	@Override
	public List<SysUserOrg> getChargeByOrgId(Long orgId) {
		return null;
	}

	@Override
	public List<Long> getUserIdsByOrgId(Long orgId) {
		String sql = "select tbl_userid from sys_user_org where tbl_orgid = ? ";
		return jdbcTemplate.queryForList(sql, Long.class, orgId);
	}

	@Override
	public void deleteOrgMiddle(Long id) {
		String sql = "delete from sys_user_org where tbl_userid = ?";
		Object[]args=new Object[]{id};
		this.jdbcTemplate.update(sql,args);
	}

	@Override
	public void deletePositionMiddle(Long id) {
		String sql = "delete from sys_user_position where tbl_userid = ?";
		Object[]args=new Object[]{id};
		this.jdbcTemplate.update(sql,args);
	}

	@Override
	public void deleteRoleMiddle(Long id) {
		String sql = "delete from sys_user_role where tbl_userid = ?";
		Object[]args=new Object[]{id};
		this.jdbcTemplate.update(sql,args);
	}

	@Override
	public void changePassword(Long id, String encPassword) {
		String sql = "update sys_user set tbl_password=? where id=?";
		Object[] params = new Object[] { encPassword, id };
		jdbcTemplate.update(sql, params);
	}
	

}
