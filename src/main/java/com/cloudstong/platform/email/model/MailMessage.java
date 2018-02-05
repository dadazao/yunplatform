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
 *         Created on 2014-9-16
 * 
 *         Description: 邮件类
 * 
 */
public class MailMessage extends EntityBase implements IEmail {

	private static final long serialVersionUID = -3045121734849633574L;

	private Long id;

	private String to;

	private String from;

	private String cc; // Carbon Copy

	private String subject;

	private String content;
	/**
	 * 关联附件id，多个以“,”分隔
	 */
	private String attachments;

	private Long userid;

	private int draft; // 是否草稿

	private Date date; // 时间

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
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

	@Override
	public boolean isMulti() {
		throw new UnsupportedOperationException("mailMessage类不允许该操作");
	}

	public int getDraft() {
		return draft;
	}

	public void setDraft(int draft) {
		this.draft = draft;
	}

	@Override
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
