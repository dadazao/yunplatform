package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmDefVar")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmDefVar extends EntityBase implements Cloneable {
	public static final String TABLE_NAME = "BPM_DEF_VARS";

	@XmlAttribute
	protected Long varId;

	@XmlAttribute
	protected Long defId;

	@XmlAttribute
	protected String varName;

	@XmlAttribute
	protected String varKey;

	@XmlAttribute
	protected String varDataType;

	@XmlAttribute
	protected String defValue;

	@XmlAttribute
	protected String nodeName;

	@XmlAttribute
	protected Long versionId;

	@XmlAttribute
	protected Long actDeployId;

	@XmlAttribute
	protected String nodeId;

	@XmlAttribute
	protected String varScope;

	public void setVarId(Long varId) {
		this.varId = varId;
	}

	public Long getVarId() {
		return varId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Long getDefId() {
		return defId;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarKey(String varKey) {
		this.varKey = varKey;
	}

	public String getVarKey() {
		return varKey;
	}

	public void setVarDataType(String varDataType) {
		this.varDataType = varDataType;
	}

	public String getVarDataType() {
		return varDataType;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setActDeployId(Long actDeployId) {
		this.actDeployId = actDeployId;
	}

	public Long getActDeployId() {
		return actDeployId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setVarScope(String varScope) {
		this.varScope = varScope;
	}

	public String getVarScope() {
		return varScope;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmDefVar)) {
			return false;
		}
		BpmDefVar rhs = (BpmDefVar) object;
		return new EqualsBuilder().append(varId, rhs.varId).append(defId, rhs.defId).append(varName, rhs.varName).append(varKey, rhs.varKey)
				.append(varDataType, rhs.varDataType).append(defValue, rhs.defValue).append(nodeName, rhs.nodeName).append(versionId, rhs.versionId)
				.append(actDeployId, rhs.actDeployId).append(nodeId, rhs.nodeId).append(varScope, rhs.varScope).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(varId).append(defId).append(varName).append(varKey).append(varDataType)
				.append(defValue).append(nodeName).append(versionId).append(actDeployId).append(nodeId).append(varScope).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("varId", varId).append("defId", defId).append("varName", varName).append("varKey", varKey)
				.append("varDataType", varDataType).append("defValue", defValue).append("nodeName", nodeName).append("versionId", versionId)
				.append("actDeployId", actDeployId).append("nodeId", nodeId).append("varScope", varScope).toString();
	}

	public Object clone() {
		BpmDefVar obj = null;
		try {
			obj = (BpmDefVar) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long getId() {
		return varId;
	}

	@Override
	public void setId(Long id) {
		varId = id;
	}
	
	
}