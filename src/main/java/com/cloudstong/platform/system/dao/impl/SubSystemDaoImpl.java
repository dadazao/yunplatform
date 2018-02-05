/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.SubSystemDao;
import com.cloudstong.platform.system.model.SubSystem;

/**
 * @author Allan
 * 
 *         Created on 2014-10-15
 * 
 *         Description:
 * 
 */
@Repository("subSystemDao")
public class SubSystemDaoImpl extends BaseJdbcDaoImpl<SubSystem, Long> implements SubSystemDao {

	@Override
	public String getTable() {
		return "sys_subsystem";
	}

}
