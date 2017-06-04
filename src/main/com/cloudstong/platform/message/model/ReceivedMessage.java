package com.cloudstong.platform.message.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * inbox message
 * 
 * @author Jason 2012-10-16下午03:48:52
 * 
 *         收件箱消息类
 */
public class ReceivedMessage extends EntityBase {

	private static final long serialVersionUID = -7216288860056220355L;
	/**
	 * id
	 */
	private Long id;

	/**
	 * 收件人id
	 */
	private Long receiver;

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
	 * 接收时间
	 */
	private Date date;

	/**
	 * 是否已读
	 */
	private boolean read;

	/**
	 * 回复状态： 需要为1，已经回复为2，不需要为0
	 */
	private int replyState;

	/**
	 * 消息类型，对应系统数据字典
	 */
	private String type;

	/**
	 * 发件箱对应邮件id
	 */
	private Long sentMessageId;// 发件id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
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

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public int getReplyState() {
		return replyState;
	}

	public void setReplyState(int replyState) {
		this.replyState = replyState;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSentMessageId() {
		return sentMessageId;
	}

	public void setSentMessageId(Long sentMessageId) {
		this.sentMessageId = sentMessageId;
	}

}
