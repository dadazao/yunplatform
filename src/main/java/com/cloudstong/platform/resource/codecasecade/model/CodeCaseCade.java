package com.cloudstong.platform.resource.codecasecade.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码级联
 */
public class CodeCaseCade implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 代码级联名称
	 */
	private String codeCaseCadeName;
	/**
	 * 是否默认
	 */
	private Integer isDefault;
	/**
	 * 宽度
	 */
	private Integer width;
	/**
	 * 高度
	 */
	private Integer height;
	/**
	 * 顶级代码
	 */
	private String topCode;
	/**
	 * 显示级数
	 */
	private Integer showProgression;
	/**
	 * 功能说明
	 */
	private String comment;
	/**
	 * 备注
	 */
	private String remark;

	public CodeCaseCade() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeCaseCadeName() {
		return codeCaseCadeName;
	}

	public void setCodeCaseCadeName(String codeCaseCadeName) {
		this.codeCaseCadeName = codeCaseCadeName;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getTopCode() {
		return topCode;
	}

	public void setTopCode(String topCode) {
		this.topCode = topCode;
	}

	public Integer getShowProgression() {
		return showProgression;
	}

	public void setShowProgression(Integer showProgression) {
		this.showProgression = showProgression;
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
