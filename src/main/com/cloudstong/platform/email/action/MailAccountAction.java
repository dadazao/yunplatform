/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.email.mail.MailAuthenticator;
import com.cloudstong.platform.email.mail.MailPropertiesUtils;
import com.cloudstong.platform.email.model.MailAccount;
import com.cloudstong.platform.email.service.MailAccountService;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-13
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/email/mailAccount")
@Results({ @Result(name = "add", location = "/pages/email/mailAccountEdit.jsp"),
		@Result(name = "edit", location = "/pages/email/mailAccountEdit.jsp"), @Result(name = "view", location = "/pages/email/mailAccountView.jsp"),
		@Result(name = "list", location = "/pages/email/mailAccountList.jsp"),
		@Result(name = "noMailAccountAvailable", location = "/pages/email/noMailAccountAvailable.jsp"),
		@Result(name = "mailbox", location = "/pages/email/mailbox.jsp") })
public class MailAccountAction extends BaseAction {
	private static final long serialVersionUID = 1440186450064953202L;

	@Resource
	private MailAccountService mailAccountService;

	private MailAccount mailAccount;

	private Long[] mailAccountIDs;

	private Long id;

	@Action("add")
	public String add() {
		mailAccount = new MailAccount();
		return "add";
	}

	@Action("connectTest")
	public String connectTest() throws IOException {
		if (mailAccount != null) {
			mailAccount.setPassword(EncryptUtil.getEncString(mailAccount.getPassword()));// 加密

			Properties props = MailPropertiesUtils.convert(mailAccount);
			final String userName = mailAccount.getAddress();
			final String password = EncryptUtil.getDesString(mailAccount.getPassword());
			Session session = Session.getInstance(props, new MailAuthenticator(userName, password));
			session.setDebug(true); // for debuging, set it true

			Store store = null;
			Transport transport = null;
			try {
				// 收件测试
				store = session.getStore();
				store.connect();
				// 发件测试
				transport = session.getTransport();
				transport.connect();

				printJSON("success");
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
				if (log.isErrorEnabled())
					log.error(e.getMessage(), e);
				printJSON("300", "对不起，连接失败，请确保您的网络通畅以及所填信息正确");
			} catch (MessagingException e) {
				e.printStackTrace();
				if (log.isErrorEnabled())
					log.error(e.getMessage(), e);
				printJSON("300", "对不起，连接失败，请确保您的网络通畅以及所填信息正确");
			} finally {
				try {
					if (store != null) {
						store.close();
					}
					if (transport != null) {
						transport.close();
					}
				} catch (MessagingException e) {
				}
			}
		} else {
			printJSON("300", "参数错误");
		}
		return NONE;
	}

