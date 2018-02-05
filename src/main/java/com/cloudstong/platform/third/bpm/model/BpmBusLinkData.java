package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmBusLinkData extends EntityBase {
	protected Long dataId;
	protected String tableName;
	protected String pkName;
	protected Long runId;
	protected String pkValue;
	protected String actDefId;
	protected Long userId = Long.valueOf(0L);

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmBusLinkData)) {
			return false;
		}
		BpmBusLinkData rhs = (BpmBusLinkData) object;
		return new EqualsBuilder().append(dataId, rhs.dataId).append(tableName, rhs.tableName).append(pkName, rhs.pkName).append(runId, rhs.runId)
				.append(pkValue, rhs.pkValue).append(actDefId, rhs.actDefId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(dataId).append(tableName).append(pkName).append(runId).append(pkValue)
				.append(actDefId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("dataId", dataId).append("tableName", tableName).append("pkName", pkName).append("runId", runId)
				.append("pkValue", pkValue).append("actDefId", actDefId).toString();
	}

	@Override
	public Long getId() {
		return dataId;
	}

	@Override
	public void setId(Long id) {
		this.dataId = id;
	}
	
}