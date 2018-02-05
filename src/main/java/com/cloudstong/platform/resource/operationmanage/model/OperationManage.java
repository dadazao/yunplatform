package com.cloudstong.platform.resource.operationmanage.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:操作列
 */
public class OperationManage implements Serializable {

	private static final long serialVersionUID = -2794488338115208734L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 操作列名称
	 */
	private String optManageName;

	/**
	 * 宽度
	 */
	private Integer optManageWidth;

	/**
	 * 单位
	 */
	private String optManageUnit;

	/**
	 * 标题显示名称
	 */
	private String optManageShowName;

	/**
	 * 操作按钮数量
	 */
	private Integer optManagerCount;
	
	/**
	 * 标题位置
	 */
	private String optManagePosition;

	/**
	 * 功能说明
	 */
	private String comment;

	/**
	 * 备注
	 */
	private String remark;
	
	public OperationManage() {
		super();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOptManageName() {
		return optManageName;
	}

	public void setOptManageName(String optManageName) {
		this.optManageName = optManageName;
	}

	public Integer getOptManageWidth() {
		return optManageWidth;
	}

	public void setOptManageWidth(Integer optManageWidth) {
		this.optManageWidth = optManageWidth;
	}

	public String getOptManageUnit() {
		return optManageUnit;
	}

	public void setOptManageUnit(String optManageUnit) {
		this.optManageUnit = optManageUnit;
	}

	public String getOptManageShowName() {
		return optManageShowName;
	}

	public void setOptManageShowName(String optManageShowName) {
		this.optManageShowName = optManageShowName;
	}

	public Integer getOptManagerCount() {
		return optManagerCount;
	}

	public void setOptManagerCount(Integer optManagerCount) {
		this.optManagerCount = optManagerCount;
	}

	public String getOptManagePosition() {
		return optManagePosition;
	}

	public void setOptManagePosition(String optManagePosition) {
		this.optManagePosition = optManagePosition;
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
