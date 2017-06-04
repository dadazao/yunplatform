package com.cloudstong.platform.resource.form.model;

import java.io.Serializable;
import java.util.List;

import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.resource.template.model.Template;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选项卡
 */
public class Tab implements Serializable,Cloneable{

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 选项卡名称
	 */
	private String tabName;
	
	/**
	 * 选项卡绑定的表
	 */
	private Long tableId;

	/**
	 * 选项卡模板类型（表单模板或组合模板）
	 */
	private Integer type;

	/**
	 * 模板类型
	 */
	private Integer templateType;

	/**
	 * 模板ID
	 */
	private Long templateId;
	
	/**
	 *模板名称 
	 */
	private String templateName;

	/**
	 * 模板文件名称
	 */
	private String templateFileName;

	/**
	 * 选项卡字体样式
	 */
	private String tabFontStyle;

	/**
	 * 选项卡字体颜色
	 */
	private String tabFontColor;

	/**
	 * 选项卡顺序
	 */
	private Integer tabOrder;
	
	/**
	 * 选项卡类型
	 */
	private Integer tabType;
	
	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 按钮或按钮组ID
	 */
	private String buttonId;

	/**
	 * 按钮组
	 */
	private ButtonGroup buttonGroup;

	/**
	 * 是否包含列表
	 */
	private Integer isIncludeList = 0;

	/**
	 * 表单ID
	 */
	private Long formId;
	
	/**
	 * tab页内的所有字段
	 */
	private List<FormColumnExtend> formColumnExtends;

	/**模板
	 * 
	 */
	private Template template;

	/**
	 * 按钮列表
	 */
	private List<FormButton> formButtons;

	/**
	 * 次表数据列表
	 */
	private List<Domain> domains;

	/**
	 * 所有分区
	 */
	private List<Partition> partitions;

	private String code;
	
	/**
	 * 是否有权限 0:有权限 1：没权限
	 */
	private String hasAuth="0";
	
	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

	public List<Partition> getPartitions() {
		return partitions;
	}

	public void setPartitions(List<Partition> partitions) {
		this.partitions = partitions;
	}

	public String getTabFontStyle() {
		return tabFontStyle;
	}

	public void setTabFontStyle(String tabFontStyle) {
		this.tabFontStyle = tabFontStyle;
	}

	public String getTabFontColor() {
		return tabFontColor;
	}

	public void setTabFontColor(String tabFontColor) {
		this.tabFontColor = tabFontColor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public List<FormColumnExtend> getFormColumnExtends() {
		return formColumnExtends;
	}

	public void setFormColumnExtends(List<FormColumnExtend> formColumnExtends) {
		this.formColumnExtends = formColumnExtends;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public Integer getTabOrder() {
		return tabOrder;
	}

	public void setTabOrder(Integer tabOrder) {
		this.tabOrder = tabOrder;
	}

	public Integer getTabType() {
		return tabType;
	}

	public void setTabType(Integer tabType) {
		this.tabType = tabType;
	}

	public Integer getIsIncludeList() {
		return isIncludeList;
	}

	public void setIsIncludeList(Integer isIncludeList) {
		this.isIncludeList = isIncludeList;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public void setButtonGroup(ButtonGroup buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public List<FormButton> getFormButtons() {
		return formButtons;
	}

	public void setFormButtons(List<FormButton> formButtons) {
		this.formButtons = formButtons;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(String hasAuth) {
		this.hasAuth = hasAuth;
	}

	public Tab clone() throws CloneNotSupportedException {
		Tab o = null;
		try {
			o = (Tab) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
}
