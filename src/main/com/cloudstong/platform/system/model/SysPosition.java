package com.cloudstong.platform.system.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author liuqi
 * Created on 2014-8-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 */
public class SysPosition extends EntityBase {
	private static final long serialVersionUID = -1186389017836881310L;
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 岗位名称
	 */
	private String positionName;
	
	/**
	 * 岗位编号
	 */
	private String positionNo;
	
	/**
	 * 所在部门
	 */
	private Long orgId;
	
	/**
	 * 所在部门显示名称
	 */
	private String orgName;
	
	/**
	 * 薪资等级
	 */
	private String xinzidengji;
	
	/**
	 * 岗位说明
	 */
	private String comment;
	
	/**
	 * 上级
	 */
	private Long parentId;

	public SysPosition() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * @return the positionNo
	 */
	public String getPositionNo() {
		return positionNo;
	}

	/**
	 * @param positionNo the positionNo to set
	 */
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the xinzidengji
	 */
	public String getXinzidengji() {
		return xinzidengji;
	}

	/**
	 * @param xinzidengji the xinzidengji to set
	 */
	public void setXinzidengji(String xinzidengji) {
		this.xinzidengji = xinzidengji;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
