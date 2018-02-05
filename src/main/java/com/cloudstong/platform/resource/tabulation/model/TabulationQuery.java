package com.cloudstong.platform.resource.tabulation.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表筛选条件
 */
public class TabulationQuery implements Serializable{
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 所属表ID
	 */
	private String tableId;

	/**
	 * 表名(英文名)
	 */
	private String tableName;

	/**
	 * 表名(中文名)
	 */
	private String tableCNName;

	/**
	 * 字段ID
	 */
	private String columnId;

	/**
	 * 字段名(英文名)
	 */
	private String columnName;

	/**
	 * 字段名(中文名)
	 */
	private String columnCNName;

	/**
	 * 字段值
	 */
	private String columnValue;
	
	/**
	 * 条件 1:= 2:> 3:< 4:like
	 */
	private Integer condition;

	/**
	 * 关系1:and 2:or
	 */
	private Integer relation;
	
	/**
	 * 列表ID
	 */
	private Long tabulationId;
	
	/**
	 * 顺序
	 */
	private String order;

	public TabulationQuery() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Long getTabulationId() {
		return tabulationId;
	}

	public void setTabulationId(Long tabulationId) {
		this.tabulationId = tabulationId;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTableCNName() {
		return tableCNName;
	}

	public void setTableCNName(String tableCNName) {
		this.tableCNName = tableCNName;
	}

	public String getColumnCNName() {
		return columnCNName;
	}

	public void setColumnCNName(String columnCNName) {
		this.columnCNName = columnCNName;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
