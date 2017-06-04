/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.service;

import java.util.Set;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.message.model.ReceivedMessage;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
public interface ReceivedMessageService {

	public PageResult queryReceivedMessage(QueryCriteria queryCriteria);

	public void doSaveMessage(ReceivedMessage receivedMessage);

	public void doReadMessage(ReceivedMessage receivedMessage);

	public void doDeleteMessage(Long ids[]);

	public void doSaveMessages(Set<ReceivedMessage> receivedMessageSet);

	public ReceivedMessage findReceivedMessageById(Long id);

	public Integer countLastestUnreadMessage(SysUser user);

	public ReceivedMessage doViewReceivedMessage(Long id);
}
