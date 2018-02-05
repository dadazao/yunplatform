package com.cloudstong.platform.resource.template.model;

import com.cloudstong.platform.core.model.EntityBase;

public class Template extends EntityBase {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 模板英文名称
	 */
	private String templateName;

	/**
	 * 模板中文名称
	 */
	private String templateChName;

	/**
	 * 模板类型
	 */
	private String templateType;

	/**
	 * 模板文件存放地址
	 */
	private String templatePath;

	/**
	 * 是否启用开发环境,0:不启用 1:启用
	 */
	private Integer isDevelopPath = 0;

	/**
	 * 开发环境路径
	 */
	private String templateDevelopPath;

	/**
	 * 模板文件名称
	 */
	private String templateFileName;

	/**
	 * 预览图片名称
	 */
	private String picFileName;

	/**
	 * 功能说明
	 */
	private String comment;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 类型,0:基础模板 1:组合模板
	 */
	private String type;

	/**
	 * 0:模版文件上传 1:利用模版元素
	 */
	private String designType;

	/**
	 * 模板内容
	 */
	private String content;

	/**
	 * 模板列数
	 */
	private int tds;
	
	/**
	 * 模板行数
	 */
	private int trs;
	
	/**
	 * 模板用途
	 */
	private Integer mobanyongtu;
	
	public Template() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateChName() {
		return templateChName;
	}

	public void setTemplateChName(String templateChName) {
		this.templateChName = templateChName;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTemplateDevelopPath() {
		return templateDevelopPath;
	}

	public void setTemplateDevelopPath(String templateDevelopPath) {
		this.templateDevelopPath = templateDevelopPath;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIsDevelopPath() {
		return isDevelopPath;
	}

	public void setIsDevelopPath(Integer isDevelopPath) {
		this.isDevelopPath = isDevelopPath;
	}

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getTrs() {
		return trs;
	}

	public void setTrs(int trs) {
		this.trs = trs;
	}

	public int getTds() {
		return tds;
	}

	public void setTds(int tds) {
		this.tds = tds;
	}

	public Integer getMobanyongtu() {
		return mobanyongtu;
	}

	public void setMobanyongtu(Integer mobanyongtu) {
		this.mobanyongtu = mobanyongtu;
	}
}
