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
 *         Created on 2014-9-13
 * 
 *         Description: 邮箱账号
 * 
 */
public class MailAccount extends EntityBase {

	private static final long serialVersionUID = -4013284333436224473L;

	private Long id;

	private String name;

	private String mailType;

	private String address;

	private String password;

	private String smtpHost;

	private int smtpPort = 25;

	private String popHost;

	private int popPort = 110;

	private int deflt;// 是否默认

	private Long userId;

	/**
	 * 上一次收取邮件的时间
	 */
	private Date lastReceiveDate = null;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getAddress() {
		return address == null ? null : address.trim();
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHost() {
		return smtpHost == null ? null : smtpHost.trim();
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getPopHost() {
		return popHost == null ? null : popHost.trim();
	}

	public void setPopHost(String popHost) {
		this.popHost = popHost;
	}

	public int getPopPort() {
		return popPort;
	}

	public void setPopPort(int popPort) {
		this.popPort = popPort;
	}

	public int getDeflt() {
		return deflt;
	}

	public void setDeflt(int deflt) {
		this.deflt = deflt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		String address = getAddress();
		if (address == null) {
			return null;
		}
		return address.substring(address.lastIndexOf("@"));
	}

	public Date getLastReceiveDate() {
		return lastReceiveDate;
	}

	public void setLastReceiveDate(Date lastReceiveDate) {
		this.lastReceiveDate = lastReceiveDate;
	}

}
