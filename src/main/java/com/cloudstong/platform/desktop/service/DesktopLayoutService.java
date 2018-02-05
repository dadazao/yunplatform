/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.desktop.model.DesktopLayout;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
public interface DesktopLayoutService {

	public PageResult queryDesktopLayout(QueryCriteria queryCriteria);

	public DesktopLayout findDesktopLayoutById(Long id);

	public void doSaveDesktopLayout(DesktopLayout desktopLayout);

	public void doSaveOrUpdateDesktopLayout(DesktopLayout desktopLayout);

	public void doUpdateDesktopLayout(DesktopLayout desktopLayout);

	public void doDeleteDesktopLayouts(Long ids[]);

	public void doDeleteDesktopLayout(Long id);

	public void doSetDefaultLayout(Long id);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	public List<DesktopLayout> findAllDesktopLayouts();

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param sysUser
	 * @param id
	 * @param columns
	 */
	public void doSaveDesktopDesign(SysUser sysUser, Long id, String columns);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 * @param items
	 */
	public void doSaveOrUpdateLayoutDesign(Long id, String items);

}
