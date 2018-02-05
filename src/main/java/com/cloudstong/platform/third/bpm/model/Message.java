package com.cloudstong.platform.third.bpm.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.resource.template.model.SysTemplate;

public class Message extends EntityBase {
	public static final String TABLE_NAME = "SYS_MESSAGE";
	public static final Integer MAIL_TYPE = Integer.valueOf(1);
	public static final Integer MOBILE_TYPE = Integer.valueOf(2);
	public static final Integer INNER_TYPE = Integer.valueOf(3);
	protected Long messageId;
	protected String subject;
	protected String receiver;
	protected String copyTo;
	protected String bcc;
	protected String fromUser;
	protected Long templateId;
	protected Integer messageType;
	protected String jumpUrl;
	protected String remainTime;
	protected String content;
	protected SysTemplate sysTemplate;
	protected Integer sendToStartUser;
	protected String actDefId;
	protected String nodeId;

	public String getRemainTime() {
		if (remainTime == null)
			return "";
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	public String getJumpUrl() {
		if (jumpUrl == null)
			return "";
		return jumpUrl;
	}

	public Integer getSendToStartUser() {
		return sendToStartUser;
	}

	public void setSendToStartUser(Integer sendToStartUser) {
		this.sendToStartUser = sendToStartUser;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public SysTemplate getSysTemplate() {
		return sysTemplate;
	}

	public void setSysTemplate(SysTemplate sysTemplate) {
		this.sysTemplate = sysTemplate;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	public String getCopyTo() {
		return copyTo;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBcc() {
		return bcc;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Message)) {
			return false;
		}
		Message rhs = (Message) object;
		return new EqualsBuilder().append(messageId, rhs.messageId).append(subject, rhs.subject).append(receiver, rhs.receiver)
				.append(copyTo, rhs.copyTo).append(bcc, rhs.bcc).append(fromUser, rhs.fromUser).append(templateId, rhs.templateId)
				.append(messageType, rhs.messageType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(messageId).append(subject).append(receiver).append(copyTo).append(bcc)
				.append(fromUser).append(templateId).append(messageType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("messageId", messageId).append("subject", subject).append("receiver", receiver)
				.append("copyTo", copyTo).append("bcc", bcc).append("fromUser", fromUser).append("templateId", templateId)
				.append("messageType", messageType).toString();
	}

	@Override
	public Long getId() {
		return messageId;
	}

	@Override
	public void setId(Long id) {
		messageId = id;
	}
}