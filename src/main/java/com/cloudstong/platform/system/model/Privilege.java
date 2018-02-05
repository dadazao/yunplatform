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
 * Description:权限表的映射对象
 */
@SuppressWarnings("serial")
public class Privilege extends EntityBase{
	
    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名称 
     */
    private String tblName;

    /**
     * 是否启用 
     */
    private String tblEnabled;

    /**
     * 所属目录
     */
    private String tblCatalog;

    /**
     * 附加属性,所属目录中文
     */
    private String tblCatalogName;

    /**
     * 权限类型 
     */
    private String tblType;

    /**
     * 附加属性,权限类型中文
     */
    private String tblTypeName;

    /**
     * 功能说明
     */
    private String tblComment;

    /**
     * 描述
     */
    private String tblRemark;

    /**
     * 编码ID
     */
    private String tblSeq;
    
    private String tblReferenceid;
    
    private String tblTablename;
    
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

	public String getTblEnabled() {
		return tblEnabled;
	}

	public void setTblEnabled(String tblEnabled) {
		this.tblEnabled = tblEnabled;
	}

	public String getTblCatalog() {
		return tblCatalog;
	}

	public void setTblCatalog(String tblCatalog) {
		this.tblCatalog = tblCatalog;
	}

	public String getTblType() {
		return tblType;
	}

	public void setTblType(String tblType) {
		this.tblType = tblType;
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

	public String getTblSeq() {
		return tblSeq;
	}

	public void setTblSeq(String tblSeq) {
		this.tblSeq = tblSeq;
	}

	public String getTblTypeName() {
		return tblTypeName;
	}

	public void setTblTypeName(String tblTypeName) {
		this.tblTypeName = tblTypeName;
	}

	public String getTblCatalogName() {
		return tblCatalogName;
	}

	public void setTblCatalogName(String tblCatalogName) {
		this.tblCatalogName = tblCatalogName;
	}

	public String getTblReferenceid() {
		return tblReferenceid;
	}

	public void setTblReferenceid(String tblReferenceid) {
		this.tblReferenceid = tblReferenceid;
	}

	public String getTblTablename() {
		return tblTablename;
	}

	public void setTblTablename(String tblTablename) {
		this.tblTablename = tblTablename;
	}
}
