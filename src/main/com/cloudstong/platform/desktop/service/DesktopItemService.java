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
import com.cloudstong.platform.desktop.model.DesktopItem;

/**
 * @author Jason
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
public interface DesktopItemService {

	public PageResult queryDesktopItem(QueryCriteria queryCriteria);

	public DesktopItem findDesktopItemById(Long id);

	public void doDeleteDesktopItems(Long[] ids);

	public void doSaveDesktopItem(DesktopItem desktopItem);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param desktopItem
	 */
	public void doUpdateDesktopItem(DesktopItem desktopItem);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	public List<DesktopItem> findAllDesktopItems();
	
}
