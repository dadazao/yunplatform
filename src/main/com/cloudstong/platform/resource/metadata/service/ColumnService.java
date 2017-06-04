package com.cloudstong.platform.resource.metadata.service;

import java.util.List;
import java.util.Map;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.ConfigColumn;

/**
 * 字段表服务
 * 
 * @author Allan
 * 
 */
public interface ColumnService {

	/**
	 * 保存字段
	 * 
	 * @param column
	 */
	public void doSaveColumn(Column column);

	/**
	 * 根据字段删除字段
	 * 
	 * @param id
	 */
	public void doDeleteColumn(Long id);

	/**
	 * 更新字段
	 * 
	 * @param column
	 */
	public void doUpdateColumn(Column column);

	/**
	 * 根据ID查找字段
	 * 
	 * @param id
	 * @return
	 */
	public Column findColumnById(Long id);

	/**
	 * 根据查询条件查找字段
	 * 
	 * @param qc
	 * @return
	 */
	public List<Column> queryColumn(QueryCriteria qc);
	
	/**
	 * 根据查询条件查找字段
	 * 
	 * @param qc
	 * @return
	 */
	public List<Column> queryColumnAuto(QueryCriteria qc);

	/**
	 * 查找指定数量的字段
	 * 
	 * @param startRow
	 * @param rowsCount
	 * @return
	 */
	public List<Column> queryColumn(int startRow, int rowsCount);

	/**
	 * 获得字段总记录数
	 * 
	 * @param queryCriteria
	 * @return
	 */
	public int count(QueryCriteria queryCriteria);

	/**
	 * 根据表名和字段值得到对应的值
	 * 
	 * @param tableName
	 * @param columnName
	 * @param whereColumn
	 * @return
	 */
	public String findValueByColumn(String tableName, String columnName,
			String whereColumn);

	/**
	 * 更新表
	 * 
	 * @param table
	 */
	public void doUpdateColumn(Column column, Map<String, String> dyncMap);

	/**
	 * 保存字段
	 * 
	 * @param column
	 */
	public void doSaveColumn(Column column, Map<String, String> dyncMap);

	/**
	 * 删除字段
	 * 
	 * @param selectedIDs
	 */
	public void doDeleteColumns(Long[] selectedIDs);

	/**
	 * 判断字段名称是否重复
	 * @param tableId
	 * @param columnZhName
	 * @return
	 */
	public boolean isColumnZhNameDouble(Long tableId, String columnZhName,Long columnId);

	/**
	 * 判断拼音名称是否重复
	 * @param tableId
	 * @param columnName
	 * @return
	 */
	public boolean isColumnNameDouble(Long tableId, String columnName,Long columnId);

	/**
	 * 
	 * Description:更新字段状态
	 * 
	 * 
	 * @param id
	 * @param i
	 */
	public void doUpdateStauts(Long id, int i);

	/**
	 * 删除字段
	 * 
	 * @param id
	 */
	public void doLogicDeleteColumn(Long id);

	/**
	 * 删除字段
	 * 
	 * @param selectedIDs
	 */
	public void doLogicDeleteColumns(Long[] selectedIDs);

	/**
	 * Description:配置代码生成器字段
	 * 
	 * Steps:
	 * 
	 * @param configStr
	 */
	public void doConfigColumns(String configStr);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param tableName
	 * @return
	 */
	public List<ConfigColumn> queryCfgColumnByTableName(String tableName);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param tableName
	 * @return
	 */
	public List<ConfigColumn> queryCftColumn(String tableName);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	public int countColumn(QueryCriteria queryCriteria);

}
