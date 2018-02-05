package com.cloudstong.platform.resource.textbox.model;

import java.io.Serializable;
import java.util.List;

import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本框
 */
public class TextBox extends Component implements Serializable {

	private static final long serialVersionUID = -6838099082622669911L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 文本框名称
	 */
	private String textBoxName = "";

	/**
	 * 文本框宽度
	 */
	private Long textBoxWidth = 135L;

	/**
	 * 文本框高度
	 */
	private Long textBoxHeight = 21L;

	/**
	 * 是否启用
	 */
	private Long textBoxEnabled = 1L;

	/**
	 * 是否启用代码的显示名称
	 */
	private String textBoxEnabledName = "";

	/**
	 * 删除标识  0：未删除  1：已删除
	 */
	private Integer status;

	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String textBoxRemarks = "";

	/**
	 * 编码
	 */
	private String textBoxCode;

	/**
	 * 表单字段信息列表
	 */
	private List<FormColumnExtend> formColumnExtends; 

	public String getTextBoxEnabledName() {
		return textBoxEnabledName;
	}

	public void setTextBoxEnabledName(String textBoxEnabledName) {
		this.textBoxEnabledName = textBoxEnabledName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTextBoxRemarks() {
		return textBoxRemarks;
	}

	public void setTextBoxRemarks(String textBoxRemarks) {
		this.textBoxRemarks = textBoxRemarks;
	}

	public String getTextBoxName() {
		return textBoxName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTextBoxName(String textBoxName) {
		this.textBoxName = textBoxName;
	}

	public Long getTextBoxWidth() {
		return textBoxWidth;
	}

	public void setTextBoxWidth(Long textBoxWidth) {
		this.textBoxWidth = textBoxWidth;
	}

	public Long getTextBoxHeight() {
		return textBoxHeight;
	}

	public void setTextBoxHeight(Long textBoxHeight) {
		this.textBoxHeight = textBoxHeight;
	}

	public Long getTextBoxEnabled() {
		return textBoxEnabled;
	}

	public void setTextBoxEnabled(Long textBoxEnabled) {
		this.textBoxEnabled = textBoxEnabled;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTextBoxCode() {
		return textBoxCode;
	}

	public void setTextBoxCode(String textBoxCode) {
		this.textBoxCode = textBoxCode;
	}

	public List<FormColumnExtend> getFormColumnExtends() {
		return formColumnExtends;
	}

	public void setFormColumnExtends(List<FormColumnExtend> formColumnExtends) {
		this.formColumnExtends = formColumnExtends;
	}

}
