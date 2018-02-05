package com.cloudstong.platform.resource.passbox.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:密码框
 */
public class Passbox extends Component implements Serializable {

	private static final long serialVersionUID = -2783291097438064998L;
	
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 密码框名称
	 */
	private String passboxName = "";

	/**
	 * 宽度
	 */
	private Long passboxWidth = 135L;

	/**
	 * 高度
	 */
	private Long passboxHeight = 21L;

	/**
	 * 是否启用
	 */
	private Long passboxEnabled = 1L;

	/**
	 * 是否启用代码名称
	 */
	private String passboxEnabledName = "";

	/**
	 * 删除标识 0：未删除  1：已删除
	 */
	private Integer status;

	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 备注
	 */
	private String passboxRemarks = "";

	/**
	 * 编码
	 */
	private String passboxCode;

	public Passbox() {
		super();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPassboxName() {
		return passboxName;
	}

	public void setPassboxName(String passboxName) {
		this.passboxName = passboxName;
	}

	public Long getPassboxWidth() {
		return passboxWidth;
	}

	public void setPassboxWidth(Long passboxWidth) {
		this.passboxWidth = passboxWidth;
	}

	public Long getPassboxHeight() {
		return passboxHeight;
	}

	public void setPassboxHeight(Long passboxHeight) {
		this.passboxHeight = passboxHeight;
	}

	public Long getPassboxEnabled() {
		return passboxEnabled;
	}

	public void setPassboxEnabled(Long passboxEnabled) {
		this.passboxEnabled = passboxEnabled;
	}

	public String getPassboxEnabledName() {
		return passboxEnabledName;
	}

	public void setPassboxEnabledName(String passboxEnabledName) {
		this.passboxEnabledName = passboxEnabledName;
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

	public String getPassboxRemarks() {
		return passboxRemarks;
	}

	public void setPassboxRemarks(String passboxRemarks) {
		this.passboxRemarks = passboxRemarks;
	}

	public String getPassboxCode() {
		return passboxCode;
	}

	public void setPassboxCode(String passboxCode) {
		this.passboxCode = passboxCode;
	}
	
}
