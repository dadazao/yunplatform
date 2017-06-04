/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.system.model.SysPrivilege;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-9
 * 
 *         Description:
 * 
 */
public interface SysPrivilegeDao extends BaseJdbcDao<SysPrivilege, Long> {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	void deleteAuthResourceData(Long id);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @return
	 */
	Long addModuleAuth(Catalog _catalog,SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @return
	 */
	Long addModuleResource(Catalog _catalog,SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param authId
	 * @param resourceId
	 */
	void addModuleRelation(Long authId, Long resourceId);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _sysPrivilege
	 * @return
	 */
	void deleteModuleAuth(SysPrivilege _sysPrivilege);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _sysPrivilege
	 * @return
	 */
	void deleteModuleResource(SysPrivilege _sysPrivilege);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _sysPrivilege
	 */
	void deleteModuleRelation(SysPrivilege _sysPrivilege);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @param _tabulationButton
	 * @param user
	 * @return
	 */
	Long addTabulationButtonAuth(Catalog _catalog, TabulationButton _tabulationButton, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @param _tabulationButton
	 * @param user
	 * @return
	 */
	Long addTabulationButtonResource(Catalog _catalog, TabulationButton _tabulationButton, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @param _formButton
	 * @param user
	 * @return
	 */
	Long addFormButtonAuth(Catalog _catalog, FormButton _formButton, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @param _formButton
	 * @param user
	 * @return
	 */
	Long addFormButtonResource(Catalog _catalog, FormButton _formButton, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @param _tabulationOpt
	 * @param user
	 * @return
	 */
	Long addTabulationOptAuth(Catalog _catalog, TabulationOpt _tabulationOpt, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param _catalog
	 * @param _tabulationOpt
	 * @param user
	 * @return
	 */
	Long addTabulationOptResource(Catalog _catalog, TabulationOpt _tabulationOpt, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	void deletePrivilegeResource(Long id);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	void deletePrivilegeRelation(Long id);
	
	public void deletePrivilegeRole(Long id);
	
	public Long findCatalogIdByPrivileId(Long id);

}
