/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service;

import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.email.model.InboxMessage;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
public interface InboxMessageService {

	public PageResult findInboxMessage(QueryCriteria queryCriteria);

	/**
	 * Description:批量插入收到的新邮件
	 * 
	 * @param messageList
	 * @param user
	 */
	public void doSaveInboxMessage(List<InboxMessage> messageList, SysUser user);

	public void doSaveInboxMessage(InboxMessage message);

	public void doPutIntoDustbin(Long id);

	public void doPutIntoDustbin(Long[] ids);

	public InboxMessage findInboxMessageById(Long id);

	public InboxMessage doViewInboxMessage(Long id);

}
