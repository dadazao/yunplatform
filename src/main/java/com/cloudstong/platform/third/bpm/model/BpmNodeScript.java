package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmNodeScript")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmNodeScript extends EntityBase implements Cloneable {
	public static final String TABLE_NAME = "BPM_NODE_SCRIPT";
	public static final Integer SCRIPT_TYPE_1 = Integer.valueOf(1);

	public static final Integer SCRIPT_TYPE_2 = Integer.valueOf(2);

	public static final Integer SCRIPT_TYPE_3 = Integer.valueOf(3);

	public static final Integer SCRIPT_TYPE_4 = Integer.valueOf(4);

	@XmlAttribute
	protected Long id;

	@XmlAttribute
	protected String memo;

	@XmlAttribute
	protected String nodeId;

	@XmlAttribute
	protected String actDefId;

	@XmlAttribute
	protected String script;

	@XmlAttribute
	protected Integer scriptType;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return memo;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public void setScriptType(Integer scriptType) {
		this.scriptType = scriptType;
	}

	public Integer getScriptType() {
		return scriptType;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeScript)) {
			return false;
		}
		BpmNodeScript rhs = (BpmNodeScript) object;
		return new EqualsBuilder().append(id, rhs.id).append(memo, rhs.memo).append(nodeId, rhs.nodeId).append(actDefId, rhs.actDefId)
				.append(script, rhs.script).append(scriptType, rhs.scriptType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(memo).append(nodeId).append(actDefId).append(script).append(scriptType)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("memo", memo).append("nodeId", nodeId).append("actDefId", actDefId)
				.append("script", script).append("scriptType", scriptType).toString();
	}

	public Object clone() {
		BpmNodeScript obj = null;
		try {
			obj = (BpmNodeScript) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}