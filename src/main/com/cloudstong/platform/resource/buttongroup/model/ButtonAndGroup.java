package com.cloudstong.platform.resource.buttongroup.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.button.model.Button;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:按钮和按钮组中间表对象
 */
public class ButtonAndGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 按钮组ID
	 */
	private Long buttonGroupID;
	
	/**
	 * 按钮ID
	 */
	private Long buttonID;

	/**
	 * 按钮名称
	 */
	private String buttonName = "";

	/**
	 * 按钮在按钮组中的启用状态
	 */
	private long buttonEnabledStatus = 1;

	/**
	 * 按钮在按钮组中的启用状态的名称
	 */
	private String buttonEnabledStatusName = "";

	/**
	 * 按钮在按钮组中的显示状态
	 */
	private long buttonDisplayStatus = 1;

	/**
	 * 按钮在按钮组中的显示状态的名称
	 */
	private String buttonDisplayStatusName = "";

	/**
	 * 按钮在按钮组中的显示次序
	 */
	private long buttonDisplayOrder = 1;

	/**
	 * 删除标识
	 */
	private long status = -1;

	/**
	 * 按钮
	 */
	private Button button;

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getButtonGroupID() {
		return buttonGroupID;
	}

	public void setButtonGroupID(Long buttonGroupID) {
		this.buttonGroupID = buttonGroupID;
	}

	public Long getButtonID() {
		return buttonID;
	}

	public void setButtonID(Long buttonID) {
		this.buttonID = buttonID;
	}

	public long getButtonEnabledStatus() {
		return buttonEnabledStatus;
	}

	public void setButtonEnabledStatus(long buttonEnabledStatus) {
		this.buttonEnabledStatus = buttonEnabledStatus;
	}

	public String getButtonEnabledStatusName() {
		return buttonEnabledStatusName;
	}

	public void setButtonEnabledStatusName(String buttonEnabledStatusName) {
		this.buttonEnabledStatusName = buttonEnabledStatusName;
	}

	public long getButtonDisplayStatus() {
		return buttonDisplayStatus;
	}

	public void setButtonDisplayStatus(long buttonDisplayStatus) {
		this.buttonDisplayStatus = buttonDisplayStatus;
	}

	public String getButtonDisplayStatusName() {
		return buttonDisplayStatusName;
	}

	public void setButtonDisplayStatusName(String buttonDisplayStatusName) {
		this.buttonDisplayStatusName = buttonDisplayStatusName;
	}

	public long getButtonDisplayOrder() {
		return buttonDisplayOrder;
	}

	public void setButtonDisplayOrder(long buttonDisplayOrder) {
		this.buttonDisplayOrder = buttonDisplayOrder;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

}
