package com.cloudstong.platform.resource.uploadify.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:多文件上传组件
 */
public class Uploadify implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 组件名称
	 */
	private String uploadifyName;
	
	/**
	 * 按钮显示文字
	 */
	private String buttonText;
	
	/**
	 * 是否可以同时选择多个
	 */
	private Integer multi;
	
	/**
	 * 是否自动上传
	 */
	private Integer autoUpload;
	
	/**
	 * 是否启用
	 */
	private Integer isEnabled;
	
	/**
	 * 文件格式
	 */
	private String fileExt;
	
	/**
	 * 文件大小限制
	 */
	private String fileSizeLimit;
	
	/**
	 * 最大文件个数
	 */
	private Integer queueSizeLimit;
	
	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String remark;

	public Uploadify() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUploadifyName() {
		return uploadifyName;
	}

	public void setUploadifyName(String uploadifyName) {
		this.uploadifyName = uploadifyName;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public Integer getMulti() {
		return multi;
	}

	public void setMulti(Integer multi) {
		this.multi = multi;
	}

	public Integer getAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(Integer autoUpload) {
		this.autoUpload = autoUpload;
	}

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}

	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}

	public Integer getQueueSizeLimit() {
		return queueSizeLimit;
	}

	public void setQueueSizeLimit(Integer queueSizeLimit) {
		this.queueSizeLimit = queueSizeLimit;
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
	
}
