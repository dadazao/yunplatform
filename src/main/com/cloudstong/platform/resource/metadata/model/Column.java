package com.cloudstong.platform.resource.metadata.model;

import java.util.Date;

public class Column {

	// 字段编号
	private Long id;

	// 字段名称
	private String columnName;

	// 中文名
	private String columnZhName;
	
	// 英文名
	private String columnEnName;

	// 所属表
	private String belongTable;

	// 所属表
	private String tableZhName;

	// 所属表Id
	private Long tableId;

	// 数据类型
	private String dataType;

	// 默认值
	private String defaultValue;

	// 长度限制
	private Integer length = 50;

	// 是否是主键
	private Integer isPrimaryKey = 0;

	// 是否空值
	private Integer isNullable = 1;

	// 是否发布
	private Integer isPublish = 0;

	// 字段功能说明
	private String comment;

	// 创建者
	private Long createBy;

	// 创建日期
	private Date createDate;

	// 更新者
	private Long updateBy;

	// 更新日期
	private Date updateDate;

	// 创建人名字
	private String userName;

	// 修改人名字
	private String updateName;

	// 标记删除
	private Integer status = 0;

	// 字段编码
	private String columnCode;

	// 字段别称
	private String columnAliasName;

	// 显示规则
	private Integer showRule = 0;

	// 验证值
	private String checkValue;

	// 备注
	private String remark;

	// 新增字段标志
	private Integer newFlag = 0;

	// 显示类型
	private Integer showType;
	
	private Integer isRequired = 0;
	
	private Integer isRepeat = 0;
	
	private Integer min = 0;
	
	private Integer max = 0;
	
	private Integer metaType = 1;
	
	private String parentId;

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnEnName() {
		return columnEnName;
	}

	public void setColumnEnName(String columnEnName) {
		this.columnEnName = columnEnName;
	}

	public String getColumnAliasName() {
		return columnAliasName;
	}

	public void setColumnAliasName(String columnAliasName) {
		this.columnAliasName = columnAliasName;
	}

	public Integer getShowRule() {
		return showRule;
	}

	public void setShowRule(Integer showRule) {
		this.showRule = showRule;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
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

	public String getBelongTable() {
		return belongTable;
	}

	public void setBelongTable(String belongTable) {
		this.belongTable = belongTable;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Integer isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Integer getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(Integer isNullable) {
		this.isNullable = isNullable;
	}

	public Integer getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
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

	public Integer getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(Integer newFlag) {
		this.newFlag = newFlag;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getTableZhName() {
		return tableZhName;
	}

	public void setTableZhName(String tableZhName) {
		this.tableZhName = tableZhName;
	}

	public Integer getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}

	public Integer getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(Integer isRepeat) {
		this.isRepeat = isRepeat;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMetaType() {
		return metaType;
	}

	public void setMetaType(Integer metaType) {
		this.metaType = metaType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
