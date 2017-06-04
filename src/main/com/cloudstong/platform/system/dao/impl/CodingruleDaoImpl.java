/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseJdbcDaoImpl;
import com.cloudstong.platform.system.dao.CodingruleDao;
import com.cloudstong.platform.system.model.Codingrule;

/**
 * @author Allan
 * Created on 2014-9-29 14:58:55
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("codingruleDao")
public class CodingruleDaoImpl extends BaseJdbcDaoImpl<Codingrule, Long> implements CodingruleDao  {

	@Override
	public String getTable() {
		return "sys_codingrule";
	}


}
