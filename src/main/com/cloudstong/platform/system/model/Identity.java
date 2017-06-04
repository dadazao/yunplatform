package com.cloudstong.platform.system.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

@XmlRootElement(name = "identity")
@XmlAccessorType(XmlAccessType.NONE)
public class Identity extends EntityBase {

	@XmlAttribute
	protected Long id = Long.valueOf(0L);

	@XmlAttribute
	protected String name;

	@XmlAttribute
	protected String alias;

	@XmlAttribute
	protected String rule;

	@XmlAttribute
	protected Short genEveryDay = Short.valueOf((short) 1);

	@XmlAttribute
	protected Integer noLength;

	@XmlAttribute
	protected Integer initValue;

	@XmlAttribute
	protected Integer curValue;
	protected String curDate = "";

	@XmlAttribute
	protected Short step = Short.valueOf((short) 1);

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

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRule() {
		return rule;
	}

	public void setGenEveryDay(Short genEveryDay) {
		this.genEveryDay = genEveryDay;
	}

	public Short getGenEveryDay() {
		return genEveryDay;
	}

	public void setNoLength(Integer noLength) {
		this.noLength = noLength;
	}

	public Integer getNoLength() {
		return noLength;
	}

	public void setInitValue(Integer initValue) {
		this.initValue = initValue;
	}

	public Integer getInitValue() {
		return initValue;
	}

	public void setCurValue(Integer curValue) {
		this.curValue = curValue;
	}

	public Integer getCurValue() {
		return curValue;
	}

	public Short getStep() {
		return step;
	}

	public void setStep(Short step) {
		this.step = step;
	}

	public String getCurDate() {
		return curDate;
	}

	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Identity)) {
			return false;
		}
		Identity rhs = (Identity) object;
		return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(alias, rhs.alias).append(rule, rhs.rule)
				.append(genEveryDay, rhs.genEveryDay).append(noLength, rhs.noLength).append(initValue, rhs.initValue).append(curValue, rhs.curValue)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(name).append(alias).append(rule).append(genEveryDay).append(noLength)
				.append(initValue).append(curValue).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).append("alias", alias).append("rule", rule)
				.append("genEveryDay", genEveryDay).append("noLength", noLength).append("initValue", initValue).append("curValue", curValue)
				.toString();
	}
}