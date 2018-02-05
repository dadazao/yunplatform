/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.dao.AuthTemplateDao;
import com.cloudstong.platform.system.model.AuthTemplate;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthTemplateService;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
@Repository("authTemplateService")
public class AuthTemplateServiceImpl implements AuthTemplateService {
	
	@Resource
	public AuthTemplateDao authTemplateDao;
	
	
	@Override
	public PageResult queryAuthTemplate(QueryCriteria queryCriteria) {
		return authTemplateDao.query("select at.*,au.tbl_name productname from sys_authtemplate at inner join sys_authproduct au where at.tbl_productid=au.id", queryCriteria, new AuthTemplateRowMapper());
	}

	@Override
	public Long doSaveOrUpdate(AuthTemplate entity, SysUser user) {
		Long id =null;
		Date now = new Date(System.currentTimeMillis());
		if(entity.getId()==null||entity.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_authtemplate (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_name,tbl_productid,tbl_authtype,tbl_startdate,tbl_enddate,tbl_validdays,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,entity.getName(),entity.getProductid(),entity.getAuthtype(),entity.getStartdate(),entity.getEnddate(),entity.getValiddays(),entity.getOptCounter()};
			authTemplateDao.insert(sql, args);
			id = uuid;
		}else{
			id = entity.getId();
			String sql = "update sys_authtemplate set comm_updateBy=?,comm_updateDate=?,tbl_name=?,tbl_productid=?,tbl_authtype=?,tbl_startdate=?,tbl_enddate=?,tbl_validdays=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,entity.getName(),entity.getProductid(),entity.getAuthtype(),entity.getStartdate(),entity.getEnddate(),entity.getValiddays(),entity.getId()};
			authTemplateDao.update(sql, args);
		}
		return id; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public AuthTemplate findAuthTemplateById(Long id) {
		return (AuthTemplate)authTemplateDao.selectById(id, new AuthTemplateOneRowMapper());
	}

	@Override
	public void doDeleteAuthTemplates(Long[] selectIds) {
		for (Long id : selectIds) {
			authTemplateDao.delete(id);
		}
	}
	
	@SuppressWarnings("rawtypes")
	class AuthTemplateRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AuthTemplate entity = new AuthTemplate();
			entity.setId(rs.getLong("id"));
			entity.setName(rs.getString("tbl_name"));
			entity.setProductid(rs.getLong("tbl_productid"));
			entity.setProductname(rs.getString("productname"));
			entity.setStartdate(rs.getTimestamp("tbl_startdate"));
			entity.setEnddate(rs.getTimestamp("tbl_enddate"));
			entity.setAuthtype(rs.getString("tbl_authtype"));
			entity.setValiddays(rs.getLong("tbl_validdays"));
			
			entity.setCreateBy(rs.getLong("comm_createBy"));
			entity.setCreateDate(rs.getTimestamp("comm_createDate"));
			entity.setUpdateBy(rs.getLong("comm_updateBy"));
			entity.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			entity.setOptCounter(rs.getLong("comm_opt_counter"));
			entity.setDeleted(rs.getBoolean("comm_mark_for_delete"));
			return entity;
		}
	}
	
	@SuppressWarnings("rawtypes")
	class AuthTemplateOneRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AuthTemplate entity = new AuthTemplate();
			entity.setId(rs.getLong("id"));
			entity.setName(rs.getString("tbl_name"));
			entity.setProductid(rs.getLong("tbl_productid"));
			entity.setStartdate(rs.getTimestamp("tbl_startdate"));
			entity.setEnddate(rs.getTimestamp("tbl_enddate"));
			entity.setAuthtype(rs.getString("tbl_authtype"));
			entity.setValiddays(rs.getLong("tbl_validdays"));
			
			entity.setCreateBy(rs.getLong("comm_createBy"));
			entity.setCreateDate(rs.getTimestamp("comm_createDate"));
			entity.setUpdateBy(rs.getLong("comm_updateBy"));
			entity.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			entity.setOptCounter(rs.getLong("comm_opt_counter"));
			entity.setDeleted(rs.getBoolean("comm_mark_for_delete"));
			return entity;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthTemplate> findAuthTemplateByPId(Long id) {
		String sql = "select at.* from sys_authtemplate at where at.tbl_productid=? ";
		Object[]args = new Object[]{id};
		return authTemplateDao.select(sql, args, new AuthTemplateOneRowMapper());
	}

}
