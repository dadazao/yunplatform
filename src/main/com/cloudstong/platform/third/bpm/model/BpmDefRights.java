package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "bpmDefRights")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmDefRights extends EntityBase implements Cloneable {
	public static final short RIGHT_TYPE_USER = 1;
	public static final short RIGHT_TYPE_ROLE = 2;
	public static final short RIGHT_TYPE_ORG = 3;
	public static final short SEARCH_TYPE_DEF = 0;
	public static final short SEARCH_TYPE_GLT = 1;

	@XmlAttribute
	protected Long id;

	@XmlAttribute
	protected String defKey;

	@XmlAttribute
	protected Long flowTypeId;

	@XmlAttribute
	protected Short rightType;

	@XmlAttribute
	protected Long ownerId;

	@XmlAttribute
	protected String ownerName;

	@XmlAttribute
	protected Short searchType;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}

	public String getDdefKey() {
		return defKey;
	}

	public void setFlowTypeId(Long flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public Long getFlowTypeId() {
		return flowTypeId;
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

	public boolean equals(Object object) {
		if (!(object instanceof BpmDefRights)) {
			return false;
		}
		BpmDefRights rhs = (BpmDefRights) object;
		return new EqualsBuilder().append(id, rhs.id).append(defKey, rhs.defKey).append(flowTypeId, rhs.flowTypeId).append(rightType, rhs.rightType)
				.append(ownerId, rhs.ownerId).append(ownerName, rhs.ownerName).append(searchType, rhs.searchType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(defKey).append(flowTypeId).append(rightType).append(ownerId)
				.append(ownerName).append(searchType).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("defId", defKey).append("flowTypeId", flowTypeId).append("rightType", rightType)
				.append("ownerId", ownerId).append("ownerName", ownerName).append("searchType", searchType).toString();
	}

	public Object clone() {
		BpmDefRights obj = null;
		try {
			obj = (BpmDefRights) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}