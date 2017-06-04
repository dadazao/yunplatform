/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.system.model.SysOrgType;
import com.cloudstong.platform.system.service.SysOrgTypeService;

/**
 * @author Allan
 * 
 *         Created on 2014-10-14
 * 
 *         Description:
 * 
 */
@Repository("orgTypeService")
public class SysOrgTypeServiceImpl implements SysOrgTypeService {

	@Override
	public SysOrgType getById(Long orgType) {
		return null;
	}

}
