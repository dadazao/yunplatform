package com.cloudstong.platform.third.bpm.form.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

public class BpmFormRule extends EntityBase {
	protected Long id;
	protected String name;
	protected String rule;
	protected String memo;
	protected String tipInfo;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRule() {
		return rule;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return memo;
	}

	public void setTipInfo(String tipInfo) {
		this.tipInfo = tipInfo;
	}

	public String getTipInfo() {
		return tipInfo;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormRule)) {
			return false;
		}
		BpmFormRule rhs = (BpmFormRule) object;
		return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(rule, rhs.rule).append(memo, rhs.memo)
				.append(tipInfo, rhs.tipInfo).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(name).append(rule).append(memo).append(tipInfo).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).append("rule", rule).append("memo", memo).append("tipInfo", tipInfo)
				.toString();
	}
}