/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.Codingrule;
import com.cloudstong.platform.system.model.SysUser;

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
public interface CodingruleService {
	
	public PageResult queryCodingrule(QueryCriteria queryCriteria);

	public List<Codingrule> getAllCodingrule();

	public Long doSaveCodingrule(Codingrule codingrule,SysUser sysUser);

	public Codingrule findCodingruleById(Long codingruleId);

	public void doDeleteCodingrule(Long codingruleId);

	public void doDeleteCodingrules(Long[] codingruleIds);
	

}
