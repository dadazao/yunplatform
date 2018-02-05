package com.cloudstong.platform.resource.personChiose.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.personChiose.dao.PersonChioseDao;
import com.cloudstong.platform.resource.personChiose.service.PersonChioseService;
import com.cloudstong.platform.system.model.Org;
import com.cloudstong.platform.system.model.Person;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:人员选择构件服务接口实现类
 */
@Repository("personChioseService")
public class PersonChioseServiceImpl implements PersonChioseService {

	@Resource
	private PersonChioseDao personChioseDao;

	@Override
	public List<Org> getOrgs() {
		// TODO Auto-generated method stub
		return personChioseDao.getOrgs();
	}

	@Override
	public List<Person> getPersons() {
		// TODO Auto-generated method stub
		return personChioseDao.getPersons();
	}

	@Override
	public List<SysUser> getUsers(){
		return personChioseDao.getUsers();
	}
	
	public PersonChioseDao getPersonChioseDao() {
		return personChioseDao;
	}

	public void setPersonChioseDao(PersonChioseDao personChioseDao) {
		this.personChioseDao = personChioseDao;
	}
}
