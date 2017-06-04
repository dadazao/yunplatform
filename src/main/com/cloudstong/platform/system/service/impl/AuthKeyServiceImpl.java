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
import com.cloudstong.platform.system.dao.AuthKeyDao;
import com.cloudstong.platform.system.model.AuthKey;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.AuthKeyService;
import com.cloudstong.platform.system.util.SnUtil;

/**
 * @author John
 * 
 *         Created on 2014-11-25
 * 
 *         Description:
 * 
 */
@Repository("authKeyService")
public class AuthKeyServiceImpl implements AuthKeyService {
	
	@Resource
	private AuthKeyDao authKeyDao;

	@Override
	public PageResult queryAuthKey(QueryCriteria queryCriteria) {
		return authKeyDao.query("select ak.*,ac.tbl_name customername,att.tbl_productid productid,att.tbl_authtype,att.tbl_name templatename from sys_authkey ak,sys_authcustomer ac,sys_authtemplate att where ak.tbl_customerid=ac.id and ak.tbl_templateid=att.id ", queryCriteria, new AuthKeyRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthKey> queryAll() {
		return authKeyDao.getAll(new AuthKeyRowMapper());
	}

	@Override
	public String[] doSaveOrUpdate(AuthKey entity, SysUser user) {
		String[] arrays = new String[2];
		Date now = new Date(System.currentTimeMillis());
		if(entity.getId()==null||entity.getId().equals(0l)){
			Long uuid = UniqueIdUtil.genId();
			entity.setStatus(AuthKey.AUTHKEYSTATUS_UNACTIVE);
			entity.setSn(SnUtil.buildRandomSn());
			String sql = "insert into sys_authkey (id,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate," +
					"tbl_templateid,tbl_sn,tbl_customerid,tbl_status,tbl_activationdate,tbl_expirydate,tbl_reminddate,tbl_startdate,tbl_enddate,tbl_validdays,comm_opt_counter)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[]args = new Object[]{uuid,user.getId(),now,user.getId(),now,entity.getTemplateid(),entity.getSn(),entity.getCustomerid(),entity.getStatus(),entity.getActivationdate(),entity.getExpirydate(),entity.getReminddate(),entity.getStartdate(),entity.getEnddate(),entity.getValiddays(),entity.getOptCounter()};
			authKeyDao.insert(sql, args);
			arrays[0] = uuid+"";
			arrays[1] = entity.getSn();
		}else{
		}
		return arrays; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public AuthKey findAuthKeyById(Long id) {
		String sql = "select ak.*,ac.tbl_name customername,att.tbl_productid productid,att.tbl_authtype,att.tbl_name templatename from sys_authkey ak,sys_authcustomer ac,sys_authtemplate att where ak.tbl_customerid=ac.id and ak.tbl_templateid=att.id and ak.id = ? ";
		Object[]args = new Object[]{id};
		List<AuthKey> alist = authKeyDao.select(sql, args, new AuthKeyRowMapper());
		return (alist.size() > 0)?alist.get(0): null;
			
	}

	@Override
	public void doUpdateAuthKeysUnValid(Long[] selectIds, SysUser user) {
		Date now = new Date(System.currentTimeMillis());
		for (Long id : selectIds) {
			String sql = "update sys_authkey set comm_updateBy=?,comm_updateDate=?,tbl_status=? where id = ?";
			Object[]args = new Object[]{user.getId(),now,AuthKey.AUTHKEYSTATUS_UNVALID,id};
			authKeyDao.update(sql, args);
		}
	}
	
	@SuppressWarnings("rawtypes")
	class AuthKeyRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			AuthKey entity = new AuthKey();
			entity.setId(rs.getLong("id"));
			entity.setTemplateid(rs.getLong("tbl_templateid"));
			entity.setTemplatename(rs.getString("templatename"));
			entity.setSn(rs.getString("tbl_sn"));
			entity.setCustomerid(rs.getLong("tbl_customerid"));
			entity.setStatus(rs.getString("tbl_status"));
			entity.setActivationdate(rs.getTimestamp("tbl_activationdate"));
			entity.setExpirydate(rs.getTimestamp("tbl_expirydate"));
			entity.setReminddate(rs.getTimestamp("tbl_reminddate"));
			entity.setStartdate(rs.getTimestamp("tbl_startdate"));
			entity.setEnddate(rs.getTimestamp("tbl_enddate"));
			entity.setValiddays(rs.getLong("tbl_validdays"));
			entity.setCustomername(rs.getString("customername"));
			entity.setProductid(rs.getLong("productid"));
			entity.setAuthtype(rs.getString("tbl_authtype"));
			
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
