/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.dao;

import java.util.List;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.desktop.model.DesktopItem;

/**
 * @author Jason
 * 
 *         Created on 2014-9-27
 * 
 *         Description:
 * 
 */
public interface DesktopItemDao extends BaseJdbcDao<DesktopItem, Long> {

	public List<DesktopItem> getAllByOrder(String orderField, String Direction);

}
