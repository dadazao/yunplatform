/*******************************************************************************
] * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.email.model.IEmail;
import com.cloudstong.platform.email.model.MailAccount;
import com.cloudstong.platform.email.model.MailAttachment;
import com.cloudstong.platform.email.service.MailAttachmentService;
import com.cloudstong.platform.email.util.MailUtils;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
public class MailSender implements Runnable {
	private final Logger log = Logger.getLogger(getClass());

	private IEmail email;
	private MailAccount mailAccount;

	private MailAttachmentService mailAttachmentService;

	public MailSender(MailAttachmentService mailAttachmentService, IEmail email,
			MailAccount mailAccount) {
		this.mailAttachmentService = mailAttachmentService;
		this.email = email;
		this.mailAccount = mailAccount;
	}

	private void send() {
		try {
			HtmlEmail mail = new HtmlEmail();
			mail.setAuthentication(mailAccount.getAddress(),
					EncryptUtil.getDesString(mailAccount.getPassword()));
			mail.setHostName(mailAccount.getSmtpHost());
			if (email.getCc() != null && email.getCc().trim().length() != 0) {
				List<InternetAddress> _list = stringToInetAddress(email.getCc());
				if (!_list.isEmpty()) {
					mail.setCc(_list);
				}
			}
			mail.setTo(stringToInetAddress(email.getTo()));
			mail.setCharset("UTF-8");
			mail.setFrom(email.getFrom());
			mail.setSmtpPort(mailAccount.getSmtpPort());
			String content = email.getContent();
			if (content == null || content.length() == 0) {
				content = " ";
			}
			mail.setHtmlMsg(content);
			mail.setTextMsg(content);
			mail.setSubject(email.getSubject());

			mail.setDebug(false); // remember to set it false

			// 处理附件
			List<MailAttachment> attaList = mailAttachmentService.findMailAttachmentByMailId(email
					.getId());
			if (!attaList.isEmpty()) {
				for (MailAttachment atta : attaList) {
					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(atta.getPath());
					String fileName = atta.getName();
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					BASE64Encoder enc = new BASE64Encoder();
					String encName = null;
					try {
						encName = "=?UTF-8?B?" + enc.encode(fileName.getBytes("utf-8")) + "?=";
					} catch (UnsupportedEncodingException e) {
						log.error(e);
						encName = fileName;
					}
					attachment.setName(encName);
					attachment.setDescription(encName);
					mail.attach(attachment);
				}
			}
			mail.send();
		} catch (EmailException e) {
			e.printStackTrace();
			log.error(e);
		}

	}

	/*
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		send();
	}

	private List<InternetAddress> stringToInetAddress(String to) {
		if (to == null) {
			throw new IllegalArgumentException("the receivers are null,which is not allowed");
		}
		String[] recptaddrs = to.split(";");
		List<InternetAddress> addrList = new ArrayList<InternetAddress>();
		for (String recptaddr : recptaddrs) {
			if (!MailUtils.isEmail(recptaddr)) {// 忽略不合法的收件人地址
				continue;
			}
			try {
				addrList.add(new InternetAddress(recptaddr));
			} catch (AddressException e) {
				log.error(e);
				continue;
			}
		}
		return addrList;
	}
}
