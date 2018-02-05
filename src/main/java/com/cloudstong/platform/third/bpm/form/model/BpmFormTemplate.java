package com.cloudstong.platform.third.bpm.form.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

public class BpmFormTemplate extends EntityBase {
	public static final String MainTable = "main";
	public static final String SubTable = "subTable";
	public static final String Macro = "macro";
	public static final String List = "list";
	public static final String Detail = "detail";
	public static final String TableManage = "tableManage";
	protected Long templateId;
	protected String templateName;
	protected String alias;
	protected String templateType;
	protected String macroTemplateAlias;
	protected String html;
	protected String templateDesc;
	protected int canEdit;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getMacroTemplateAlias() {
		return macroTemplateAlias;
	}

	public void setMacroTemplateAlias(String macroTemplateAlias) {
		this.macroTemplateAlias = macroTemplateAlias;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getHtml() {
		return html;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}

	public String getTemplateDesc() {
		return templateDesc;
	}

	public boolean equals(Object object) {
		if (!(object instanceof BpmFormTemplate)) {
			return false;
		}
		BpmFormTemplate rhs = (BpmFormTemplate) object;
		return new EqualsBuilder().append(templateId, rhs.templateId).append(templateName, rhs.templateName).append(templateType, rhs.templateType)
				.append(macroTemplateAlias, rhs.macroTemplateAlias).append(html, rhs.html).append(templateDesc, rhs.templateDesc).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(templateId).append(templateName).append(templateType).append(macroTemplateAlias)
				.append(html).append(templateDesc).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("templateId", templateId).append("templateName", templateName).append("templateType", templateType)
				.append("macroTemplateId", macroTemplateAlias).append("html", html).append("templateDesc", templateDesc).toString();
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub need to be implemented.
		
	}
}