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
import com.cloudstong.platform.system.dao.AuthCustomerHadwareDao;
import com.cloudstong.platform.system.model.AuthCustomerHadware;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthCustomerHadwareService;

/**
 * @author John
 * 
 *         Created on 2014-11-22
 * 
 *         Description:
 * 
 */
@Repository("authCustomerHadwareService")
public class AuthCustomerHadwareServiceImpl implements AuthCustomerHadwareService {
	
	@Resource
	private AuthCustomerHadwareDao authCustomerHadwareDao;

	@Override
	public PageResult queryAuthCustomerHadware(QueryCriteria queryCriteria) {
		return authCustomerHadwareDao.query("select ah.* from sys_authcustomer_hadware ah where 1=1", queryCriteria, new AuthCustomerHadwareRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthCustomerHadware> queryAll() {
		return authCustomerHadwareDao.getAll(new AuthCustomerHadwareRowMapper());
	}

	@Override
	public Long doSaveOrUpdate(AuthCustomerHadware entity, SysUser user) {
		Long id =null;
		Date now = new Date(System.currentTimeMillis());
		if(entity.getId()==null||entity.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			String sql = "insert into sys_authcustomer_hadware (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_mac,tbl_computerkey,tbl_customerid,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,entity.getMac(),entity.getComputerkey(),entity.getCustomerid(),entity.getOptCounter()};
			authCustomerHadwareDao.insert(sql, args);
			id = uuid;
		}else{
			id = entity.getId();
			String sql = "update sys_authcustomer_hadware set comm_updateBy=?,comm_updateDate=?,tbl_mac=?,tbl_computerkey=?,tbl_customerid=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,entity.getMac(),entity.getComputerkey(),entity.getCustomerid(),entity.getId()};
			authCustomerHadwareDao.update(sql, args);
		}
		return id; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public AuthCustomerHadware findAuthCustomerHadwareById(Long id) {
		return (AuthCustomerHadware)authCustomerHadwareDao.selectById(id, new AuthCustomerHadwareRowMapper());
	}

	@Override
	public void doDeleteAuthCustomerHadwares(Long[] selectIds) {
		for (Long id : selectIds) {
			authCustomerHadwareDao.delete(id);
		}
	}
	
	@SuppressWarnings("rawtypes")
	class AuthCustomerHadwareRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AuthCustomerHadware entity = new AuthCustomerHadware();
			entity.setId(rs.getLong("id"));
			entity.setMac(rs.getString("tbl_mac"));
			entity.setComputerkey(rs.getString("tbl_computerkey"));
			entity.setCustomerid(rs.getLong("tbl_customerid"));
			
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
