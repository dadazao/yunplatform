/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:邮件附件
 * 
 */
public class MailAttachment extends EntityBase {

	private static final long serialVersionUID = 7297605837768689209L;

	private Long id;

	private String name;

	private String path;

	private Long mailId;// 邮件 id

	/**
	 * Description:Constructor for MailAttachment class.
	 * 
	 * @param id
	 * @param name
	 * @param path
	 */
	public MailAttachment(String name, String path) {
		this(name, path, null);
	}

	/**
	 * Description:Constructor for MailAttachment class.
	 * 
	 * @param id
	 * @param name
	 * @param path
	 * @param mailId
	 */
	public MailAttachment(String name, String path, Long mailId) {
		super();
		this.name = name;
		this.path = path;
		this.mailId = mailId;
	}

	public MailAttachment() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getMailId() {
		return mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

}
