package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmNodeUser")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeUser extends EntityBase implements Cloneable {
	public static final short ASSIGN_TYPE_START_USER = 0;
	public static final short ASSIGN_TYPE_USER = 1;
	public static final short ASSIGN_TYPE_ROLE = 2;
	public static final short ASSIGN_TYPE_ORG = 3;
	public static final short ASSIGN_TYPE_ORG_CHARGE = 4;
	public static final short ASSIGN_TYPE_POS = 5;
	public static final short ASSIGN_TYPE_UP_LOW = 6;
	public static final short ASSIGN_TYPE_USER_ATTR = 7;
	public static final short ASSIGN_TYPE_ORG_ATTR = 8;
	public static final short ASSIGN_TYPE_SAME_DEP = 9;
	public static final short ASSIGN_TYPE_SAME_NODE = 10;
	public static final short ASSIGN_TYPE_DIRECT_LED = 11;
	public static final short ASSIGN_TYPE_SCRIPT = 12;
	public static final short ASSIGN_TYPE_PREUSER_ORG_LEADER = 13;
	public static final short ASSIGN_TYPE_STARTUSER_LEADER = 14;
	public static final short ASSIGN_TYPE_PREVUSER_LEADER = 15;
	public static final short ASSIGN_TYPE_PREVTYPEUSER_LEADER = 16;
	public static final short COMP_TYPE_OR = 0;
	public static final short COMP_TYPE_AND = 1;
	public static final short COMP_TYPE_EXCLUDE = 2;
	public static final short ASSIGN_USE_TYPE_PARTICIPATION = 0;
	public static final short ASSIGN_USE_TYPE_NOTIFY = 1;
	public static final String USER_TYPE_PARTICIPATION = "PARTICIPATION";
	public static final String USER_TYPE_NOTIFY = "NOTIFY";

	@XmlAttribute
	protected Long nodeUserId;

	@XmlAttribute
	protected Long setId;

	@XmlAttribute
	protected String nodeId;

	@XmlAttribute
	protected Short assignType;

	@XmlAttribute
	protected Short assignUseType = Short.valueOf((short) 0);

	@XmlAttribute
	protected String actDefId;

	@XmlAttribute
	protected String cmpIds;

	@XmlAttribute
	protected String cmpNames;

	@XmlAttribute
	protected Short compType;

	@XmlAttribute
	protected Integer sn;

	@XmlAttribute
	protected Long conditionId;

	public void setNodeUserId(Long nodeUserId) {
		this.nodeUserId = nodeUserId;
	}

	public Long getNodeUserId() {
		return nodeUserId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setAssignType(Short assignType) {
		this.assignType = assignType;
	}

	public Short getAssignType() {
		return assignType;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeUser)) {
			return false;
		}
		BpmNodeUser rhs = (BpmNodeUser) object;
		return new EqualsBuilder().append(nodeUserId, rhs.nodeUserId).append(setId, rhs.setId).append(nodeId, rhs.nodeId)
				.append(assignType, rhs.assignType).append(actDefId, rhs.actDefId).append(cmpIds, rhs.cmpIds).append(conditionId, rhs.conditionId)
				.append(cmpNames, rhs.cmpNames).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(nodeUserId).append(setId).append(nodeId).append(assignType).append(actDefId)
				.append(cmpIds).append(conditionId).append(cmpNames).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("nodeUserId", nodeUserId).append("setId", setId).append("nodeId", nodeId)
				.append("assignType", assignType).append("actDefId", actDefId).append("cmpIds", cmpIds).append("cmpNames", cmpNames)
				.append("conditionId", conditionId).toString();
	}

	public String getCmpIds() {
		return cmpIds;
	}

	public void setCmpIds(String cmpIds) {
		this.cmpIds = cmpIds;
	}

	public String getCmpNames() {
		return cmpNames;
	}

	public void setCmpNames(String cmpNames) {
		this.cmpNames = cmpNames;
	}

	public Short getCompType() {
		return compType;
	}

	public void setCompType(Short compType) {
		this.compType = compType;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Short getAssignUseType() {
		return Short.valueOf(assignUseType == null ? 0 : assignUseType.shortValue());
	}

	public void setAssignUseType(Short assignUseType) {
		this.assignUseType = assignUseType;
	}

	public Long getConditionId() {
		return conditionId;
	}

	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}

	public Object clone() {
		BpmNodeUser obj = null;
		try {
			obj = (BpmNodeUser) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long getId() {
		return nodeUserId;
	}

	@Override
	public void setId(Long id) {
		nodeUserId = id;
	}

}