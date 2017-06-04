/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.userChiose.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.userChiose.dao.UserChioseDao;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.rowmap.SysUserRowMapper;

/**
 * @author michael
 * Created on 2012-12-25
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("userChioseDao")
public class UserChioseDaoImpl implements UserChioseDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	@Override
	@Cacheable(value = "resourceCache", key = "getUsers")
	public List<SysUser> getUsers() {
		List<SysUser> _lstResultUser = jdbcTemplate.query("select * from sys_user",new SysUserRowMapper());
		return _lstResultUser;
	}

}
