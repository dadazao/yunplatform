package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmNodeUserUplow")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeUserUplow extends EntityBase {
	public static Map<String, Short> UPLOWTYPE_MAP = new HashMap();

	@XmlAttribute
	protected Long id;

	@XmlAttribute
	protected Long nodeUserId;

	@XmlAttribute
	protected Long demensionId;

	@XmlAttribute
	protected String demensionName;

	@XmlAttribute
	protected Integer upLowLevel;

	@XmlAttribute
	protected Short upLowType;

	static {
		UPLOWTYPE_MAP.put("上级", Short.valueOf((short) 1));
		UPLOWTYPE_MAP.put("下级", Short.valueOf((short) -1));
		UPLOWTYPE_MAP.put("同级", Short.valueOf((short) 0));
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setNodeUserId(Long nodeUserId) {
		this.nodeUserId = nodeUserId;
	}

	public Long getNodeUserId() {
		return nodeUserId;
	}

	public void setDemensionId(Long demensionId) {
		this.demensionId = demensionId;
	}

	public Long getDemensionId() {
		return demensionId;
	}

	public void setDemensionName(String demensionName) {
		this.demensionName = demensionName;
	}

	public String getDemensionName() {
		return demensionName;
	}

	public void setUpLowLevel(Integer upLowLevel) {
		this.upLowLevel = upLowLevel;
	}

	public Integer getUpLowLevel() {
		return upLowLevel;
	}

	public void setUpLowType(Short upLowType) {
		this.upLowType = upLowType;
	}

	public Short getUpLowType() {
		return upLowType;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeUserUplow)) {
			return false;
		}
		BpmNodeUserUplow rhs = (BpmNodeUserUplow) object;
		return new EqualsBuilder().append(id, rhs.id).append(nodeUserId, rhs.nodeUserId).append(demensionId, rhs.demensionId)
				.append(demensionName, rhs.demensionName).append(upLowLevel, rhs.upLowLevel).append(upLowType, rhs.upLowType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(nodeUserId).append(demensionId).append(demensionName).append(upLowLevel)
				.append(upLowType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("nodeUserId", nodeUserId).append("demensionId", demensionId)
				.append("demensionName", demensionName).append("upLowLevel", upLowLevel).append("upLowType", upLowType).toString();
	}
}