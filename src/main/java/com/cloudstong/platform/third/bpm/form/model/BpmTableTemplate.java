package com.cloudstong.platform.third.bpm.form.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

@XmlRootElement(name = "bpmTableTemplate")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmTableTemplate extends EntityBase {
	public static final int AUTHOR_TYPE_ALL = 1;
	public static final int AUTHOR_TYPE_SELF = 2;
	public static final int AUTHOR_TYPE_UNDER = 3;
	public static final int AUTHOR_TYPE_ORG = 4;

	@XmlAttribute
	protected Long id = Long.valueOf(0L);

	@XmlAttribute
	protected Long tableId;

	@XmlAttribute
	protected Long categoryId;

	@XmlAttribute
	protected String htmlList;

	@XmlAttribute
	protected String htmlDetail;

	@XmlAttribute
	protected String templateName;

	@XmlAttribute
	protected String tableName = "";

	@XmlAttribute
	protected String categoryName = "";

	@XmlAttribute
	protected int authorType;

	@XmlAttribute
	protected Long formKey = Long.valueOf(0L);

	@XmlAttribute
	protected String memo = "";

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setHtmlList(String htmlList) {
		this.htmlList = htmlList;
	}

	public String getHtmlList() {
		return htmlList;
	}

	public void setHtmlDetail(String htmlDetail) {
		this.htmlDetail = htmlDetail;
	}

	public String getHtmlDetail() {
		return htmlDetail;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getAuthorType() {
		return authorType;
	}

	public void setAuthorType(int authorType) {
		this.authorType = authorType;
	}

	public Long getFormKey() {
		return formKey;
	}

	public void setFormKey(Long formKey) {
		this.formKey = formKey;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmTableTemplate)) {
			return false;
		}
		BpmTableTemplate rhs = (BpmTableTemplate) object;
		return new EqualsBuilder().append(id, rhs.id).append(tableId, rhs.tableId).append(categoryId, rhs.categoryId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(id).append(tableId).append(categoryId).append(templateName).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("tableId", tableId).append("categoryId", categoryId)
				.append("templateName", templateName).toString();
	}
}