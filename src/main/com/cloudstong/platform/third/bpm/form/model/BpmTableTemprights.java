package com.cloudstong.platform.third.bpm.form.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

public class BpmTableTemprights extends EntityBase {
	public static final int RIGHT_TYPE_USER = 1;
	public static final int RIGHT_TYPE_ROLE = 2;
	public static final int RIGHT_TYPE_ORG = 3;
	public static final Short SEARCH_TYPE_DEF = Short.valueOf((short) 0);
	public static final Short SEARCH_TYPE_GLT = Short.valueOf((short) 1);
	protected Long id;
	protected Long templateId;
	protected Short rightType;
	protected Long ownerId;
	protected String ownerName;
	protected Short searchType;
	protected Long categoryId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setRightType(Short rightType) {
		this.rightType = rightType;
	}

	public Short getRightType() {
		return rightType;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setSearchType(Short searchType) {
		this.searchType = searchType;
	}

	public Short getSearchType() {
		return searchType;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmTableTemprights)) {
			return false;
		}
		BpmTableTemprights rhs = (BpmTableTemprights) object;
		return new EqualsBuilder().append(id, rhs.id).append(templateId, rhs.templateId).append(rightType, rhs.rightType)
				.append(ownerId, rhs.ownerId).append(ownerName, rhs.ownerName).append(searchType, rhs.searchType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(templateId).append(rightType).append(ownerId).append(ownerName)
				.append(searchType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("templateId", templateId).append("rightType", rightType).append("ownerId", ownerId)
				.append("ownerName", ownerName).append("searchType", searchType).toString();
	}
}