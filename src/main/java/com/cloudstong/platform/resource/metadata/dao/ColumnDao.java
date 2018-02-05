package com.cloudstong.platform.resource.metadata.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.ConfigColumn;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:字段数据操作Dao
 * 
 */
public interface ColumnDao {

	/**
	 * Description:查询字段
	 * 
	 * @param where
	 * @param args
	 * @param startRow
	 * @param rowsCount
	 * @return
	 */
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * Description:更新字段
	 * 
	 * 
	 * @param column
	 */
	public void update(Column column);

	/**
	 * Description:插入字段
	 * 
	 * 
	 * @param u
	 * @param flag
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insert(Column u, boolean flag);

	/**
	 * Description:查询字段
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public Column selectById(Long id);

	/**
	 * Description:删除字段
	 * 
	 * 
	 * @param id
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id);

	/**
	 * Description:根据类生成字段
	 * 
	 * 
	 * @param clazz
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insertFieldsToColumn(Class clazz);

	/**
	 * Description:根据查询条件统计字段数
	 * 
	 * 
	 * @param qc
	 * @return
	 */
	public int count(QueryCriteria qc);

	/**
	 * Description:根据表名和字段查询出字段的值
	 * 
	 * 
	 * @param tableName
	 * @param columnName
	 * @param whereColumn
	 * @return
	 */
	public String findValueByColumn(String tableName, String columnName,
			String whereColumn);

	/**
	 * Description:更新字段
	 * 
	 * 
	 * @param column
	 * @param dyncMap
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void update(Column column, Map<String, String> dyncMap);

	/**
	 * Description:插入字段
	 * 
	 * 
	 * @param column
	 * @param b
	 * @param dyncMap
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insert(Column column, boolean b, Map<String, String> dyncMap);

	/**
	 * Description:根据field查找出字段
	 * 
	 * 
	 * @param tableId
	 * @param columnZhName
	 * @param field
	 * @return
	 */
	public List queryColumnByField(Long tableId, String columnZhName,
			String field);

	/**
	 * 
	 * Description:根据表id查询粗字段列表
	 * 
	 * 
	 * @param modelTableId
	 * @return
	 */
	public List<Map<String,Object>> findColumnsByTableId(String modelTableId);

	/**
	 * 
	 * Description:根据表ID删除字段
	 * 
	 * 
	 * @param tableId
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void deleteByTableId(Long tableId);

	/**
	 * 
	 * Description:更新字段状态
	 * 
	 * @param id
	 * @param i
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void updateStatus(Long id, int i);

	/**
	 * Description:删除字段
	 * 
	 * 
	 * @param id
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void logicDelete(Long id);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param tableName
	 */
	public void clearCfgColumnByTableName(String tableName);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param list
	 */
	public void saveCfgColumns(List<LinkedHashMap<String, Object>> list);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param tableName
	 * @return
	 */
	public List<ConfigColumn> selectCfgColumnByTableName(String tableName);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param tableName
	 * @return
	 */
	public List<ConfigColumn> selectCfgColumn(String tableName);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param string
	 * @param array
	 * @return
	 */
	public int count(String string, Object[] array);

}
