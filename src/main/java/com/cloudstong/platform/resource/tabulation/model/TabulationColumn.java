package com.cloudstong.platform.resource.tabulation.model;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表元素
 */
public class TabulationColumn {

	/**
	 * 唯一标示ID
	 */
	private Long id;
	/**
	 * 字段名
	 */
	private String columnName;
	/**
	 * 字段中文名
	 */
	private String columnZhName;

	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 字段ID
	 */
	private String columnId;
	/**
	 * 所属表
	 */
	private String belongTable;
	/**
	 * 所属表中文名
	 */
	private String tableZhName;
	/**
	 * 所属表ID
	 */
	private Long tableId;
	/**
	 * 是否显示在列表中，1代表是，0代表否
	 */
	private int isShowInList = 1;
	/**
	 * 列表顺序
	 */
	private int listOrder;
	/**
	 * 是否是查询条件，1代表是，0代表否
	 */
	private int isQuery = 1;
	/**
	 * 是否是缺省查询条件，1代表是，0代表否
	 */
	private int isDefaultQuery = 0;
	/**
	 * 是否启用，1代表是，0代表否
	 */
	private int isUse = 1;

	/**
	 * 所属列表ID
	 */
	private Long listId;


	public TabulationColumn() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnZhName() {
		return columnZhName;
	}

	public void setColumnZhName(String columnZhName) {
		this.columnZhName = columnZhName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public String getBelongTable() {
		return belongTable;
	}

	public void setBelongTable(String belongTable) {
		this.belongTable = belongTable;
	}

	public String getTableZhName() {
		return tableZhName;
	}

	public void setTableZhName(String tableZhName) {
		this.tableZhName = tableZhName;
	}

	public int getIsShowInList() {
		return isShowInList;
	}

	public void setIsShowInList(int isShowInList) {
		this.isShowInList = isShowInList;
	}

	public int getListOrder() {
		return listOrder;
	}

	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}

	public int getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(int isQuery) {
		this.isQuery = isQuery;
	}

	public int getIsDefaultQuery() {
		return isDefaultQuery;
	}

	public void setIsDefaultQuery(int isDefaultQuery) {
		this.isDefaultQuery = isDefaultQuery;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

}
