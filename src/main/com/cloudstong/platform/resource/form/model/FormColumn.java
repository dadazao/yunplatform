package com.cloudstong.platform.resource.form.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单字段
 */
public class FormColumn implements Serializable{
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 编码 
	 */
	private String code;
	
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
	 * 字段长度
	 */
	private Integer length;
	/**
	 * 字段ID
	 */
	private Long columnId;
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
	 * 关联字段
	 */
	private Long relColumnId = -1L;
	/**
	 * 表单中显示顺序
	 */
	private Integer formOrder;
	/**
	 * 是否显示在列表中，1代表是，0代表否
	 */
	private int isShowInList = 0;
	/**
	 * 列表顺序
	 */
	private Integer listOrder;
	/**
	 * 是否是查询条件，1代表是，0代表否
	 */
	private Integer isQuery = 0;
	/**
	 * 是否是缺省查询条件，1代表是，0代表否
	 */
	private Integer isDefaultQuery = 0;
	/**
	 * 录入类型
	 */
	private int inputType = 0;

	/**
	 * 宽度
	 */
	private Integer colWidth;

	/**
	 * 单位
	 */
	private String colUnit;

	/**
	 * 是否启用，1代表是，0代表否
	 */
	private int isUse = 1;

	/**
	 * 所属表单ID
	 */
	private Long formId;

	/**
	 * 选项卡ID
	 */
	private Long tabId = -1L;

	/**
	 * 选项卡名称
	 */
	private String tabName;

	/**
	 * 所属分区
	 */
	private Long partitionId = -1L;

	/**
	 * 文本框ID
	 */
	private Long textBoxId;

	/**
	 * 文本域ID
	 */
	private Long textFieldId;

	/**
	 * 下拉框ID
	 */
	private Long comBoxId;

	/**
	 * 日期组件ID
	 */
	private Long dateId;

	/**
	 * 单选框ID
	 */
	private Long radioId;

	/**
	 * 复选框ID
	 */
	private Long checkBoxId;

	/**
	 * 搜索下拉框ID
	 */
	private Long searchComBoxId;

	/**
	 * 树ID
	 */
	private Long treeId;

	/**
	 * 文本编辑器ID
	 */
	private Long editorId;

	/**
	 * 组件ID
	 */
	private Long compexId;

	/**
	 * 下拉框数据类型
	 */
	private Integer selectDataType;

	/**
	 * 代码父ID
	 */
	private Long codeParentId;

	/**
	 * 代码父名称
	 */
	private String codeParentName;

	/**
	 * 关系表
	 */
	private String relationTable;

	/**
	 * 关系字段
	 */
	private String relationColumn;

	/**
	 * 校验
	 */
	private String validate;

	/**
	 * 是否必填
	 */
	private Integer required = 0;

	/**
	 * 查询字段
	 */
	private String queryStr;

	/**
	 * 是否单独成行
	 */
	private Integer isLine = 0;

	/**
	 * 是否编辑
	 */
	private Integer isEdit = 1;

	/**
	 * 是否查看
	 */
	private Integer isView = 1;

	/**
	 * 是否有空选项
	 */
	private Integer hasNull = 0;

	/**
	 * 空选项
	 */
	private String nullName;

	/**
	 * 默认值
	 */
	private String defaultValue;
	
	/**
	 * 是否允许重复
	 */
	private Integer canReply = 1;
	
	/**
	 *字段功能说明 
	 */
	private String colComment;
	
	/**
	 * 首页显示
	 */
	private Integer isShowIndex;
	
	/**
	 * 可选代码项 
	 */
	private String canSelectItem;
	
	/**
	 * 代码显示顺序
	 */
	private Integer codeShowOrder;
	
	/**
	 * 是否有默认选项
	 */
	private Integer hasDefaultItem;
	
	/**
	 * 默认选项
	 */
	private String defaultSelectItem;
	
	/**
	 * 所属对象字段 
	 */
	private String recordColumn;
	
	/**
	 * 是否链接打开查看页面
	 */
	private Integer isLinkView;
	
	/**
	 * 是否有权限 0 无权限  1 查看权限 2 修改权限 3 查看和修改权限
	 */
	private String hasAuth="3";
	
	/**
	 * 字处理组件的显示方式
	 */
	private Integer officeMode=0;
	
	/**
	 * 是否支持排序，0：不支持 1：支持
	 */
	private Integer supportOrder;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getBelongTable() {
		return belongTable;
	}

	public void setBelongTable(String belongTable) {
		this.belongTable = belongTable;
	}

	public Integer getFormOrder() {
		return formOrder;
	}

