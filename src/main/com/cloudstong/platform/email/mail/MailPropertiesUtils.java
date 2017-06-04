/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.mail;

import java.util.Locale;
import java.util.Properties;

import com.cloudstong.platform.email.model.MailAccount;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
public abstract class MailPropertiesUtils {

	public static Properties convert(MailAccount mailAccount) {
		Properties props = new Properties();
		String type = mailAccount.getMailType().toLowerCase(Locale.ENGLISH);
		props.setProperty("mail.store.protocol", type);
		if ("pop3".equals(type)) {
			props.setProperty("mail.pop3.host", mailAccount.getPopHost());
			props.setProperty("mail.pop3.port", mailAccount.getPopPort() + "");
		} else if ("imap".equals(type)) {
			props.setProperty("mail.imap.host", mailAccount.getPopHost());
			props.setProperty("mail.imap.port", mailAccount.getPopPort() + "");
		}
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.host", mailAccount.getSmtpHost());
		props.setProperty("mail.smtp.port", mailAccount.getSmtpPort() + "");
		return props;
	}
}
