package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmNodeWebService extends EntityBase {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String actDefId;
	protected String nodeId;
	protected String document;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
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
		if (!(object instanceof BpmNodeWebService)) {
			return false;
		}
		BpmNodeWebService rhs = (BpmNodeWebService) object;
		return new EqualsBuilder().append(id, rhs.id).append(actDefId, rhs.actDefId).append(nodeId, rhs.nodeId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(actDefId).append(nodeId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("actDefId", actDefId).append("nodeId", nodeId).toString();
	}
}