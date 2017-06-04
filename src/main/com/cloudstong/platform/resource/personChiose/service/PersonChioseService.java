package com.cloudstong.platform.resource.personChiose.service;

import java.util.List;

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
 * Description:人员选择构件服务接口
 */
public interface PersonChioseService {
	/**
	 * Description:获得机构列表
	 * @return 机构列表
	 */
	List<Org> getOrgs();

	/**
	 * Description:获取人员列表
	 * @return 人员列表
	 */
	List<Person> getPersons();
	
	/**
	 * Description:获取用户列表
	 * @return 用户列表
	 */
	public List<SysUser> getUsers();
}
