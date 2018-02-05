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
 * Description:数据查询表的映射对象
 */
@SuppressWarnings("serial")
public class DataSearch  extends EntityBase {
	/**
	 * 数据查询id
	 */
	private Long id;
	
	/**
	 * 所属表
	 */
	private String tblBelongtable;
	
	/**
	 * 数据查询内容 
	 */
	private String tblContent;
	
	/**
	 * 功能说明 
	 */
	private String tblComment;
	
	/**
	 * 备注
	 */
	private String tblRemark;
	
	/**
	 * 数据查询名称 
	 */
	private String tblName;
	
	/**
	 * 所属目录
	 */
	private String tblCatalog;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTblBelongtable() {
		return tblBelongtable;
	}
	public void setTblBelongtable(String tblBelongtable) {
		this.tblBelongtable = tblBelongtable;
	}
	public String getTblContent() {
		return tblContent;
	}
	public void setTblContent(String tblContent) {
		this.tblContent = tblContent;
	}
	public String getTblComment() {
		return tblComment;
	}
	public void setTblComment(String tblComment) {
		this.tblComment = tblComment;
	}
	public String getTblRemark() {
		return tblRemark;
	}
	public void setTblRemark(String tblRemark) {
		this.tblRemark = tblRemark;
	}
	public String getTblName() {
		return tblName;
	}
	public void setTblName(String tblName) {
		this.tblName = tblName;
	}
	public String getTblCatalog() {
		return tblCatalog;
	}
	public void setTblCatalog(String tblCatalog) {
		this.tblCatalog = tblCatalog;
	}
}