	@Action("delete")
	public String del() throws IOException {
		if (mailAccountIDs == null) {
			if (id != null) {
				mailAccountIDs = new Long[] { id };
			} else {
				mailAccountIDs = new Long[0];
			}
		}
		try {
			mailAccountService.doDeleteMailAccounts(mailAccountIDs, getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		printJSON("success");
		return NONE;
	}

	@Action("edit")
	public String edit() {
		mailAccount = mailAccountService.findMailAccountById(mailAccount.getId());
		mailAccount.setPassword(null);
		return "edit";
	}

	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (mailAccount != null) {
			if (mailAccount.getName() != null && !"".equals(mailAccount.getName())) {
				queryCriteria.addQueryCondition("tbl_name", "%" + mailAccount.getName().trim() + "%");
			}
			if (mailAccount.getAddress() != null && !"".equals(mailAccount.getAddress())) {
				queryCriteria.addQueryCondition("tbl_address", mailAccount.getAddress().trim());
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		queryCriteria.addQueryCondition("tbl_userid", ((SysUser) getSession().getAttribute("user")).getId());

		// 列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		this.pageResult = mailAccountService.queryMailAccount(queryCriteria);
		return "list";
	}

	@Action("fromList")
	public String fromList() {
		SysUser user = (SysUser) getSession().getAttribute("user");
		List<MailAccount> mailAccountList = mailAccountService.getMailAccountByUser(user);
		printObject(mailAccountList);
		return NONE;
	}

	@Action("mailbox")
	public String mailbox() {
		List<MailAccount> mailAccountList = mailAccountService.getMailAccountByUser(getUser());
		if (mailAccountList == null || mailAccountList.isEmpty()) {
			return "noMailAccountAvailable";
		}
		return "mailbox";
	}

	@Action("save")
	public String save() {
		try {
			mailAccount.setPassword(EncryptUtil.getEncString(mailAccount.getPassword()));// 加密
			if (mailAccount.getId() == null) {
				if (mailAccountService.checkExist(mailAccount, getUser())) {
					printJSON("300", "您已添加该账号，请不要重复添加");
					return NONE;
				}
				mailAccount.setUserId(((SysUser) getSession().getAttribute("user")).getId());
				mailAccountService.doSaveMailAccount(mailAccount);
			} else {
				mailAccountService.doUpdateMailAccount(mailAccount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		printSuccess(mailAccount);
		return NONE;
	}

	@Action("view")
	public String view() {
		mailAccount = mailAccountService.findMailAccountById(mailAccount.getId());
		return "view";
	}

	@Action("setDefault")
	public String setDefault() {
		if (mailAccountIDs != null && mailAccountIDs.length > 0) {
			Long id = mailAccountIDs[0];
			mailAccountService.doSetDefault(id, getUser());
		}
		printJSON("success");
		return NONE;
	}

	@Action("ztreeMailAccount")
	public String ztreeMailAccount() {
		SysUser user = (SysUser) getSession().getAttribute("user");
		List<MailAccount> list = mailAccountService.getMailAccountByUser(user);
		List<Map<String, Object>> ztree = new ArrayList<Map<String, Object>>();
		for (MailAccount c : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", c.getId());
			map.put("name", c.getName());
			map.put("isParent", true);
			map.put("open", true);
			map.put("email", c.getAddress());
			map.put("title", c.getAddress());
			ztree.add(map);
			ztree.addAll(generateChildren(c));
		}
		printObject(ztree);
		return NONE;
	}

	private List<Map<String, Object>> generateChildren(MailAccount account) {
		List<Map<String, Object>> childTree = new ArrayList<Map<String, Object>>();
		Map<String, Object> inbox = new HashMap<String, Object>();
		inbox.put("id", account.getId() + "inbox");
		inbox.put("name", "收件箱");
		inbox.put("pId", account.getId());
		inbox.put("email", account.getAddress());
		inbox.put("kind", "inbox");
		inbox.put("isParent", false);
		inbox.put("accountId", account.getId());
		inbox.put("title", "收件箱");

		Map<String, Object> outbox = new HashMap<String, Object>();
		outbox.put("id", account.getId() + "outbox");
		outbox.put("name", "发件箱");
		outbox.put("pId", account.getId());
		outbox.put("email", account.getAddress());
		outbox.put("kind", "outbox");
		outbox.put("isParent", false);
		outbox.put("accountId", account.getId());
		outbox.put("title", "发件箱");

		Map<String, Object> draftbox = new HashMap<String, Object>();
		draftbox.put("id", account.getId() + "draftbox");
		draftbox.put("name", "草稿箱");
		draftbox.put("pId", account.getId());
		draftbox.put("email", account.getAddress());
		draftbox.put("kind", "draftbox");
		draftbox.put("isParent", false);
		draftbox.put("accountId", account.getId());
		draftbox.put("title", "草稿箱");

		Map<String, Object> dustbin = new HashMap<String, Object>();
		dustbin.put("id", account.getId() + "dustbin");
		dustbin.put("name", "垃圾箱");
		dustbin.put("pId", account.getId());
		dustbin.put("email", account.getAddress());
		dustbin.put("kind", "dustbin");
		dustbin.put("isParent", false);
		dustbin.put("accountId", account.getId());
		dustbin.put("title", "垃圾箱");

		childTree.add(inbox);
		childTree.add(outbox);
		childTree.add(draftbox);
		childTree.add(dustbin);
		return childTree;
	}

	public MailAccount getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(MailAccount mailAccount) {
		this.mailAccount = mailAccount;
	}

	public Long[] getMailAccountIDs() {
		return mailAccountIDs;
	}

	public void setMailAccountIDs(Long[] mailAccountIDs) {
		this.mailAccountIDs = mailAccountIDs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
