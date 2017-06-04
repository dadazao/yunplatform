/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.service;

import java.util.List;

import com.cloudstong.platform.email.model.MailAttachment;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
public interface MailAttachmentService {

	public MailAttachment findMailAttachmentById(Long id);

	public void doSaveMailAttachments(List<MailAttachment> mailAttachments);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 * @return
	 */
	public List<MailAttachment> findMailAttachmentByMailId(Long id);

}
