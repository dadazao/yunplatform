package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmNodeRule")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeRule extends EntityBase implements Cloneable {
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "BPM_NODE_RULE";

	@XmlAttribute
	protected Long ruleId = Long.valueOf(0L);

	@XmlAttribute
	protected String ruleName = "";

	@XmlAttribute
	protected String conditionCode = "";

	@XmlAttribute
	protected String actDefId = "";

	@XmlAttribute
	protected String nodeId = "";

	@XmlAttribute
	protected Long priority = Long.valueOf(0L);

	@XmlAttribute
	protected String targetNode = "";

	@XmlAttribute
	protected String targetNodeName = "";

	@XmlAttribute
	protected String memo = " ";

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}

	public String getConditionCode() {
		return conditionCode;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Long getPriority() {
		return priority;
	}

	public void setTargetNode(String targetNode) {
		this.targetNode = targetNode;
	}

	public String getTargetNode() {
		return targetNode;
	}

	public void setTargetNodeName(String targetNodeName) {
		this.targetNodeName = targetNodeName;
	}

	public String getTargetNodeName() {
		return targetNodeName;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return memo;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeRule)) {
			return false;
		}
		BpmNodeRule rhs = (BpmNodeRule) object;
		return new EqualsBuilder().append(actDefId, rhs.actDefId).append(nodeId, rhs.nodeId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(actDefId).append(nodeId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("ruleId", ruleId).append("ruleName", ruleName).append("conditionCode", conditionCode)
				.append("actDefId", actDefId).append("nodeId", nodeId).append("priority", priority).append("targetNode", targetNode)
				.append("targetNodeName", targetNodeName).append("memo", memo).toString();
	}

	public Object clone() {
		BpmNodeRule obj = null;
		try {
			obj = (BpmNodeRule) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long getId() {
		return ruleId;
	}

	@Override
	public void setId(Long id) {
		ruleId = id;
	}

}