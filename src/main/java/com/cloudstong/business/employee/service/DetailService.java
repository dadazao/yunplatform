/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.employee.service;

import java.util.List;

import com.cloudstong.business.employee.model.Detail;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan
 * Created on 2014-10-10 11:35:19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public interface DetailService {
	
	public PageResult queryDetail(QueryCriteria queryCriteria);

	public List<Detail> getAllDetail();

	public void doSaveDetail(Detail detail);

	public void doUpdateDetail(Detail detail);

	public Detail findDetailById(Long detailId);

	public void doDeleteDetail(Long detailId);

	public void doDeleteDetails(Long[] detailIds);
	public Detail findDetailByEmployeeId(Long employeeId);
}
