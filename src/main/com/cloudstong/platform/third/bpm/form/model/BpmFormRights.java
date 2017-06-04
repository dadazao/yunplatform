package com.cloudstong.platform.third.bpm.form.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

@XmlRootElement(name = "bpmFormRights")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormRights extends EntityBase implements Cloneable {
	public static final short FieldRights = 1;
	public static final short TableRights = 2;
	public static final short OpinionRights = 3;

	@XmlAttribute
	protected Long id;

	@XmlAttribute
	protected Long formDefId;

	@XmlAttribute
	protected String name;

	@XmlAttribute
	protected String permission = "";

	@XmlAttribute
	protected short type = 1;

	@XmlAttribute
	protected String actDefId = "";

	@XmlAttribute
	protected String nodeId = "";

	public BpmFormRights() {
	}

	public BpmFormRights(Long id, Long formDefId, String name, String permission, short type) {
		this.id = id;
		this.formDefId = formDefId;
		this.name = name;
		this.permission = permission;
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public Long getFormDefId() {
		return formDefId;
	}

	public void setName(String fieldName) {
		name = fieldName;
	}

	public String getName() {
		return name;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormRights)) {
			return false;
		}
		BpmFormRights rhs = (BpmFormRights) object;
		return new EqualsBuilder().append(id, rhs.id).append(formDefId, rhs.formDefId).append(name, rhs.name).append(permission, rhs.permission)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(formDefId).append(name).append(permission).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("formDefId", formDefId).append("fieldName", name).append("permission", permission)
				.toString();
	}

	public Object clone() {
		BpmFormRights obj = null;
		try {
			obj = (BpmFormRights) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}