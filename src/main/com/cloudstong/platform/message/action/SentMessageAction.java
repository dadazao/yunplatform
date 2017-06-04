/*******************************************************************************
] * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.message.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.message.model.SentMessage;
import com.cloudstong.platform.message.service.SentMessageService;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.dwr.service.MessagePusher;

/**
 * @author Jason
 * 
 *         Created on 2014-9-25
 * 
 *         Description: 系统内部消息：发送消息Action
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/message/sentMessage")
@Results({ @Result(name = "create", location = "/pages/message/sentMessageEdit.jsp"),
		@Result(name = "list", location = "/pages/message/sentMessageList.jsp"),
		@Result(name = "view", location = "/pages/message/sentMessageView.jsp") })
public class SentMessageAction extends BaseAction {

	private static final long serialVersionUID = 9094309966771180874L;

	@Resource
	private SentMessageService sentMessageService;

	private SentMessage sentMessage;

	private Long id;

	private Long[] sentMessageIDs;

	private List<Map<String, Object>> replyedMessageList;
	private List<Map<String, Object>> readerList;

	@Action("create")
	public String create() {
		sentMessage = new SentMessage();
		return "create";
	}

	@Action("delete")
	public String delete() {
		if (sentMessageIDs != null) {
			sentMessageService.doDeleteSentMessage(sentMessageIDs);
		}
		printJSON("success");
		return NONE;
	}

	@Action("list")
	public String list() {
		SysUser user = getUser();
		queryCriteria = new QueryCriteria();
		if (sentMessage != null) {
			String subject = sentMessage.getSubject();
			if (subject != null && subject.trim().length() != 0) {
				queryCriteria.addQueryCondition("tbl_subject", "%" + subject.trim() + "%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		queryCriteria.addQueryCondition("tbl_sender", user.getId());
		queryCriteria.setOrderField("tbl_date");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		pageResult = sentMessageService.querySentMessage(queryCriteria);
		return "list";
	}

	@Action("send")
	public String send() {
		if (sentMessage != null) {
			SysUser user = getUser();
			sentMessage.setSender(user.getId());// 设置发件人
			sentMessage.setSenderName(user.getFullname());
			sentMessage.setType("2");// 普通消息
			List<Long> receiverIdList = sentMessageService.doSendMessage(sentMessage);
			MessagePusher.sendMessageAuto(receiverIdList);
			printSuccess(sentMessage);
		} else {
			log.error("sentMessage对象为空");
			printJSON("300", "操作失败，请稍后再试");
		}
		return NONE;
	}

	@Action("view")
	public String view() {
		sentMessage = sentMessageService.findSentMessageById(id);
		readerList = sentMessageService.getReaderList(id);
		replyedMessageList = sentMessageService.getReplyedMessage(id, getUser());
		return "view";
	}

	public SentMessage getSentMessage() {
		return sentMessage;
	}

	public void setSentMessage(SentMessage sentMessage) {
		this.sentMessage = sentMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getSentMessageIDs() {
		return sentMessageIDs;
	}

	public void setSentMessageIDs(Long[] sentMessageIDs) {
		this.sentMessageIDs = sentMessageIDs;
	}

	public List<Map<String, Object>> getReplyedMessageList() {
		return replyedMessageList;
	}

	public void setReplyedMessageList(List<Map<String, Object>> replyedMessageList) {
		this.replyedMessageList = replyedMessageList;
	}

	public List<Map<String, Object>> getReaderList() {
		return readerList;
	}

	public void setReaderList(List<Map<String, Object>> readerList) {
		this.readerList = readerList;
	}

}
