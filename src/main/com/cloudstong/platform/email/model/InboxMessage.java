/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.model;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Jason
 * 
 *         Created on 2014-9-18
 * 
 *         Description: 邮件中 收到的邮件
 * 
 */
public class InboxMessage extends EntityBase implements IEmail {
	private final static String excludedFields[] = new String[] { "id", "read", "optCounter", "createBy", "createUser", "createDate", "updateBy",
			"updateDate", "isDeleted" };

	private static final long serialVersionUID = 1774279796422078406L;

	private Long id;

	private String to;

	private String from;

	private String cc; // Carbon Copy

	private String subject;

	private String content;

	private String attachments;

	private Long userid;

	private Date date; // 时间

	private boolean read;// 是否已读

	private String mimeMessageId; // 邮件id

	private String useremail;// 收件email账号

	@Override
	public String getTo() {
		return to;
	}

	@Override
	public String getCc() {
		return cc;
	}

	@Override
	public String getFrom() {
		return from;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public boolean isMulti() {
		throw new UnsupportedOperationException("inboxMessage 类不允许该操作");
	}

	@Override
	public String getAttachments() {
		return attachments;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, excludedFields);
	}

	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public String getMimeMessageId() {
		return mimeMessageId;
	}

	public void setMimeMessageId(String mimeMessageId) {
		this.mimeMessageId = mimeMessageId;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

}
