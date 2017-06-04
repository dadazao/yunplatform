package com.cloudstong.platform.system.model;

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
public class Org extends EntityBase {
	private static final long serialVersionUID = 3426637686641664990L;
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 组织机构名称
	 */
	private String tblName;
	/**
	 * 组织的隶属关系
	 */
	private String tblParentid;
	/**
	 * 部门职能
	 */
	private String tblBumenzhineng;
	/**
	 * 部门级别
	 */
	private Integer tblBumenjibie;
	/**
	 * 机构排序
	 */
	private Integer tblJigoupaixu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public String getTblParentid() {
		return tblParentid;
	}

	public void setTblParentid(String tblParentid) {
		this.tblParentid = tblParentid;
	}

	public String getTblBumenzhineng() {
		return tblBumenzhineng;
	}

	public void setTblBumenzhineng(String tblBumenzhineng) {
		this.tblBumenzhineng = tblBumenzhineng;
	}

	public Integer getTblBumenjibie() {
		return tblBumenjibie;
	}

	public void setTblBumenjibie(Integer tblBumenjibie) {
		this.tblBumenjibie = tblBumenjibie;
	}

	public Integer getTblJigoupaixu() {
		return tblJigoupaixu;
	}

	public void setTblJigoupaixu(Integer tblJigoupaixu) {
		this.tblJigoupaixu = tblJigoupaixu;
	}
	
}
