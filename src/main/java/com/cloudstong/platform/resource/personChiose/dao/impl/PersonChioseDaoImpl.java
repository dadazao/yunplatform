package com.cloudstong.platform.resource.personChiose.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.resource.personChiose.dao.PersonChioseDao;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.rowmap.SysUserRowMapper;

/**
 * @author michael Created on 2012-11-16
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:人员选择构件操作数据库接口实现类
 */
@Repository("personChioseDao")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PersonChioseDaoImpl implements PersonChioseDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Override
	public List<Org> getOrgs() {
		List<Org> _lstResultOrj = jdbcTemplate.query("select * from sys_org ", new BeanPropertyRowMapper(Org.class));
		return _lstResultOrj;
	}

	@Override
	@Cacheable(value = "resourceCache", key = "'getPersons'")
	public List<Person> getPersons() {
		List<Person> _lstResultPerson = jdbcTemplate.query("select * from sys_renyuan", new BeanPropertyRowMapper(Person.class));
		return _lstResultPerson;
	}

	@Override
	public List<SysUser> getUsers() {
		List<SysUser> _lstResultUser = jdbcTemplate.query("select * from sys_user", new SysUserRowMapper());
		return _lstResultUser;
	}
}
