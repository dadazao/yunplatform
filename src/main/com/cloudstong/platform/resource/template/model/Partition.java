package com.cloudstong.platform.resource.template.model;

import java.io.Serializable;
import java.util.List;

import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分区
 */
public class Partition implements Serializable{
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 分区名称
	 */
	private String partitionName;
	/**
	 * 分区类型
	 */
	private Integer partitionType;
	/**
	 * 基础模板
	 */
	private Long baseTemplate;
	/**
	 * 基础模板名称
	 */
	private String baseTemplateName;

	/**
	 * 模板文件名称
	 */
	private String templateFileName;

	/**
	 * 顺序
	 */
	private Integer showOrder;

	/**
	 * 主记录id
	 */
	private String templateId;

	/**
	 * 所属选项卡id
	 */
	private Long tabId;

	/**
	 * 操作区表单元素
	 */
	private List<FormColumnExtend> formColumnExtends;
	/**
	 * 列表表头
	 */
	private List<FormColumnExtend> tabulationColumnExtends;
	/**
	 * 列表区
	 */
	private List<Domain> domains;

	/**
	 * 分区按钮
	 */
	private List<FormButton> formButtons;

	/**
	 * 分区对应的模板
	 */
	private Template template;

	public Partition() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

	public Integer getPartitionType() {
		return partitionType;
	}

	public void setPartitionType(Integer partitionType) {
		this.partitionType = partitionType;
	}

	public Long getBaseTemplate() {
		return baseTemplate;
	}

	public void setBaseTemplate(Long baseTemplate) {
		this.baseTemplate = baseTemplate;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getBaseTemplateName() {
		return baseTemplateName;
	}

	public void setBaseTemplateName(String baseTemplateName) {
		this.baseTemplateName = baseTemplateName;
	}

	public List<FormColumnExtend> getFormColumnExtends() {
		return formColumnExtends;
	}

	public void setFormColumnExtends(List<FormColumnExtend> formColumnExtends) {
		this.formColumnExtends = formColumnExtends;
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

	public List<FormButton> getFormButtons() {
		return formButtons;
	}

	public void setFormButtons(List<FormButton> formButtons) {
		this.formButtons = formButtons;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public List<FormColumnExtend> getTabulationColumnExtends() {
		return tabulationColumnExtends;
	}

	public void setTabulationColumnExtends(
			List<FormColumnExtend> tabulationColumnExtends) {
		this.tabulationColumnExtends = tabulationColumnExtends;
	}

	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

}
