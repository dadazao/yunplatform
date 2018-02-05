/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.dao;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.email.model.MailAccount;

/**
 * @author Jason
 * 
 *         Created on 2014-9-13
 * 
 *         Description:
 * 
 */
public interface MailAccountDao extends BaseJdbcDao<MailAccount, Long> {

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 * @param userId
	 */
	void deleteCascade(Long id, Long userId);

}
