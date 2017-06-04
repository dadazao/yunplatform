package com.cloudstong.platform.resource.metadata.model;

import java.io.Serializable;
import java.util.Date;

public class Table implements Serializable{

	// 编号
	private Long id;

	// 拼音表名
	private String tableName;

	// 中文名
	private String tableZhName;
	
	// 英文名 
	private String tableEnName;

	// 所属数据库
	private String tableSchema;

	// 表功能类型
	private String tableType;

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

	// 删除标记
	private Integer status = 0;

	// 备注
	private String remark;

	// 是否包含外键
	private Integer hasForeignKey = 0;

	// 是否包含索引
	private Integer hasIndex = 0;

	// 是否包含视图
	private Integer hasView = 0;

	// 是否包含公有字段
	private Integer hasCommonColumn = 1;

	// 表关系类型
	private String tableRelationType;

	// 表编码
	private String tableCode;

	// 功能说明
	private String tableFunction;

	// 表主键
	private String primaryKey;
	
	//所属分组
	private String group = "bus";
	
	//版本
	private String version = "1";

	public Table() {

	}

	public Table(String belongTable) {
		this.tableName = belongTable;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getTableFunction() {
		return tableFunction;
	}

	public void setTableFunction(String tableFunction) {
		this.tableFunction = tableFunction;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getTableEnName() {
		return tableEnName;
	}

	public void setTableEnName(String tableEnName) {
		this.tableEnName = tableEnName;
	}

	public String getTableRelationType() {
		return tableRelationType;
	}

	public void setTableRelationType(String tableRelationType) {
		this.tableRelationType = tableRelationType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getHasForeignKey() {
		return hasForeignKey;
	}

	public void setHasForeignKey(Integer hasForeignKey) {
		this.hasForeignKey = hasForeignKey;
	}

	public Integer getHasIndex() {
		return hasIndex;
	}

	public void setHasIndex(Integer hasIndex) {
		this.hasIndex = hasIndex;
	}

	public Integer getHasView() {
		return hasView;
	}

	public void setHasView(Integer hasView) {
		this.hasView = hasView;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableZhName() {
		return tableZhName;
	}

	public void setTableZhName(String tableZhName) {
		this.tableZhName = tableZhName;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
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

	public Integer getHasCommonColumn() {
		return hasCommonColumn;
	}

	public void setHasCommonColumn(Integer hasCommonColumn) {
		this.hasCommonColumn = hasCommonColumn;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
