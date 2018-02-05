package com.cloudstong.platform.third.bpm.form.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

@XmlRootElement(name = "bpmFormDef")
@XmlAccessorType(XmlAccessType.NONE)
public class BpmFormDef extends EntityBase implements Cloneable {
	public static final String EditInline = "edit";
	public static final String EditForm = "form";
	public static final int DesignType_FromTable = 0;
	public static final int DesignType_CustomDesign = 1;
	public static final String EditWindow = "window";
	public static String PageSplitor = "#page#";

	public static final Short IS_NOT_DEFAULT = Short.valueOf((short) 0);

	public static final Short IS_DEFAULT = Short.valueOf((short) 1);

	public static final Short IS_NOT_PUBLISHED = Short.valueOf((short) 0);

	public static final Short IS_PUBLISHED = Short.valueOf((short) 1);

	@XmlAttribute
	protected Long formDefId = Long.valueOf(0L);

	@XmlAttribute
	protected Long formKey = Long.valueOf(0L);

	@XmlAttribute
	protected Long categoryId = Long.valueOf(0L);

	protected String categoryName = "";

	@XmlAttribute
	protected String subject = "";

	@XmlAttribute
	protected String formDesc = "";

	protected String tabTitle = "";

	@XmlAttribute
	protected String html;

	@XmlAttribute
	protected String template;

	@XmlAttribute
	protected Short isDefault = Short.valueOf((short) 0);

	@XmlAttribute
	protected Long tableId = Long.valueOf(0L);

	@XmlAttribute
	protected Integer versionNo;
	protected Short isPublished = Short.valueOf((short) 0);
	protected String publishedBy;
	protected Date publishTime;
	protected String tableName = "";

	@XmlAttribute
	protected int designType = 0;

	protected Short isMain = Short.valueOf((short) 0);
	protected String templatesId;

	public void setFormDefId(Long formDefId) {
		this.formDefId = formDefId;
	}

	public Long getFormDefId() {
		return formDefId;
	}

	public void setFormKey(Long formKey) {
		this.formKey = formKey;
	}

	public Long getFormKey() {
		return formKey;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public String getFormDesc() {
		return formDesc;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getHtml() {
		return html;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setIsPublished(Short isPublished) {
		this.isPublished = isPublished;
	}

	public Short getIsPublished() {
		return isPublished;
	}

	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	public String getPublishedBy() {
		return publishedBy;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getDesignType() {
		return designType;
	}

	public void setDesignType(int designType) {
		this.designType = designType;
	}

	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}

	public Short getIsMain() {
		return isMain;
	}

	public String getTemplatesId() {
		return templatesId;
	}

	public void setTemplatesId(String templatesId) {
		this.templatesId = templatesId;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormDef)) {
			return false;
		}
		BpmFormDef rhs = (BpmFormDef) object;
		return new EqualsBuilder().append(formDefId, rhs.formDefId).append(formKey, rhs.formKey).append(subject, rhs.subject)
				.append(formDesc, rhs.formDesc).append(html, rhs.html).append(template, rhs.template).append(isDefault, rhs.isDefault)
				.append(tableId, rhs.tableId).append(versionNo, rhs.versionNo).append(isPublished, rhs.isPublished)
				.append(publishedBy, rhs.publishedBy).append(publishTime, rhs.publishTime).append(templatesId, rhs.templatesId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(formDefId).append(formKey).append(subject).append(formDesc).append(html)
				.append(template).append(isDefault).append(tableId).append(versionNo).append(isPublished).append(publishedBy).append(publishTime)
				.append(templatesId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("formDefId", formDefId).append("formKey", formKey).append("subject", subject)
				.append("formDesc", formDesc).append("html", html).append("template", template).append("isDefault", isDefault)
				.append("tableId", tableId).append("versionNo", versionNo).append("isPublished", isPublished).append("publishedBy", publishedBy)
				.append("publishTime", publishTime).append("templatesId", templatesId).toString();
	}

	public Object clone() {
		BpmFormDef obj = null;
		try {
			obj = (BpmFormDef) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Long getId() {
		return formDefId;
	}

	@Override
	public void setId(Long id) {
		formDefId = id;
	}
	
}