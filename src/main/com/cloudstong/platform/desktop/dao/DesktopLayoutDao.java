/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.dao;

import java.util.List;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.desktop.model.DesktopLayout;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
public interface DesktopLayoutDao extends BaseJdbcDao<DesktopLayout, Long> {

	public List<DesktopLayout> getAllOrderBy(String orderField, String direction);
}
