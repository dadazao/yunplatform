package com.cloudstong.platform.message.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * outbox message
 * 
 * @author Jason 2012-10-16下午03:51:38
 * 
 *         发件箱消息类
 */
public class SentMessage extends EntityBase {
	private static final long serialVersionUID = -1581264374037415379L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 收件人id,多个以英文分号”;“分隔
	 */
	private String receiver;

	/**
	 * 收件组织
	 */
	private String receiveGroups;

	/**
	 * 发件人id
	 */
	private Long sender;

	/**
	 * 发件人姓名
	 */
	private String senderName;

	/**
	 * 消息主题
	 */
	private String subject;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 发送时间
	 */
	private Date date;

	/**
	 * 是否需要回复
	 */
	private boolean needReply = false;

	/**
	 * 消息类型，对应系统数据字典
	 */
	private String type = "-1";

	/**
	 * 回复消息id
	 */
	private Long replyMessageId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiveGroups() {
		return receiveGroups;
	}

	public void setReceiveGroups(String receiveGroups) {
		this.receiveGroups = receiveGroups;
	}

	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isNeedReply() {
		return needReply;
	}

	public void setNeedReply(boolean needReply) {
		this.needReply = needReply;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getReplyMessageId() {
		return replyMessageId;
	}

	public void setReplyMessageId(Long replyMessageId) {
		this.replyMessageId = replyMessageId;
	}

}