	public void setFormOrder(Integer formOrder) {
		this.formOrder = formOrder;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public int getIsShowInList() {
		return isShowInList;
	}

	public void setIsShowInList(int isShowInList) {
		this.isShowInList = isShowInList;
	}

	public Integer getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(Integer isQuery) {
		this.isQuery = isQuery;
	}

	public Integer getIsDefaultQuery() {
		return isDefaultQuery;
	}

	public void setIsDefaultQuery(Integer isDefaultQuery) {
		this.isDefaultQuery = isDefaultQuery;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public int getIsUse() {
		return isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public String getColumnZhName() {
		return columnZhName;
	}

	public void setColumnZhName(String columnZhName) {
		this.columnZhName = columnZhName;
	}

	public String getTableZhName() {
		return tableZhName;
	}

	public void setTableZhName(String tableZhName) {
		this.tableZhName = tableZhName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getCodeParentName() {
		return codeParentName;
	}

	public void setCodeParentName(String codeParentName) {
		this.codeParentName = codeParentName;
	}

	public Integer getSelectDataType() {
		return selectDataType;
	}

	public void setSelectDataType(Integer selectDataType) {
		this.selectDataType = selectDataType;
	}

	public String getRelationColumn() {
		return relationColumn;
	}

	public void setRelationColumn(String relationColumn) {
		this.relationColumn = relationColumn;
	}

	public String getRelationTable() {
		return relationTable;
	}

	public void setRelationTable(String relationTable) {
		this.relationTable = relationTable;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public Integer getRequired() {
		return required;
	}

	public void setRequired(Integer required) {
		this.required = required;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public Integer getIsLine() {
		return isLine;
	}

	public void setIsLine(Integer isLine) {
		this.isLine = isLine;
	}

	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public Integer getHasNull() {
		return hasNull;
	}

	public void setHasNull(Integer hasNull) {
		this.hasNull = hasNull;
	}

	public String getNullName() {
		return nullName;
	}

	public void setNullName(String nullName) {
		this.nullName = nullName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getCanReply() {
		return canReply;
	}

	public void setCanReply(Integer canReply) {
		this.canReply = canReply;
	}

	public String getColComment() {
		return colComment;
	}

	public void setColComment(String colComment) {
		this.colComment = colComment;
	}

	public Integer getIsShowIndex() {
		return isShowIndex;
	}

	public void setIsShowIndex(Integer isShowIndex) {
		this.isShowIndex = isShowIndex;
	}

	public String getCanSelectItem() {
		return canSelectItem;
	}

	public void setCanSelectItem(String canSelectItem) {
		this.canSelectItem = canSelectItem;
	}

	public Integer getCodeShowOrder() {
		return codeShowOrder;
	}

	public void setCodeShowOrder(Integer codeShowOrder) {
		this.codeShowOrder = codeShowOrder;
	}

	public Integer getColWidth() {
		return colWidth;
	}

	public void setColWidth(Integer colWidth) {
		this.colWidth = colWidth;
	}

	public String getColUnit() {
		return colUnit;
	}

	public void setColUnit(String colUnit) {
		this.colUnit = colUnit;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getHasDefaultItem() {
		return hasDefaultItem;
	}

	public void setHasDefaultItem(Integer hasDefaultItem) {
		this.hasDefaultItem = hasDefaultItem;
	}

	public String getDefaultSelectItem() {
		return defaultSelectItem;
	}

	public void setDefaultSelectItem(String defaultSelectItem) {
		this.defaultSelectItem = defaultSelectItem;
	}

	public String getRecordColumn() {
		return recordColumn;
	}

	public void setRecordColumn(String recordColumn) {
		this.recordColumn = recordColumn;
	}

	public Integer getIsLinkView() {
		return isLinkView;
	}

	public void setIsLinkView(Integer isLinkView) {
		this.isLinkView = isLinkView;
	}

	public String getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(String hasAuth) {
		this.hasAuth = hasAuth;
	}

	public Integer getOfficeMode() {
		return officeMode;
	}

	public void setOfficeMode(Integer officeMode) {
		this.officeMode = officeMode;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getRelColumnId() {
		return relColumnId;
	}

	public void setRelColumnId(Long relColumnId) {
		this.relColumnId = relColumnId;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public Long getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(Long partitionId) {
		this.partitionId = partitionId;
	}

	public Long getTextBoxId() {
		return textBoxId;
	}

	public void setTextBoxId(Long textBoxId) {
		this.textBoxId = textBoxId;
	}

	public Long getTextFieldId() {
		return textFieldId;
	}

	public void setTextFieldId(Long textFieldId) {
		this.textFieldId = textFieldId;
	}

	public Long getComBoxId() {
		return comBoxId;
	}

	public void setComBoxId(Long comBoxId) {
		this.comBoxId = comBoxId;
	}

	public Long getDateId() {
		return dateId;
	}

	public void setDateId(Long dateId) {
		this.dateId = dateId;
	}

	public Long getRadioId() {
		return radioId;
	}

	public void setRadioId(Long radioId) {
		this.radioId = radioId;
	}

	public Long getCheckBoxId() {
		return checkBoxId;
	}

	public void setCheckBoxId(Long checkBoxId) {
		this.checkBoxId = checkBoxId;
	}

	public Long getSearchComBoxId() {
		return searchComBoxId;
	}

	public void setSearchComBoxId(Long searchComBoxId) {
		this.searchComBoxId = searchComBoxId;
	}

	public Long getTreeId() {
		return treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	public Long getCompexId() {
		return compexId;
	}

	public void setCompexId(Long compexId) {
		this.compexId = compexId;
	}

	public Long getCodeParentId() {
		return codeParentId;
	}

	public void setCodeParentId(Long codeParentId) {
		this.codeParentId = codeParentId;
	}

	/**
	 * @return Returns the value of supportOrder field with the type Integer.
	 */
	public Integer getSupportOrder() {
		return supportOrder;
	}

	/**
	 * @param supportOrder. Set the value of supportOrder field with the type Integer by the supportOrder parameter.
	 */
	public void setSupportOrder(Integer supportOrder) {
		this.supportOrder = supportOrder;
	}
	
}
