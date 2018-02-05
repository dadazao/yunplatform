package com.cloudstong.platform.resource.selectmanage.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-19
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选择列
 */
public class SelectManage implements Serializable {

	private static final long serialVersionUID = -1514416805567122609L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 选择列名称
	 */
	private String selectManageName;

	/**
	 * 宽度
	 */
	private Integer selectManageWidth;

	/**
	 * 单位
	 */
	private String selectManageUnit;

	/**
	 * 样式
	 */
	private Integer selectManageStyle;

	/**
	 * 是否全选
	 */
	private Integer selectManageIsSelect;

	/**
	 * 标题位置
	 */
	private String selectManagePosition;

	/**
	 * 功能说明
	 */
	private String comment;

	/**
	 * 备注
	 */
	private String remark;

	public SelectManage() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSelectManageName() {
		return selectManageName;
	}

	public void setSelectManageName(String selectManageName) {
		this.selectManageName = selectManageName;
	}

	public Integer getSelectManageWidth() {
		return selectManageWidth;
	}

	public void setSelectManageWidth(Integer selectManageWidth) {
		this.selectManageWidth = selectManageWidth;
	}

	public String getSelectManageUnit() {
		return selectManageUnit;
	}

	public void setSelectManageUnit(String selectManageUnit) {
		this.selectManageUnit = selectManageUnit;
	}

	public Integer getSelectManageStyle() {
		return selectManageStyle;
	}

	public void setSelectManageStyle(Integer selectManageStyle) {
		this.selectManageStyle = selectManageStyle;
	}

	public Integer getSelectManageIsSelect() {
		return selectManageIsSelect;
	}

	public void setSelectManageIsSelect(Integer selectManageIsSelect) {
		this.selectManageIsSelect = selectManageIsSelect;
	}

	public String getSelectManagePosition() {
		return selectManagePosition;
	}

	public void setSelectManagePosition(String selectManagePosition) {
		this.selectManagePosition = selectManagePosition;
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
