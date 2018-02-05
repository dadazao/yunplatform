/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.SysResourceExtend;
import com.cloudstong.platform.system.model.SysRole;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * 
 *         Created on 2014-8-28
 * 
 *         Description:
 * 
 */
public interface SysRoleService {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	@Cacheable(value = "privilegeCache")
	PageResult queryRole(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param roleId
	 * @return
	 */
	@Cacheable(value = "privilegeCache")
	SysRole findRoleById(Long roleId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doDeleteRoles(Long[] selectedIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysRole
	 * @param user
	 * @return
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	Long doSaveRole(SysRole sysRole, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	@Cacheable(value = "privilegeCache")
	PageResult queryUserRole(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param userIds
	 * @param roleId
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doAddUser(String userIds, Long roleId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedSubIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doDeleteUser(Long[] selectedSubIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysRole
	 * @param user
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doCopyRole(SysRole sysRole, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	@Cacheable(value = "privilegeCache")
	List<SysRole> selectAllRole();

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param authIds
	 * @param roleId
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doAddAuth(String authIds, Long roleId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 * @return
	 */
	List findNotCanDeleteRole(Long[] selectedIDs);
	
	@Cacheable(value = "privilegeCache")
	List<SysRole> getRoleBySystemId(Long systemId);

	@Cacheable(value = "privilegeCache")
	List<String> getRolesByUserId(Long userId);

}
