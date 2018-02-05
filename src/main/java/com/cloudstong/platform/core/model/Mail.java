package com.cloudstong.platform.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Mail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subject;
	private String from;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private Date sendDate;
	private String content;
	private String mailTemplate;
	private Map mailData = null;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public Map getMailData() {
		return mailData;
	}

	public void setMailData(Map mailData) {
		this.mailData = mailData;
	}
}