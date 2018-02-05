package com.cloudstong.platform.resource.button.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:按钮
 */
public class Button implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 按钮类型(文字、图片、文字图片)
	 */
	private long buttonType = -1;
	/**
	 * 按钮类型名称
	 */
	private String buttonTypeName = "";
	/**
	 * 按钮编码
	 */
	private String buttonBM = ""; 
	/**
	 * 按钮编码名称
	 */
	private String buttonBMName = "";

	/**
	 * 按钮名称
	 */
	private String buttonName = "";
	/**
	 * 按钮宽度
	 */
	private long buttonWidth = 60; 
	/**
	 * 按钮高度
	 */
	private long buttonHeight = 21; 
	/**
	 * 按钮名称字体
	 */
	private String buttonNameFontStyle = "";
	/**
	 * 按钮名称字体名称
	 */
	private String buttonNameFontStyleName = "";

	/**
	 * 按钮名称字号
	 */
	private Long buttonNameFontSize = 12L;

	/**
	 * 按钮名称字体颜色
	 */
	private String buttonNameFontColor = "";
	/**
	 * 按钮名称字体颜色名称
	 */
	private String buttonNameFontColorName = "";

	/**
	 * 按钮背景颜色
	 */
	private String buttonBackGroundColor = "";
	/**
	 * 按钮背景颜色名称
	 */
	private String buttonBackGroundColorName = "";

	/**
	 * 按钮背景图片
	 */
	private String buttonBackGroundImage = "";

	/**
	 * 按钮框大小
	 */
	private long buttonBorderSize = 1;
	/**
	 * 按钮框颜色
	 */
	private String buttonBorderColor = "";
	/**
	 * 按钮框颜色名称
	 */
	private String buttonBorderColorName = "";

	/**
	 * 按钮启用状态
	 */
	private long buttonStatus = -1;
	/**
	 * 按钮编码（编码表中的编码）
	 */
	private String buttonCode;

	/**
	 * 按钮启用状态名称
	 */
	private String buttonStatusName = "";
	/**
	 * 功能说明
	 */
	private String comment;
	/**
	 * 备注
	 */
	private String buttonRemarks = "";

	/**
	 * 删除标志 0：未删除   1：已删除
	 */
	private long status = -1;
	/**
	 * 创建人
	 */
	private String createBy = "-1";
	/**
	 * 创建时间
	 */
	private java.sql.Timestamp createDate = null;
	/**
	 * 修改人
	 */
	private String updateBy = "-1";
	/**
	 * 修改时间
	 */
	private java.sql.Timestamp updateDate = null;

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}

	public String getButtonBorderColorName() {
		return buttonBorderColorName;
	}

	public void setButtonBorderColorName(String buttonBorderColorName) {
		this.buttonBorderColorName = buttonBorderColorName;
	}

	public long getButtonBorderSize() {
		return buttonBorderSize;
	}

	public String getButtonBMName() {
		return buttonBMName;
	}

	public void setButtonBMName(String buttonBMName) {
		this.buttonBMName = buttonBMName;
	}

	public String getButtonNameFontStyleName() {
		return buttonNameFontStyleName;
	}

	public void setButtonNameFontStyleName(String buttonNameFontStyleName) {
		this.buttonNameFontStyleName = buttonNameFontStyleName;
	}

	public String getButtonNameFontColorName() {
		return buttonNameFontColorName;
	}

	public void setButtonNameFontColorName(String buttonNameFontColorName) {
		this.buttonNameFontColorName = buttonNameFontColorName;
	}

	public String getButtonBackGroundColorName() {
		return buttonBackGroundColorName;
	}

	public void setButtonBackGroundColorName(String buttonBackGroundColorName) {
		this.buttonBackGroundColorName = buttonBackGroundColorName;
	}

	public String getButtonStatusName() {
		return buttonStatusName;
	}

	public void setButtonStatusName(String buttonStatusName) {
		this.buttonStatusName = buttonStatusName;
	}

	public void setButtonBorderSize(long buttonBorderSize) {
		this.buttonBorderSize = buttonBorderSize;
	}

	public String getButtonBorderColor() {
		return buttonBorderColor;
	}

	public void setButtonBorderColor(String buttonBorderColor) {
		this.buttonBorderColor = buttonBorderColor;
	}

	public String getButtonTypeName() {
		return buttonTypeName;
	}

	public void setButtonTypeName(String buttonTypeName) {
		this.buttonTypeName = buttonTypeName;
	}

	public long getButtonType() {
		return buttonType;
	}

	public void setButtonType(long buttonType) {
		this.buttonType = buttonType;
	}

	public String getButtonBM() {
		return buttonBM;
	}

	public void setButtonBM(String buttonBM) {
		this.buttonBM = buttonBM;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public long getButtonWidth() {
		return buttonWidth;
	}

	public void setButtonWidth(long buttonWidth) {
		this.buttonWidth = buttonWidth;
	}

	public long getButtonHeight() {
		return buttonHeight;
	}

	public void setButtonHeight(long buttonHeight) {
		this.buttonHeight = buttonHeight;
	}

	public String getButtonNameFontStyle() {
		return buttonNameFontStyle;
	}

	public void setButtonNameFontStyle(String buttonNameFontStyle) {
		this.buttonNameFontStyle = buttonNameFontStyle;
	}

	public Long getButtonNameFontSize() {
		return buttonNameFontSize;
	}

	public void setButtonNameFontSize(Long buttonNameFontSize) {
		this.buttonNameFontSize = buttonNameFontSize;
	}

	public String getButtonNameFontColor() {
		return buttonNameFontColor;
	}

	public void setButtonNameFontColor(String buttonNameFontColor) {
		this.buttonNameFontColor = buttonNameFontColor;
	}

	public String getButtonBackGroundColor() {
		return buttonBackGroundColor;
	}

	public void setButtonBackGroundColor(String buttonBackGroundColor) {
		this.buttonBackGroundColor = buttonBackGroundColor;
	}

	public String getButtonBackGroundImage() {
		return buttonBackGroundImage;
	}

	public void setButtonBackGroundImage(String buttonBackGroundImage) {
		this.buttonBackGroundImage = buttonBackGroundImage;
	}

	public long getButtonStatus() {
		return buttonStatus;
	}

	public void setButtonStatus(long buttonStatus) {
		this.buttonStatus = buttonStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getButtonRemarks() {
		return buttonRemarks;
	}

	public void setButtonRemarks(String buttonRemarks) {
		this.buttonRemarks = buttonRemarks;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
