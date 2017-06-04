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
import com.cloudstong.platform.system.dao.AuthCustomerDao;
import com.cloudstong.platform.system.model.AuthCustomer;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthCustomerService;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
@Repository("authCustomerService")
public class AuthCustomerServiceImpl implements AuthCustomerService {

	@Resource
	private AuthCustomerDao authCustomerDao;
	
	@Override
	public PageResult queryAuthCustomer(QueryCriteria queryCriteria) {
		return authCustomerDao.query("select ac.* from sys_authcustomer ac where 1=1", queryCriteria, new AuthCustomerRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthCustomer> queryAll() {
		return authCustomerDao.getAll(new AuthCustomerRowMapper());
	}

	@Override
	public Long doSaveOrUpdate(AuthCustomer entity, SysUser user) {
		Long id =null;
		Date now = new Date(System.currentTimeMillis());
		if(entity.getId()==null||entity.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_authcustomer (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_name,tbl_linkman,tbl_mobile,tbl_email,tbl_tel,tbl_address,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,entity.getName(),entity.getLinkman(),entity.getMobile(),entity.getEmail(),entity.getTel(),entity.getAddress(),entity.getOptCounter()};
			authCustomerDao.insert(sql, args);
			id = uuid;
		}else{
			id = entity.getId();
			String sql = "update sys_authcustomer set comm_updateBy=?,comm_updateDate=?,tbl_name=?,tbl_linkman=?,tbl_mobile=?,tbl_email=?,tbl_tel=?,tbl_address=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,entity.getName(),entity.getLinkman(),entity.getMobile(),entity.getEmail(),entity.getTel(),entity.getAddress(),entity.getId()};
			authCustomerDao.update(sql, args);
		}
		return id; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public AuthCustomer findAuthCustomerById(Long id) {
		return (AuthCustomer)authCustomerDao.selectById(id, new AuthCustomerRowMapper());
	}

	@Override
	public void doDeleteAuthCustomers(Long[] selectIds) {
		for (Long id : selectIds) {
			authCustomerDao.delete(id);
		}
	}

	@SuppressWarnings("rawtypes")
	class AuthCustomerRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AuthCustomer entity = new AuthCustomer();
			entity.setId(rs.getLong("id"));
			entity.setName(rs.getString("tbl_name"));
			entity.setLinkman(rs.getString("tbl_linkman"));
			entity.setMobile(rs.getString("tbl_mobile"));
			entity.setEmail(rs.getString("tbl_email"));
			entity.setTel(rs.getString("tbl_tel"));
			entity.setAddress(rs.getString("tbl_address"));
			
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
