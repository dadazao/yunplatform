/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.email.mail.MailSender;
import com.cloudstong.platform.email.model.MailAccount;
import com.cloudstong.platform.email.model.MailMessage;
import com.cloudstong.platform.email.service.MailAccountService;
import com.cloudstong.platform.email.service.MailAttachmentService;
import com.cloudstong.platform.email.service.MailMessageService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/email/mailMessage")
@Results({ @Result(name = "create", location = "/pages/email/mailMessageEdit.jsp"),
		@Result(name = "edit", location = "/pages/email/mailMessageEdit.jsp"), @Result(name = "view", location = "/pages/email/mailMessageView.jsp"),
		@Result(name = "outbox", location = "/pages/email/outbox.jsp"), @Result(name = "draftbox", location = "/pages/email/draftbox.jsp") })
public class MailMessageAction extends BaseAction {

	private static final long serialVersionUID = 246211032448127837L;

	@Resource
	private MailMessageService mailMessageService;

	@Resource
	private MailAccountService mailAccountService;

	@Resource
	private MailAttachmentService mailAttachmentService;

	private MailMessage mailMessage;
	private Long mailMessageId;

	private String email;
	private Long[] mailMessageIDs;

	@Action("create")
	public String create() {
		mailMessage = new MailMessage();
		mailMessage.setId(UniqueIdUtil.genId());// 必须设置ID
		return "create";
	}

	@Action("send")
	public String send() throws IOException {
		SysUser user = (SysUser) getSession().getAttribute("user");
		if (mailMessage != null) {
			try {
				mailMessage.setUserid(user.getId());// important
				mailMessageService.doSendMailMessage(mailMessage);
				// 发送邮件
				MailAccount account = mailAccountService.findMailAccountByAddress(mailMessage.getFrom());
				new Thread(new MailSender(mailAttachmentService, mailMessage, account)).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		printJSON("success");
		return NONE;
	}

	// 再次发送..
	@Action("resend")
	public String resend() {
		mailMessage = mailMessageService.findMailMessageById(mailMessageId);
		return "create";
	}

	// 转发,需要数据库中新插入一条记录
	@Action("forward")
	public String forward() {
		mailMessage = mailMessageService.findMailMessageById(mailMessageId);
		String subject = "转发:" + mailMessage.getSubject();
		mailMessage.setSubject(subject);
		mailMessage.setId(UniqueIdUtil.genId()); //需要重新赋予id
		return "create";
	}

	@Action("draften")
	public String draften() {
		SysUser user = (SysUser) getSession().getAttribute("user");
		if (mailMessage != null) {
			mailMessage.setUserid(user.getId());// important
			mailMessageService.doDraftenMailMessage(mailMessage);
		}
		printJSON("success");
		return NONE;
	}

	@Action("draftbox")
	public String draftbox() {
		SysUser user = (SysUser) getSession().getAttribute("user");
		queryCriteria = new QueryCriteria();
		if (mailMessage != null) {
			String to = mailMessage.getTo();
			String subject = mailMessage.getSubject();
			if (to != null && to.length() != 0) {
				queryCriteria.addQueryCondition("tbl_to", "%" + to.trim() + "%");
			}
			if (subject != null && subject.length() != 0) {
				queryCriteria.addQueryCondition("tbl_subject", "%" + subject.trim() + "%");
			}
		}
		queryCriteria.addQueryCondition("tbl_userid", user.getId());
		queryCriteria.addQueryCondition("tbl_draft", 1);
		queryCriteria.addQueryCondition("comm_mark_for_delete", 0);

		queryCriteria.setOrderField("comm_createDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		pageResult = mailMessageService.findMailMessage(queryCriteria);
		return "draftbox";
	}

	@Action("putIntoDustbin")
	public String putIntoDustbin() {
		if (mailMessageIDs != null) {
			mailMessageService.doPutIntoDustbin(mailMessageIDs);
		}
		printJSON("success");
		return NONE;
	}

	@Action("outbox")
	public String outbox() {
		SysUser user = (SysUser) getSession().getAttribute("user");
		queryCriteria = new QueryCriteria();
		if (mailMessage != null) {
			String to = mailMessage.getTo();
			String subject = mailMessage.getSubject();
			if (to != null && to.length() != 0) {
				queryCriteria.addQueryCondition("tbl_to", "%" + to.trim() + "%");
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

		queryCriteria.addQueryCondition("tbl_userid", user.getId());
		queryCriteria.addQueryCondition("tbl_from", email);
		queryCriteria.addQueryCondition("comm_mark_for_delete", 0);

		queryCriteria.setOrderField("comm_createDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		pageResult = mailMessageService.findMailMessage(queryCriteria);
		return "outbox";
	}

	@Action("view")
	public String view() {
		if (mailMessage != null) {
			mailMessage = mailMessageService.viewMailMessage(mailMessage.getId());
		}
		return "view";
	}

	public MailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long[] getMailMessageIDs() {
		return mailMessageIDs;
	}

	public void setMailMessageIDs(Long[] mailMessageIDs) {
		this.mailMessageIDs = mailMessageIDs;
	}

	public Long getMailMessageId() {
		return mailMessageId;
	}

	public void setMailMessageId(Long mailMessageId) {
		this.mailMessageId = mailMessageId;
	}

}
