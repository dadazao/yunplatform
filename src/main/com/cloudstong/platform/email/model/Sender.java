/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.model;

/**
 * @author Jason
 * Created on 2012-11-26
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 发件人类，添加此类的目的是为了以后如果邮件中需要"常用联系人"功能，可以直接在此基础上扩展
 */
public class Sender {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 中文姓名
	 */
	private String chName;
	/**
	 * 英文姓名
	 */
	private String enName;
	/**
	 * 邮箱地址
	 */
	private String emailAddress;
	
	public Sender() {
	}

	public Sender(Long id) {
		this.id = id;
	}

	public Sender(Long id, String chName, String enName) {
		this(id);
		this.chName = chName;
		this.enName = enName;
	}

	public Sender(Long id, String chName, String enName, String emailAddress) {
		this(id, chName, enName);
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
