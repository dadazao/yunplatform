package com.cloudstong.platform.core.model;

import java.io.Serializable;
import java.util.Date;

public class SmsMobile implements Serializable {
	public static final Short STATUS_SENDED = Short.valueOf((short) 1);

	public static final Short STATUS_NOT_SENDED = Short.valueOf((short) 0);
	protected Long smsId;
	protected Date sendTime;
	protected String recipients;
	protected String phoneNumber;
	protected Long userId;
	protected String userName;
	protected String smsContent;
	protected Short status;
	protected String fromUser;

	public SmsMobile() {
	}

	public SmsMobile(Long in_smsId) {
		setSmsId(in_smsId);
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long aValue) {
		smsId = aValue;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date aValue) {
		sendTime = aValue;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String aValue) {
		recipients = aValue;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String aValue) {
		phoneNumber = aValue;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long aValue) {
		userId = aValue;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String aValue) {
		userName = aValue;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String aValue) {
		smsContent = aValue;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short aValue) {
		status = aValue;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
}