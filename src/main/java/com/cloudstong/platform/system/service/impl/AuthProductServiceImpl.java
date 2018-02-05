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
import com.cloudstong.platform.system.dao.AuthProductDao;
import com.cloudstong.platform.system.model.AuthProduct;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthProductService;

/**
 * @author John
 * 
 *         Created on 2014-11-21
 * 
 *         Description:
 * 
 */
@Repository("authProductService")
public class AuthProductServiceImpl implements AuthProductService {
	
	@Resource
	private AuthProductDao authProductDao;

	@Override
	public PageResult queryAuthProduct(QueryCriteria queryCriteria) {
		return authProductDao.query("select au.* from sys_authproduct au where 1=1", queryCriteria, new AuthProductRowMapper());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AuthProduct> queryAll() {
		return authProductDao.getAll(new AuthProductRowMapper());
	}

	@Override
	public Long doSaveOrUpdate(AuthProduct entity,SysUser user) {
		Long id =null;
		Date now = new Date(System.currentTimeMillis());
		if(entity.getId()==null||entity.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_authproduct (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_name,tbl_version,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,entity.getName(),entity.getVersion(),entity.getOptCounter()};
			authProductDao.insert(sql, args);
			id = uuid;
		}else{
			id = entity.getId();
			String sql = "update sys_authproduct set comm_updateBy=?,comm_updateDate=?,tbl_name=?,tbl_version=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,entity.getName(),entity.getVersion(),entity.getId()};
			authProductDao.update(sql, args);
		}
		return id; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AuthProduct findAuthProductById(Long id) {
		return (AuthProduct)authProductDao.selectById(id, new AuthProductRowMapper());
	}

	@Override
	public void doDeleteAuthProducts(Long[] selectIds) {
		for (Long id : selectIds) {
			authProductDao.delete(id);
		}
	}

	
	@SuppressWarnings("rawtypes")
	class AuthProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AuthProduct entity = new AuthProduct();
			entity.setId(rs.getLong("id"));
			entity.setName(rs.getString("tbl_name"));
			entity.setVersion(rs.getString("tbl_version"));
			
			entity.setCreateBy(rs.getLong("comm_createBy"));
			entity.setCreateDate(rs.getTimestamp("comm_createDate"));
			entity.setUpdateBy(rs.getLong("comm_updateBy"));
			entity.setUpdateDate(rs.getTimestamp("comm_updateDate"));
			entity.setOptCounter(rs.getLong("comm_opt_counter"));
			entity.setDeleted(rs.getBoolean("comm_mark_for_delete"));
			return entity;
		}
	}

}
