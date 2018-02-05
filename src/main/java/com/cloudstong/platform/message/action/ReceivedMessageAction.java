/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.message.model.ReceivedMessage;
import com.cloudstong.platform.message.model.SentMessage;
import com.cloudstong.platform.message.service.ReceivedMessageService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.dwr.service.MessagePusher;

/**
 * @author Jason
 * 
 *         Created on 2014-9-25
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/message/receivedMessage")
@Results({ @Result(name = "view", location = "/WEB-INF/view/message/receivedMessageView.jsp"),
		@Result(name = "replyui", location = "/WEB-INF/view/message/replyMessage.jsp"),
		@Result(name = "list", location = "/WEB-INF/view/message/receivedMessageList.jsp") })
public class ReceivedMessageAction extends BaseAction {
	private static final long serialVersionUID = -1041485229100742508L;

	@Resource
	private ReceivedMessageService receivedMessageService;

	private ReceivedMessage receivedMessage;
	private SentMessage sentMessage;
	private Long id;
	private Long[] receivedMessageIDs;

	@Action("delete")
	public String delete() {
		if (receivedMessageIDs != null) {
			receivedMessageService.doDeleteMessage(receivedMessageIDs);
			// 更新首页消息数量
			MessagePusher.sendMessageAuto(getUser().getId());
		}
		printJSON("success");
		return NONE;
	}

	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (receivedMessage != null) {
			String subject = receivedMessage.getSubject();
			if (subject != null && subject.trim().length() != 0) {
				queryCriteria.addQueryCondition("tbl_subject", "%" + subject.trim() + "%");
			}
			String type = receivedMessage.getType();
			if (type != null && !type.equals("-1") && !"".equals(type)) {
				queryCriteria.addQueryCondition("tbl_type", type);
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);

		queryCriteria.addQueryCondition("tbl_receiver", getUser().getId());

		queryCriteria.setOrderField("tbl_date");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		pageResult = receivedMessageService.queryReceivedMessage(queryCriteria);
		return "list";
	}

	@Action("replyui")
	public String replyui() {
		receivedMessage = receivedMessageService.doViewReceivedMessage(id);
		sentMessage = new SentMessage();
		sentMessage.setReplyMessageId(receivedMessage.getSentMessageId());
		sentMessage.setReceiver(String.valueOf(receivedMessage.getSender()));
		sentMessage.setSubject("回复：" + receivedMessage.getSubject());
		// 更新首页消息数量
		MessagePusher.sendMessageAuto(getUser().getId());
		return "replyui";
	}

	@Action("view")
	public String view() {
		receivedMessage = receivedMessageService.doViewReceivedMessage(id);// findReceivedMessageById(id);
		// 更新首页消息数量
		MessagePusher.sendMessageAuto(getUser().getId());
		return "view";
	}

	@Action("size")
	public String fetchLastestUnreadMessageSize() {
		SysUser user = getUser();
		Integer size = receivedMessageService.countLastestUnreadMessage(user);
		printObject(size);
		return NONE;
	}

	public ReceivedMessage getReceivedMessage() {
		return receivedMessage;
	}

	public void setReceivedMessage(ReceivedMessage receivedMessage) {
		this.receivedMessage = receivedMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getReceivedMessageIDs() {
		return receivedMessageIDs;
	}

	public void setReceivedMessageIDs(Long[] receivedMessageIDs) {
		this.receivedMessageIDs = receivedMessageIDs;
	}

	public SentMessage getSentMessage() {
		return sentMessage;
	}

	public void setSentMessage(SentMessage sentMessage) {
		this.sentMessage = sentMessage;
	}

}
