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
import com.cloudstong.platform.email.model.MailMessage;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
public interface MailMessageService {

	public MailMessage findMailMessageById(Long id);

	public List<MailMessage> getAllMailMessage();

	public PageResult queryMailMessage(QueryCriteria queryCriteria);

	public void doUpdateMailMessage(MailMessage mailMessage);

	public void doSaveMailMessage(MailMessage mailMessage);

	public void doDeleteMailMessages(Long[] ids);

	public void doDraftenMailMessage(MailMessage mailMessage);

	public void doSaveOrUpdateMailMessage(MailMessage mailMessage);

	public void doSendMailMessage(MailMessage mailMessage);

	public PageResult findMailMessage(QueryCriteria queryCriteria);

	/**
	 * Description:将邮件放入垃圾箱
	 * 
	 * @param mailMessage
	 */
	public void doPutIntoDustbin(Long[] mailMessageIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 * @return
	 */
	public MailMessage viewMailMessage(Long id);

}
