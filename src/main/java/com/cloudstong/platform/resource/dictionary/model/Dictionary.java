package com.cloudstong.platform.resource.dictionary.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码
 */
public class Dictionary implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 代码名称
	 */
	private String name;
	/**
	 * 代码值
	 */
	private String value;
	/**
	 * 类型（分类、对象）
	 */
	private String type;
	/**
	 * 所属上级
	 */
	private Long belong;
	/**
	 * 级别
	 */
	private Integer level;
	/**
	 * 创建人员
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新人员
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 功能说明
	 */
	private String comment;
	/**
	 * 状态（逻辑删除、审批、使用、……）
	 */
	private Integer status;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 所属分类名称
	 */
	private String parent;
	/**
	 * 代码编序
	 */
	private int dicOrder;
	/**
	 * 代码编码
	 */
	private String dicCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getBelong() {
		return belong;
	}

	public void setBelong(Long belong) {
		this.belong = belong;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getDicOrder() {
		return dicOrder;
	}

	public void setDicOrder(int dicOrder) {
		this.dicOrder = dicOrder;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

}
