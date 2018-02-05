package com.cloudstong.platform.resource.personChiose.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

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
 * Description:人员选择构件操作数据库接口
 */
public interface PersonChioseDao {
	/**
	 * Description:获得机构列表
	 * @return 机构列表
	 */
	List<Org> getOrgs();

	/**
	 * Description:获取人员列表
	 * @return 人员列表
	 */
	@Cacheable(value = "resourceCache", key = "'getPersons'")
	List<Person> getPersons();
	
	/**
	 * Description:获取用户列表
	 * @return 用户列表
	 */
	public List<SysUser> getUsers();
}
