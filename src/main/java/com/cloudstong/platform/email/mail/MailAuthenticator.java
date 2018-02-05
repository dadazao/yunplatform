/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
public class MailAuthenticator extends Authenticator {
	private String userName;

	private String password;

	/**
	 * Description:Constructor for MailAuthenticator class.
	 * 
	 * @param userName
	 * @param password
	 */
	public MailAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

}
