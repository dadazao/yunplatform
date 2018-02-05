package com.cloudstong.platform.resource.metadata.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.model.Table;

/**
 * 管理表服务
 * 
 * @author Allan
 * 
 */
public interface TableService {

	/**
	 * 保存表到数据库
	 * 
	 * @param table
	 */
	public Long doSaveTable(Table table);

	/**
	 * 根据Id删除表
	 * 
	 * @param id
	 */
	public void doDeleteTable(Long id);

	/**
	 * 更新表
	 * 
	 * @param table
	 */
	public void doUpdateTable(Table table);

	/**
	 * 根据Id查询表
	 * 
	 * @param id
	 * @return
	 */
	public Table findTableById(Long id);

	/**
	 * 根据查询条件查询表
	 * 
	 * @param qc
	 * @return
	 */
	public List<Table> queryTable(QueryCriteria qc);

	/**
	 * 查询获得指定数量的表
	 * 
	 * @param startRow
	 * @param rowsCount
	 * @return
	 */
	public List<Table> queryTable(int startRow, int rowsCount);

	/**
	 * 获得总记录数
	 * 
	 * @param queryCriteria
	 * @return
	 */
	public int countTable(QueryCriteria queryCriteria);

	/**
	 * 初始化管理表和字段表
	 */
	public void initializeDataTable();

	/**
	 * 根据表名和字段得到对应的值
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String findValueByColumn(String tableName, String columnName);

	/**
	 * 删除表
	 * 
	 * @param selectedIDs
	 */
	public void doDeleteTables(Long[] selectedIDs);

	/**
	 * 检查表名称是否重复
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isTableNameDouble(String tableName);

	/**
	 * 检查拼音名称是否重复
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isTableZhNameDouble(String tableZhName);

	/**
	 * Description:根据Id查找出所有表
	 * 
	 * @param tableId
	 * @return
	 */
	public List<Table> findTables(String tableId);

	/**
	 * Description:根据表名称查询出所有表
	 * 
	 * @param tableName
	 * @return
	 */
	public String findTableTypeByName(String tableName);

	/**
	 * 
	 * Description:更新数据表状态
	 * 
	 * Steps:
	 * 
	 * @param id
	 * @param i
	 */
	public void doUpdateStauts(Long id, int i);

	/**
	 * 删除表
	 * 
	 * @param selectedIDs
	 */
	public void dologicDeleteTables(Long[] selectedIDs);

	/**
	 * Description:根据Id和分组查找表的集合
	 * 
	 * @param tableId
	 * @return
	 */
	public List<Table> findTablesByTeam(Long tableId, String systemTeam);

	/**
	 * Description:定时创建日志表
	 * 
	 * Steps:
	 * 
	 */
	public void doAutoCreateLogTable();

	/**
	 * 
	 * Description:变更版本号
	 * 
	 * Steps:
	 * 
	 * @param id
	 */
	public void doChangeVersion(String id);
	
	public List queryHistory(String id);

	public List queryMetaHistory(String id, String version);
}
