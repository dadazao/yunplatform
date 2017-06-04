/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.email.mail.MailReceiver;
import com.cloudstong.platform.email.model.InboxMessage;
import com.cloudstong.platform.email.model.MailAccount;
import com.cloudstong.platform.email.model.MailAttachment;
import com.cloudstong.platform.email.model.MailMessage;
import com.cloudstong.platform.email.service.InboxMessageService;
import com.cloudstong.platform.email.service.MailAccountService;
import com.cloudstong.platform.email.service.MailAttachmentService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-17
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/email/inboxMessage")
@Results({ @Result(name = "inbox", location = "/pages/email/inbox.jsp"), @Result(name = "refetchInbox", location = "inbox", type = "chain"),
		@Result(name = "view", location = "/pages/email/inboxMessageView.jsp"),
		@Result(name = "reply", location = "/pages/email/mailMessageEdit.jsp") })
public class InboxMessageAction extends BaseAction {

	private static final long serialVersionUID = 7241842441003264076L;

	@Resource
	private InboxMessageService inboxMessageService;

	@Resource
	private MailAccountService mailAccountService;

	@Resource
	private MailAttachmentService mailAttachmentService;

	private InboxMessage inboxMessage;
	private Long inboxMessageId;
	private String email;
	private Long mailAccountId;
	private Long[] inboxMessageIDs;

	@Action("inbox")
	public String inbox() {
		SysUser user = getUser();
		queryCriteria = new QueryCriteria();
		if (inboxMessage != null) {
			String from = inboxMessage.getFrom();
			String subject = inboxMessage.getSubject();
			if (from != null && from.length() != 0) {
				queryCriteria.addQueryCondition("tbl_from", "%" + from.trim() + "%");
			}
			if (subject != null && subject.length() != 0) {
				queryCriteria.addQueryCondition("tbl_subject", "%" + subject.trim() + "%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		queryCriteria.addQueryCondition("tbl_useremail", email); // 收件人为当前邮箱的
		queryCriteria.addQueryCondition("tbl_userid", user.getId());
		queryCriteria.addQueryCondition("comm_mark_for_delete", 0);// 没有删除的

		queryCriteria.setOrderField("tbl_date");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		pageResult = inboxMessageService.findInboxMessage(queryCriteria);
		return "inbox";
	}

	@Action("receive")
	public String receive() {
		try {
			SysUser user = getUser();
			MailAccount mailAccount = null;
			if (mailAccountId != null) {
				mailAccount = mailAccountService.findMailAccountById(mailAccountId);
				email = mailAccount.getAddress();// important
			} else {
				mailAccount = mailAccountService.findMailAccountByAddress(email);
			}
			if (mailAccount != null) {
				List<MailAttachment> attaList = new ArrayList<MailAttachment>();

				String contextPath = getSession().getServletContext().getRealPath("/") + File.separator + "upload";
				List<InboxMessage> list = new MailReceiver(mailAccount, attaList, contextPath).receive();

				inboxMessageService.doSaveInboxMessage(list, user);
				mailAttachmentService.doSaveMailAttachments(attaList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "refetchInbox";
	}

	@Action("defaultInbox")
	public String defaultInbox() {
		MailAccount mail = mailAccountService.findDefaultMailAccount(getUser());
		if (mail != null) {
			mailAccountId = mail.getId();
			email = mail.getAddress();
			SysUser user = getUser();
			queryCriteria = new QueryCriteria();
			if (this.pageNum == null || "".equals(this.pageNum)) {
				this.pageNum = 1;
			}

			queryCriteria.setCurrentPage(this.pageNum);
			queryCriteria.setPageSize(this.numPerPage);

			queryCriteria.addQueryCondition("tbl_useremail", email); // 收件人为当前邮箱的
			queryCriteria.addQueryCondition("tbl_userid", user.getId());
			queryCriteria.addQueryCondition("comm_mark_for_delete", 0);// 没有删除的

			queryCriteria.setOrderField("tbl_date");
			queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

			pageResult = inboxMessageService.findInboxMessage(queryCriteria);
		}
		return "inbox";
	}

	@Action("view")
	public String view() {
		if (inboxMessage != null) {
			inboxMessage = inboxMessageService.doViewInboxMessage(inboxMessage.getId());
		}
		return "view";
	}

	@Action("reply")
	public String reply() {
		InboxMessage message = inboxMessageService.findInboxMessageById(inboxMessageId);
		MailMessage mailMessage = new MailMessage();
		mailMessage.setTo(email);
		mailMessage.setSubject("回复邮件:" + message.getSubject());
		String boundary = "=========================原始邮件=============================<br/>";
		mailMessage.setContent(boundary + message.getContent());
		getRequest().setAttribute("mailMessage", mailMessage);
		return "reply";
	}

	@Action("putIntoDustbin")
	public String putIntoDustbin() {
		if (inboxMessageIDs != null) {
			inboxMessageService.doPutIntoDustbin(inboxMessageIDs);
		}
		printJSON("success");
		return NONE;
	}

	public InboxMessage getInboxMessage() {
		return inboxMessage;
	}

	public void setInboxMessage(InboxMessage InboxMessage) {
		this.inboxMessage = InboxMessage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMailAccountId() {
		return mailAccountId;
	}

	public void setMailAccountId(Long mailAccountId) {
		this.mailAccountId = mailAccountId;
	}

	public Long getInboxMessageId() {
		return inboxMessageId;
	}

	public void setInboxMessageId(Long inboxMessageId) {
		this.inboxMessageId = inboxMessageId;
	}

	public Long[] getInboxMessageIDs() {
		return inboxMessageIDs;
	}

	public void setInboxMessageIDs(Long[] inboxMessageIDs) {
		this.inboxMessageIDs = inboxMessageIDs;
	}

}
