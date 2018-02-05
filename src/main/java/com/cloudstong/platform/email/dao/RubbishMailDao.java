/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.email.model.RubbishMail;

/**
 * @author Jason
 * 
 *         Created on 2014-9-18
 * 
 *         Description:
 * 
 */
public interface RubbishMailDao extends BaseJdbcDao<RubbishMail, Long> {
	public int getRubbishMailTypeById(Long id);
}
