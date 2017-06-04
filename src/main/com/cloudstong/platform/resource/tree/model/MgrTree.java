package com.cloudstong.platform.resource.tree.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Allan
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:树模板
 */
public class MgrTree extends EntityBase{
	
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 所属分组
	 */
	private String systemTeam;
	
	/**
	 * 树模板名称
	 */
	private String treename;

	/**
	 * 树模板所属表ID
	 */
	private Long tableId;

	/**
	 * 树模板所属表名称
	 */
	private String tableName;

	/**
	 * 树模板所属表中文名称
	 */
	private String tableZhName;

	/**
	 * 显示字段ID
	 */
	private Long disColumnId;

	/**
	 * 显示字段名称
	 */
	private String disColumnName;

	/**
	 * 显示字段中文名称
	 */
	private String disColumnZhName;

	/**
	 * 父ID字段
	 */
	private Long parentColumnId;

	/**
	 * 父ID字段名称
	 */
	private String parentColumnName;

	/**
	 * 父ID字段中文名称
	 */
	private String parentColumnZhName;

	/**
	 * 根字段ID
	 */
	private Long rootId;

	/**
	 * 根字段名称
	 */
	private String rootName;

	/**
	 * 根字段中文名称
	 */
	private String rootZhName;

	/**
	 * 排序字段ID
	 */
	private Long orderColumnId;

	/**
	 * 排序字段名称
	 */
	private String orderColumnName;

	/**
	 * 排序字段名称
	 */
	private String orderColumnZhName;

	/**
	 * 类型
	 */
	private Long type;

	/**
	 * 类型名称
	 */
	private String typeName;

	/**
	 * 功能说明
	 */
	private String comment;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 表类型
	 */
	private String tableType;
	
	/**
	 * 树模板所属表(2)ID
	 */
	private Long tableIdChild;

	/**
	 * 所属表(2)显示字段ID
	 */
	private Long disColumnIdChild;
	/**
	 * 所属表(2)排序字段ID
	 */
	private Long orderColumnIdChild;

	/**
	 * 所属表(2)关联字段
	 */
	private Long parentColumnIdChild;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSystemTeam() {
		return systemTeam;
	}

	public void setSystemTeam(String systemTeam) {
		this.systemTeam = systemTeam;
	}

	public String getTreename() {
		return treename;
	}

	public void setTreename(String treename) {
		this.treename = treename;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDisColumnName() {
		return disColumnName;
	}

	public void setDisColumnName(String disColumnName) {
		this.disColumnName = disColumnName;
	}

	public String getParentColumnName() {
		return parentColumnName;
	}

	public void setParentColumnName(String parentColumnName) {
		this.parentColumnName = parentColumnName;
	}

	public String getOrderColumnName() {
		return orderColumnName;
	}

	public void setOrderColumnName(String orderColumnName) {
		this.orderColumnName = orderColumnName;
	}

	public String getTableZhName() {
		return tableZhName;
	}

	public void setTableZhName(String tableZhName) {
		this.tableZhName = tableZhName;
	}

	public String getDisColumnZhName() {
		return disColumnZhName;
	}

	public void setDisColumnZhName(String disColumnZhName) {
		this.disColumnZhName = disColumnZhName;
	}

	public String getParentColumnZhName() {
		return parentColumnZhName;
	}

	public void setParentColumnZhName(String parentColumnZhName) {
		this.parentColumnZhName = parentColumnZhName;
	}

	public String getRootZhName() {
		return rootZhName;
	}

	public void setRootZhName(String rootZhName) {
		this.rootZhName = rootZhName;
	}

	public String getOrderColumnZhName() {
		return orderColumnZhName;
	}

	public void setOrderColumnZhName(String orderColumnZhName) {
		this.orderColumnZhName = orderColumnZhName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getDisColumnId() {
		return disColumnId;
	}

	public void setDisColumnId(Long disColumnId) {
		this.disColumnId = disColumnId;
	}

	public Long getParentColumnId() {
		return parentColumnId;
	}

	public void setParentColumnId(Long parentColumnId) {
		this.parentColumnId = parentColumnId;
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public Long getOrderColumnId() {
		return orderColumnId;
	}

	public void setOrderColumnId(Long orderColumnId) {
		this.orderColumnId = orderColumnId;
	}

	public Long getTableIdChild() {
		return tableIdChild;
	}

	public void setTableIdChild(Long tableIdChild) {
		this.tableIdChild = tableIdChild;
	}

	public Long getDisColumnIdChild() {
		return disColumnIdChild;
	}

	public void setDisColumnIdChild(Long disColumnIdChild) {
		this.disColumnIdChild = disColumnIdChild;
	}

	public Long getOrderColumnIdChild() {
		return orderColumnIdChild;
	}

	public void setOrderColumnIdChild(Long orderColumnIdChild) {
		this.orderColumnIdChild = orderColumnIdChild;
	}

	public Long getParentColumnIdChild() {
		return parentColumnIdChild;
	}

	public void setParentColumnIdChild(Long parentColumnIdChild) {
		this.parentColumnIdChild = parentColumnIdChild;
	}
	

}
