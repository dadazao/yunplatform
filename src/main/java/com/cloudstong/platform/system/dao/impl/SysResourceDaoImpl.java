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
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.system.dao.SysResourceDao;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysResourceExtend;

/**
 * @author Allan
 * 
 *         Created on 2014-10-22
 * 
 *         Description:
 * 
 */
@Repository("sysResourceDao")
public class SysResourceDaoImpl extends BaseJdbcDaoImpl<SysResource, Long> implements SysResourceDao {
	@Override
	public String getTable() {
		return "sys_resource";
	}
	
	@Override
	public List<SysResourceExtend> selectSysResourceExtend(String sql, Object object, RowMapper<SysResourceExtend> rowMapper) {
		return this.jdbcTemplate.query(sql,rowMapper);
	}

	@Override
	public List<String> selectRole(String sql, Object[] objects, RowMapper<String> rowMapper) {
		return this.jdbcTemplate.query(sql,objects,rowMapper);
	}

	public List<Catalog> getSuperResource(Long systemId) {
		String sql = "select * from sys_catalog where tbl_ishide=0 order by tbl_ordernum asc";
		List<Catalog> sres = jdbcTemplate.query(sql, new RowMapper<Catalog>(){
			@Override
			public Catalog mapRow(ResultSet rs, int rowNum) throws SQLException {
				Catalog sre = new Catalog();
				sre.setName(rs.getString("tbl_name"));
				sre.setPath(rs.getString("tbl_path"));
				sre.setId(rs.getLong("id"));
				sre.setParentId(rs.getLong("tbl_parentId"));
				return sre;
			}
		});
		return sres;
	}

	@Override
	public List<Catalog> getNormResourceByRole(Long systemId, String roles) {
		String sql = "select DISTINCT c.* from sys_catalog c  left join sys_privilege p on p.tbl_module=c.id left join sys_role_privilege rp on rp.tbl_privilegeid=p.id where c.tbl_isauth=0 or rp.tbl_roleid in (select r.id from sys_role r where tbl_bieming in ("+roles+") and r.tbl_shifouqiyong=1) order by c.tbl_orderNum asc";
		List<Catalog> sres = jdbcTemplate.query(sql, new RowMapper<Catalog>(){
			@Override
			public Catalog mapRow(ResultSet rs, int rowNum) throws SQLException {
				Catalog sre = new Catalog();
				sre.setName(rs.getString("tbl_name"));
				sre.setPath(rs.getString("tbl_path"));
				sre.setId(rs.getLong("id"));
				sre.setParentId(rs.getLong("tbl_parentId"));
				return sre;
			}
		});
		return sres;
	}
	

}
