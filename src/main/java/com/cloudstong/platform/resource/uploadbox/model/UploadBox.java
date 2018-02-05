package com.cloudstong.platform.resource.uploadbox.model;

import java.io.Serializable;

import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:上传文件框
 */
public class UploadBox extends Component implements Serializable {

	private static final long serialVersionUID = -676957621001861722L;
	
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 上传文件框名称
	 */
	private String uploadBoxName = "";

	/**
	 * 上传文件框宽度
	 */
	private Long uploadBoxWidth = 135L;

	/**
	 * 上传文件框高度
	 */
	private Long uploadBoxHeight = 21L;

	/**
	 * 是否启用
	 */
	private Long uploadBoxEnabled = 1L;

	/**
	 * 是否启用代码的显示名称
	 */
	private String uploadBoxEnabledName = "";

	/**
	 * 删除标识  0：未删除  1:已删除
	 */
	private Integer status;

	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String uploadBoxRemarks = "";

	/**
	 * 编码
	 */
	private String uploadBoxCode;

	public UploadBox() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUploadBoxName() {
		return uploadBoxName;
	}

	public void setUploadBoxName(String uploadBoxName) {
		this.uploadBoxName = uploadBoxName;
	}

	public Long getUploadBoxWidth() {
		return uploadBoxWidth;
	}

	public void setUploadBoxWidth(Long uploadBoxWidth) {
		this.uploadBoxWidth = uploadBoxWidth;
	}

	public Long getUploadBoxHeight() {
		return uploadBoxHeight;
	}

	public void setUploadBoxHeight(Long uploadBoxHeight) {
		this.uploadBoxHeight = uploadBoxHeight;
	}

	public Long getUploadBoxEnabled() {
		return uploadBoxEnabled;
	}

	public void setUploadBoxEnabled(Long uploadBoxEnabled) {
		this.uploadBoxEnabled = uploadBoxEnabled;
	}

	public String getUploadBoxEnabledName() {
		return uploadBoxEnabledName;
	}

	public void setUploadBoxEnabledName(String uploadBoxEnabledName) {
		this.uploadBoxEnabledName = uploadBoxEnabledName;
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

	public String getUploadBoxRemarks() {
		return uploadBoxRemarks;
	}

	public void setUploadBoxRemarks(String uploadBoxRemarks) {
		this.uploadBoxRemarks = uploadBoxRemarks;
	}

	public String getUploadBoxCode() {
		return uploadBoxCode;
	}

	public void setUploadBoxCode(String uploadBoxCode) {
		this.uploadBoxCode = uploadBoxCode;
	}

}
