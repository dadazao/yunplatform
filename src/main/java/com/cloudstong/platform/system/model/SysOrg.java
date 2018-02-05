package com.cloudstong.platform.system.model;

import javax.persistence.Column;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author sam
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:组织机构表的映射对象
 */
public class SysOrg extends EntityBase {
	private static final long serialVersionUID = 3426637686641664990L;
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 组织机构名称
	 */
	private String orgName;
	
	/**
	 * 上级ID
	 */
	private Long parentId;
	
	/**
	 * 上级名称
	 */
	private String parentName;
	
	/**
	 * 部门职能
	 */
	@Column(name = "tbl_bumenzhineng")
	private String orgFunction;
	/**
	 * 部门级别
	 */
	@Column(name = "tbl_bumenjibie")
	private Integer orgLevel;
	/**
	 * 机构排序
	 */
	@Column(name = "tbl_jigoupaixu")
	private Integer orgOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the value of parentId field with the type String.
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId. Set the value of parentId field with the type String by the parentId parameter.
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return Returns the value of parentName field with the type String.
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName. Set the value of parentName field with the type String by the parentName parameter.
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return Returns the value of orgName field with the type String.
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName. Set the value of orgName field with the type String by the orgName parameter.
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return Returns the value of orgFunction field with the type String.
	 */
	public String getOrgFunction() {
		return orgFunction;
	}

	/**
	 * @param orgFunction. Set the value of orgFunction field with the type String by the orgFunction parameter.
	 */
	public void setOrgFunction(String orgFunction) {
		this.orgFunction = orgFunction;
	}

	/**
	 * @return Returns the value of orgLevel field with the type Integer.
	 */
	public Integer getOrgLevel() {
		return orgLevel;
	}

	/**
	 * @param orgLevel. Set the value of orgLevel field with the type Integer by the orgLevel parameter.
	 */
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * @return Returns the value of orgOrder field with the type Integer.
	 */
	public Integer getOrgOrder() {
		return orgOrder;
	}

	/**
	 * @param orgOrder. Set the value of orgOrder field with the type Integer by the orgOrder parameter.
	 */
	public void setOrgOrder(Integer orgOrder) {
		this.orgOrder = orgOrder;
	}

	public Long getOrgType() {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

}
