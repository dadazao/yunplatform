/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Jason
 * 
 *         Created on 2014-9-18
 * 
 *         Description: 删除的邮件
 * 
 */
public class RubbishMail extends EntityBase {

	private static final long serialVersionUID = -2889312372443285076L;
	private Long id;

	private int type;// 类型:收件1、发件2(草稿属于发件)

	private String to;

	private String from;

	private String cc; // Carbon Copy

	private String subject;

	private String content;

	private String attachments;

	private Long userid;

	private Date date; // 时间

	private int draft = 0; // 是否草稿

	private boolean read;// 是否已读

	private String mimeMessageId; // 邮件id

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getMimeMessageId() {
		return mimeMessageId;
	}

	public void setMimeMessageId(String mimeMessageId) {
		this.mimeMessageId = mimeMessageId;
	}

	public int getDraft() {
		return draft;
	}

	public void setDraft(int draft) {
		this.draft = draft;
	}

}
