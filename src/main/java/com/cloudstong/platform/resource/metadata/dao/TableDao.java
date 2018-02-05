package com.cloudstong.platform.resource.metadata.dao;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.metadata.model.Meta;
import com.cloudstong.platform.resource.metadata.model.Table;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:数据表数据操作Dao
 */
public interface TableDao {

	/**
	 * Description:查询表
	 * 
	 * @param where
	 * @param args
	 * @param startRow
	 * @param rowsCount
	 * @return
	 */
	@Cacheable(value = "resourceCache")
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * Description:更新表
	 * 
	 * 
	 * @param u
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void update(Table u);

	/**
	 * Description:插入表
	 * 
	 * 
	 * @param u
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public Long insert(Table u);

	/**
	 * 
	 * Description:查询表
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public Table selectById(Long id);

	/**
	 * Description:删除表
	 * 
	 * 
	 * @param id
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id);

	/**
	 * Description:插入表list
	 * 
	 * 
	 * @param tables
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insertTables(final List<Table> tables);

	/**
	 * Description:初始化tables
	 * 
	 * 
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void initializeDataTable();

	/**
	 * Description:统计
	 * 
	 * @param qc
	 * @return
	 */
	public int count(QueryCriteria qc);

	/**
	 * Description:根据表名和字段查询出值
	 * 
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String findValueByColumn(String tableName, String columnName);

	/**
	 * Description:根据表名和field查询列表
	 * 
	 * @param tableZhName
	 * @param field
	 * @return
	 */
	public List queryTableByField(String tableZhName, String field);

	/**
	 * Description:根据id查询出表list
	 * 
	 * 
	 * @param tableId
	 * @return
	 */
	public List<Table> findTables(String tableId);

	/**
	 * Description:根据表名查询出表
	 * 
	 * @param _tableName
	 * @return
	 */
	public String findTableTypeByName(String tableName);

	/**
	 * 
	 * Description:根据表Id查询 出表Map结构
	 * 
	 * 
	 * @param model
	 * @return
	 */
	public Map findTableById(Long modelId);

	/**
	 * 
	 * Description:更新数据表状态
	 * 
	 * 
	 * @param id
	 * @param i
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void updateStatus(Long id, int i);

	/**
	 * Description:删除表
	 * 
	 * 
	 * @param id
	 */
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void logicDelete(Long id);

	/**
	 * Description:根据Id和分组查找表的集合
	 * 
	 * @param tableId
	 * @return
	 */
	public List<Table> findTablesByTeam(Long tableId, String systemTeam);

	/**
	 * Description:自动创建日志表
	 * 
	 * Steps:
	 * 
	 */
	public void autoCreateLogTable();

	public void insertMeta(Meta meta);
	
	public void updateVersion(String version, String id);
	
	public List selectHistoryById(String id);

	public List selectMetaHistoryById(String id, String version);
}
