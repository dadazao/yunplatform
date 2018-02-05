package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmNodeMessage")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeMessage extends EntityBase implements Cloneable {
	public static final String TABLE_NAME = "BPM_NODE_MESSAGE";

	@XmlAttribute
	protected Long id;

	@XmlAttribute
	protected Long messageId;

	@XmlAttribute
	protected String actDefId;

	@XmlAttribute
	protected String nodeId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeMessage)) {
			return false;
		}
		BpmNodeMessage rhs = (BpmNodeMessage) object;
		return new EqualsBuilder().append(id, rhs.id).append(messageId, rhs.messageId).append(actDefId, rhs.actDefId).append(nodeId, rhs.nodeId)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(messageId).append(actDefId).append(nodeId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("messageId", messageId).append("actDefId", actDefId).append("nodeId", nodeId)
				.toString();
	}

	public Object clone() {
		BpmNodeMessage obj = null;
		try {
			obj = (BpmNodeMessage) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}