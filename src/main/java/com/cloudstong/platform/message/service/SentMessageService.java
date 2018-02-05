/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.service;

import java.util.List;
import java.util.Map;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.message.model.SentMessage;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
public interface SentMessageService {

	public PageResult querySentMessage(QueryCriteria queryCriteria);

	/**
	 * 
	 * Description:发送内部消息，返回收件人id列表
	 * 
	 * @param sentMessage
	 * @return
	 */
	public List<Long> doSendMessage(SentMessage sentMessage);

	public SentMessage findSentMessageById(Long id);

	/**
	 * Description:根据发送消息id获取已读人员列表
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getReaderList(Long id);

	/**
	 * Description:根据发送消息id获取已回复消息列表
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getReplyedMessage(Long id, SysUser sysUser);

	public void doDeleteSentMessage(Long[] sentMessageIDs);
}
