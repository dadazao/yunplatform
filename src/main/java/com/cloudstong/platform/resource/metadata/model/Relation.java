package com.cloudstong.platform.resource.metadata.model;

import java.io.Serializable;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author michael Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:表关系
 */
public class Relation extends EntityBase implements Serializable {

	private static final long serialVersionUID = 7498346960337373747L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 主表ID
	 */
	private String mainTableId;
	/**
	 * 主表名称
	 */
	private String mainTableName;
	/**
	 * 子表ID
	 */
	private String subTableId;
	/**
	 * 子表名称
	 */
	private String subTableName;
	/**
	 * 表关系类型
	 */
	private Integer relationType = -1;

	/**
	 * 主表中文名称
	 */
	private String mainTableCNName;
	/**
	 * 子表中文名称
	 */
	private String subTableCNName;
	/**
	 * 表关系类型名称
	 */
	private String relationTypeName;
	/**
	 * 功能说明
	 */
	private String comment;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 所属分组
	 */
	private String systemteam;
	/**
	 * 关联类型：关系表/外键
	 */
	private String connecttype;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainTableId() {
		return mainTableId;
	}

	public void setMainTableId(String mainTableId) {
		this.mainTableId = mainTableId;
	}

	public String getMainTableName() {
		return mainTableName;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}

	public String getSubTableId() {
		return subTableId;
	}

	public void setSubTableId(String subTableId) {
		this.subTableId = subTableId;
	}

	public String getSubTableName() {
		return subTableName;
	}

	public void setSubTableName(String subTableName) {
		this.subTableName = subTableName;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public String getMainTableCNName() {
		return mainTableCNName;
	}

	public void setMainTableCNName(String mainTableCNName) {
		this.mainTableCNName = mainTableCNName;
	}

	public String getSubTableCNName() {
		return subTableCNName;
	}

	public void setSubTableCNName(String subTableCNName) {
		this.subTableCNName = subTableCNName;
	}

	public String getRelationTypeName() {
		return relationTypeName;
	}

	public void setRelationTypeName(String relationTypeName) {
		this.relationTypeName = relationTypeName;
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

	public String getSystemteam() {
		return systemteam;
	}

	public void setSystemteam(String systemteam) {
		this.systemteam = systemteam;
	}

	public String getConnecttype() {
		return connecttype;
	}

	public void setConnecttype(String connecttype) {
		this.connecttype = connecttype;
	}

}
