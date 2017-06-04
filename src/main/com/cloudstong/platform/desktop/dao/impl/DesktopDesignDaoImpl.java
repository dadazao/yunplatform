/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.desktop.dao.DesktopDesignDao;
import com.cloudstong.platform.desktop.model.DesktopDesign;

/**
 * @author Jason
 * 
 *         Created on 2014-9-29
 * 
 *         Description:
 * 
 */
@Repository("desktopDesignDao")
public class DesktopDesignDaoImpl extends BaseJdbcDaoImpl<DesktopDesign, Long> implements DesktopDesignDao {

	@Override
	public String getTable() {
		return "sys_desktopdesign";
	}

}
