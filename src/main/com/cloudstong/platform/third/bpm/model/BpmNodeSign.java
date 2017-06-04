package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmNodeSign")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeSign extends EntityBase implements Cloneable {
	public static final String TABLE_NAME = "BPM_NODE_SIGN";
	public static final Short VOTE_TYPE_PERCENT = Short.valueOf((short) 1);

	public static final Short VOTE_TYPE_ABSOLUTE = Short.valueOf((short) 2);

	public static final Short FLOW_MODE_DIRECT = Short.valueOf((short) 1);

	public static final Short FLOW_MODE_WAITALL = Short.valueOf((short) 2);

	public static final Short DECIDE_TYPE_PASS = Short.valueOf((short) 1);

	public static final Short DECIDE_TYPE_REFUSE = Short.valueOf((short) 2);

	@XmlAttribute
	protected Long signId = Long.valueOf(0L);

	@XmlAttribute
	protected String nodeId = " ";

	@XmlAttribute
	protected Long voteAmount = Long.valueOf(0L);

	@XmlAttribute
	protected Short decideType = Short.valueOf((short) 0);

	@XmlAttribute
	protected Short voteType = Short.valueOf((short) 0);

	@XmlAttribute
	protected String actDefId = " ";

	@XmlAttribute
	protected Short flowMode;

	public Short getFlowMode() {
		return flowMode;
	}

	public void setFlowMode(Short flowMode) {
		this.flowMode = flowMode;
	}

	public void setSignId(Long signId) {
		this.signId = signId;
	}

	public Long getSignId() {
		return signId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setVoteAmount(Long voteAmount) {
		this.voteAmount = voteAmount;
	}

	public Long getVoteAmount() {
		return voteAmount;
	}

	public void setDecideType(Short decideType) {
		this.decideType = decideType;
	}

	public Short getDecideType() {
		return decideType;
	}

	public void setVoteType(Short voteType) {
		this.voteType = voteType;
	}

	public Short getVoteType() {
		return voteType;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeSign)) {
			return false;
		}
		BpmNodeSign rhs = (BpmNodeSign) object;
		return new EqualsBuilder().append(signId, rhs.signId).append(nodeId, rhs.nodeId).append(voteAmount, rhs.voteAmount)
				.append(decideType, rhs.decideType).append(voteType, rhs.voteType).append(actDefId, rhs.actDefId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(signId).append(nodeId).append(voteAmount).append(decideType).append(voteType)
				.append(actDefId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("signId", signId).append("nodeId", nodeId).append("voteAmount", voteAmount)
				.append("decideType", decideType).append("voteType", voteType).append("actDeployId", actDefId).toString();
	}

	public Object clone() {
		BpmNodeSign obj = null;
		try {
			obj = (BpmNodeSign) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long getId() {
		return signId;
	}

	@Override
	public void setId(Long id) {
		signId = id;
	}

}