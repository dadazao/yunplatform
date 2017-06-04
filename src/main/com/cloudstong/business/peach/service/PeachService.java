/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.peach.service;

import java.util.List;

import com.cloudstong.business.peach.model.Peach;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan
 * Created on 2015-6-11 12:28:44
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public interface PeachService {
	
	public PageResult queryPeach(QueryCriteria queryCriteria);

	public List<Peach> getAllPeach();

	public void doSavePeach(Peach peach);

	public void doUpdatePeach(Peach peach);

	public Peach findPeachById(Long peachId);

	public void doDeletePeach(Long peachId);

	public void doDeletePeachs(Long[] peachIds);
}
