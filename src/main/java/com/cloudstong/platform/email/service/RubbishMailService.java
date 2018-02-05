/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.email.model.RubbishMail;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
public interface RubbishMailService {

	public PageResult queryRubbishMail(QueryCriteria queryCriteria);

	public RubbishMail findRubbishMailById(Long id);

	/**
	 * 永久删除
	 */
	public void doDeletePermanently(Long[] rubbishMailIDs);

	/**
	 * 恢复
	 */
	public void doResetState(Long[] rubbishMailIDs);

}
