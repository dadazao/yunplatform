package com.cloudstong.platform.resource.textarea.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本域
 */
public class Textarea extends Component implements Serializable {

	private static final long serialVersionUID = 5940232085908155841L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 文本域名称
	 */
	private String textareaName = "";

	/**
	 * 宽度
	 */
	private Long textareaWidth = 135L;

	/**
	 * 高度
	 */
	private Long textareaHeight = 21L;

	/**
	 * 是否启用
	 */
	private Long textareaEnabled = 1L;

	/**
	 * 是否启用代码的显示名称
	 */
	private String textareaEnabledName = "";

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
	private String textareaRemarks = "";

	/**
	 * 编码
	 */
	private String textareaCode;
	
	public Textarea() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTextareaName() {
		return textareaName;
	}

	public void setTextareaName(String textareaName) {
		this.textareaName = textareaName;
	}

	public Long getTextareaWidth() {
		return textareaWidth;
	}

	public void setTextareaWidth(Long textareaWidth) {
		this.textareaWidth = textareaWidth;
	}

	public Long getTextareaHeight() {
		return textareaHeight;
	}

	public void setTextareaHeight(Long textareaHeight) {
		this.textareaHeight = textareaHeight;
	}

	public Long getTextareaEnabled() {
		return textareaEnabled;
	}

	public void setTextareaEnabled(Long textareaEnabled) {
		this.textareaEnabled = textareaEnabled;
	}

	public String getTextareaEnabledName() {
		return textareaEnabledName;
	}

	public void setTextareaEnabledName(String textareaEnabledName) {
		this.textareaEnabledName = textareaEnabledName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getTextareaRemarks() {
		return textareaRemarks;
	}

	public void setTextareaRemarks(String textareaRemarks) {
		this.textareaRemarks = textareaRemarks;
	}

	public String getTextareaCode() {
		return textareaCode;
	}

	public void setTextareaCode(String textareaCode) {
		this.textareaCode = textareaCode;
	}

}
