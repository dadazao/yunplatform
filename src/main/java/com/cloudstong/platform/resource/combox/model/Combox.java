package com.cloudstong.platform.resource.combox.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:下拉框
 */
public class Combox extends Component implements Serializable {
	
	private static final long serialVersionUID = 6186664863624761097L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 下拉框名称
	 */
	private String comboxName = "";

	/**
	 * 下拉框宽度
	 */
	private Long comboxWidth = 135L;

	/**
	 * 下拉框高度
	 */
	private Long comboxHeight = 21L;

	/**
	 * 是否启用
	 */
	private Long comboxEnabled = 1L;

	/**
	 * 是否启用状态的显示名称
	 */
	private String comboxEnabledName = "";

	/**
	 * 下拉框类型
	 */
	private Long comboxType = 1L;

	/**
	 * 下拉框类型的显示名称
	 */
	private String comboxTypeName = "";

	/**
	 * 删除标识
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String comboxRemarks = "";

	/**
	 * 编码
	 */
	private String comboxCode;

	public Combox() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComboxName() {
		return comboxName;
	}

	public void setComboxName(String comboxName) {
		this.comboxName = comboxName;
	}

	public Long getComboxWidth() {
		return comboxWidth;
	}

	public void setComboxWidth(Long comboxWidth) {
		this.comboxWidth = comboxWidth;
	}

	public Long getComboxHeight() {
		return comboxHeight;
	}

	public void setComboxHeight(Long comboxHeight) {
		this.comboxHeight = comboxHeight;
	}

	public Long getComboxEnabled() {
		return comboxEnabled;
	}

	public void setComboxEnabled(Long comboxEnabled) {
		this.comboxEnabled = comboxEnabled;
	}

	public String getComboxEnabledName() {
		return comboxEnabledName;
	}

	public void setComboxEnabledName(String comboxEnabledName) {
		this.comboxEnabledName = comboxEnabledName;
	}

	public Long getComboxType() {
		return comboxType;
	}

	public void setComboxType(Long comboxType) {
		this.comboxType = comboxType;
	}

	public String getComboxTypeName() {
		return comboxTypeName;
	}

	public void setComboxTypeName(String comboxTypeName) {
		this.comboxTypeName = comboxTypeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComboxRemarks() {
		return comboxRemarks;
	}

	public void setComboxRemarks(String comboxRemarks) {
		this.comboxRemarks = comboxRemarks;
	}

	public String getComboxCode() {
		return comboxCode;
	}

	public void setComboxCode(String comboxCode) {
		this.comboxCode = comboxCode;
	}
	
}
