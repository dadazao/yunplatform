/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.service;

import com.cloudstong.platform.desktop.model.DesktopDesign;

/**
 * @author Jason
 * 
 *         Created on 2014-9-29
 * 
 *         Description:
 * 
 */
public interface DesktopDesignService {

	public void doSaveOrUpdate(DesktopDesign desktopDesign);

	public DesktopDesign findDefault();

	public DesktopDesign findByUserId(Long userId);

	public void doSave(DesktopDesign desktopDesign);

	public void doUpdate(DesktopDesign desktopDesign);

	public DesktopDesign findById(Long id);

	/**
	 * Description:根据当前用户加载桌面，如果用户没有自定义桌面则加载系统默认桌面
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	public DesktopDesign loadDesktop(Long id);

}
