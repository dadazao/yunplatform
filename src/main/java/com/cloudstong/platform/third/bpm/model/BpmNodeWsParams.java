package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmNodeWsParams extends EntityBase {
	private static final long serialVersionUID = 1L;
	public static final Short INPUT_PARAMS = Short.valueOf((short) 1);

	public static final Short OUTPUT_PARAMS = Short.valueOf((short) 0);
	protected Long id;
	protected Long webserviceId;
	protected Short paraType;
	protected Long varId;
	protected String wsName;
	protected String type;
	protected String varName;

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setWebserviceId(Long webserviceId) {
		this.webserviceId = webserviceId;
	}

	public Long getWebserviceId() {
		return webserviceId;
	}

	public void setParaType(Short paraType) {
		this.paraType = paraType;
	}

	public Short getParaType() {
		return paraType;
	}

	public void setVarId(Long varId) {
		this.varId = varId;
	}

	public Long getVarId() {
		return varId;
	}

	public void setWsName(String wsName) {
		this.wsName = wsName;
	}

	public String getWsName() {
		return wsName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmNodeWsParams)) {
			return false;
		}
		BpmNodeWsParams rhs = (BpmNodeWsParams) object;
		return new EqualsBuilder().append(id, rhs.id).append(webserviceId, rhs.webserviceId).append(paraType, rhs.paraType).append(varId, rhs.varId)
				.append(wsName, rhs.wsName).append(type, rhs.type).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(webserviceId).append(paraType).append(varId).append(wsName).append(type)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("webserviceId", webserviceId).append("paraType", paraType).append("varId", varId)
				.append("wsName", wsName).append("type", type).toString();
	}
}