/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.system.model.SysPrivilege;
import com.cloudstong.platform.system.model.SysResource;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
public interface SysPrivilegeService {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryPrivilegeResource(Long privilegeId,QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysResource
	 * @param user
	 * @return
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	Long doSaveResource(SysResource sysResource, Long privilegeId, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysPrivilege
	 * @param user
	 * @return
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	Long doSavePrivilege(SysPrivilege sysPrivilege, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryPrivilege(QueryCriteria queryCriteria);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param privilegeId
	 * @return
	 */
	SysPrivilege findPrivilegeById(Long privilegeId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doDeletePrivileges(Long[] selectedIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doEnablePrivileges(Long[] selectedIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doDisablePrivileges(Long[] selectedIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param resourceId
	 * @return
	 */
	SysResource findResourceById(Long resourceId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doDeleteResources(Long[] selectedSubIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedSubIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doEnableResources(Long[] selectedSubIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param selectedSubIDs
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doDisableResources(Long[] selectedSubIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param module
	 * @return
	 */
	List<SysPrivilege> selectCatalogAuthList(String module);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysPrivilege> selectHasAuthList(Long roleId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param status
	 * @param _catalog
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doAddModuleAuth(String status, Catalog _catalog, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param status
	 * @param _catalog
	 * @param _tabulationButton
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doAddTabulationButtonAuth(String status, Catalog _catalog, TabulationButton _tabulationButton, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param status
	 * @param _catalog
	 * @param _formButton
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doAddFormButtonAuth(String status, Catalog _catalog, FormButton _formButton, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param module
	 * @return
	 */
	List<SysPrivilege> findAllPrivilegeByModule(Long module);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param status
	 * @param _catalog
	 * @param _tabulationOpt
	 * @param user
	 */
	@CacheEvict(value = "privilegeCache", allEntries = true, beforeInvocation = true)
	void doAddTabulationOptAuth(String status, Catalog _catalog, TabulationOpt _tabulationOpt, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param modules
	 * @return
	 */
	List<SysPrivilege> selectCatalogAuthListByModules(String modules);

}
