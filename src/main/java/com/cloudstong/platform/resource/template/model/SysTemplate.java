package com.cloudstong.platform.resource.template.model;

import java.util.Date;

import com.cloudstong.platform.core.model.EntityBase;

public class SysTemplate extends EntityBase {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	/**
	 * 模板中文名称
	 */
	private String tblTemplatechname;
	/**
	 * 模板类型
	 */
	private String tblTemplatetype;
	/**
	 * 模板文件名称
	 */
	private String tblTemplatefilename;
	/**
	 * 预览图片名称
	 */
	private String tblPicfilename;
	/**
	 * 类型,0:基础模板 1:组合模板
	 */
	private String tblType;
	/**
	 * 0:模版文件上传 1:利用模版元素
	 */
	private int tblDesignType;
	/**
	 * 模板内容
	 */
	private String tblContent;
	/**
	 * 功能说明
	 */
	private String tblComment;
	/**
	 * 备注
	 */
	private String tblRemark;
	/**
	 * 模板列数
	 */
	private String tblTds="0";
	/**
	 * 模板用途
	 */
	private String tblMobanyongtu;
	/**
	 * 模板行数
	 */
	private String tblTrs="0";
	/**
	 * 系统元素
	 */
	private String tblSystemteam;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTblTemplatechname() {
		return tblTemplatechname;
	}
	public void setTblTemplatechname(String tblTemplatechname) {
		this.tblTemplatechname = tblTemplatechname;
	}
	public String getTblTemplatetype() {
		return tblTemplatetype;
	}
	public void setTblTemplatetype(String tblTemplatetype) {
		this.tblTemplatetype = tblTemplatetype;
	}
	public String getTblTemplatefilename() {
		return tblTemplatefilename;
	}
	public void setTblTemplatefilename(String tblTemplatefilename) {
		this.tblTemplatefilename = tblTemplatefilename;
	}
	public String getTblPicfilename() {
		return tblPicfilename;
	}
	public void setTblPicfilename(String tblPicfilename) {
		this.tblPicfilename = tblPicfilename;
	}
	public String getTblType() {
		return tblType;
	}
	public void setTblType(String tblType) {
		this.tblType = tblType;
	}
	public int getTblDesignType() {
		return tblDesignType;
	}
	public void setTblDesignType(int tblDesignType) {
		this.tblDesignType = tblDesignType;
	}
	public String getTblContent() {
		return tblContent;
	}
	public void setTblContent(String tblContent) {
		this.tblContent = tblContent;
	}
	public String getTblComment() {
		return tblComment;
	}
	public void setTblComment(String tblComment) {
		this.tblComment = tblComment;
	}
	public String getTblRemark() {
		return tblRemark;
	}
	public void setTblRemark(String tblRemark) {
		this.tblRemark = tblRemark;
	}
	public String getTblTds() {
		return tblTds;
	}
	public void setTblTds(String tblTds) {
		this.tblTds = tblTds;
	}
	public String getTblMobanyongtu() {
		return tblMobanyongtu;
	}
	public void setTblMobanyongtu(String tblMobanyongtu) {
		this.tblMobanyongtu = tblMobanyongtu;
	}
	public String getTblTrs() {
		return tblTrs;
	}
	public void setTblTrs(String tblTrs) {
		this.tblTrs = tblTrs;
	}
	public String getTblSystemteam() {
		return tblSystemteam;
	}
	public void setTblSystemteam(String tblSystemteam) {
		this.tblSystemteam = tblSystemteam;
	}
	
}

