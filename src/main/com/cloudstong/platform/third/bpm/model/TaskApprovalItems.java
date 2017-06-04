package com.cloudstong.platform.third.bpm.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

@XmlRootElement(name = "taskApprovalItems")
@XmlAccessorType(XmlAccessType.NONE)
public class TaskApprovalItems extends EntityBase {
	public static final Short global = Short.valueOf((short) 1);

	public static final Short notGlobal = Short.valueOf((short) 0);

	@XmlAttribute
	protected Long itemId;

	@XmlAttribute
	protected Long setId;

	@XmlAttribute
	protected String actDefId;

	@XmlAttribute
	protected String nodeId;

	@XmlAttribute
	protected Short isGlobal;

	@XmlAttribute
	protected String expItems;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
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

	public Short getIsGlobal() {
		return isGlobal;
	}

	public void setIsGlobal(Short isGlobal) {
		this.isGlobal = isGlobal;
	}

	public String getExpItems() {
		return expItems;
	}

	public void setExpItems(String expItems) {
		this.expItems = expItems;
	}

	public boolean equals(Object object) {
		if (!(object instanceof TaskApprovalItems)) {
			return false;
		}
		TaskApprovalItems rhs = (TaskApprovalItems) object;
		return new EqualsBuilder().append(itemId, rhs.itemId).append(setId, rhs.setId).append(actDefId, rhs.actDefId).append(nodeId, rhs.nodeId)
				.append(isGlobal, rhs.isGlobal).append(expItems, rhs.expItems).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(itemId).append(setId).append(actDefId).append(nodeId).append(isGlobal)
				.append(expItems).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("itemId", itemId).append("setId", setId).append("actDefId", actDefId).append("nodeId", nodeId)
				.append("isGlobal", isGlobal).append("expItems", expItems).toString();
	}

	@Override
	public Long getId() {
		return itemId;
	}

	@Override
	public void setId(Long id) {
		itemId = id;
	}

}