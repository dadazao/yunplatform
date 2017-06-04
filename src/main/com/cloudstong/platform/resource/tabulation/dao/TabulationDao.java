package com.cloudstong.platform.resource.tabulation.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationColumn;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.resource.tabulation.model.TabulationQuery;

/**
 * @author michael Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:列表操作数据库接口
 */
public interface TabulationDao {
	/**
	 * Description:查询列表集合
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param startRow
	 *            当前页
	 * @param rowsCount
	 *            每页记录数
	 * @return 列表集合
	 */
	@Cacheable(value = "tabulationCache")
	public List select(String sql, Object[] args, int startRow, int rowsCount);

	/**
	 * Description:更新列表信息
	 * 
	 * @param tabulation
	 *            列表
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void update(Tabulation tabulation);

	/**
	 * Description:更新列表详细信息
	 * 
	 * @param tabulation
	 *            列表
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updateDetails(Tabulation tabulation);

	/**
	 * Description:更新列表路径
	 * 
	 * @param tabulation
	 *            列表
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updatePath(Tabulation tabulation);

	/**
	 * Description:保存列表信息
	 * 
	 * @param tabulation
	 *            列表
	 * @return
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public Long insert(Tabulation tabulation);

	/**
	 * Description:删除列表
	 * 
	 * @param id
	 *            列表ID
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void delete(Long id);

	/**
	 * Description:根据列表ID查找列表
	 * 
	 * @param id
	 *            列表ID
	 * @return 列表
	 */
	@Cacheable(value = "tabulationCache", key = "'Tabulation_selectById:'+#id")
	public Tabulation selectById(Long id);

	/**
	 * Description:查询列表字段
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @param currentIndex
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return 列表字段集合
	 */
	@Cacheable(value = "tabulationCache")
	public List selectTabulationColumn(String sql, Object[] array, int currentIndex, int pageSize);

	/**
	 * Description:统计列表字段
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @return 列表字段数量
	 */
	@Cacheable(value = "tabulationCache")
	public int countTabulationColumn(String sql, Object[] array);

	/**
	 * Description:更新列表字段信息
	 * 
	 * @param tabulationColumn
	 *            列表字段
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updateTabulationColumn(TabulationColumn tabulationColumn);

	/**
	 * Description:保存列表字段到数据库
	 * 
	 * @param tabulationColumn
	 *            列表字段
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void insertTabulationColumn(TabulationColumn tabulationColumn);

	/**
	 * Description:查询列表筛选条件
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @param currentIndex
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return 列表筛选条件集合
	 */
	@Cacheable(value = "tabulationCache")
	public List selectTabulationQuery(String sql, Object[] array, int currentIndex, int pageSize);

	/**
	 * Description:统计列表筛选条件
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @return 列表筛选条件数量
	 */
	@Cacheable(value = "tabulationCache")
	public int countTabulationQuery(String sql, Object[] array);

	/**
	 * Description:删除列表
	 * 
	 * @param id
	 *            列表ID
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void deleteTabulation(Long id);

	/**
	 * Description:根据列表字段ID查找列表字段
	 * 
	 * @param id
	 *            列表字段ID
	 * @return 列表字段
	 */
	public TabulationColumn selectTabulationColumnById(Long id);

	/**
	 * Description:根据表单ID查找列表
	 * 
	 * @param formId
	 *            表单ID
	 * @return 列表
	 */
	public Tabulation selectTabulationByFormId(Long formId);

	/**
	 * Description:根据列表ID和查询条件查询列表
	 * 
	 * @param id
	 *            列表ID
	 * @param qc
	 *            查询条件
	 * @param user
	 *            用户信息
	 * @return 列表
	 */
	// public Tabulation selectTabulationById(Long id, QueryCriteria qc,User
	// user);

	/**
	 * Description:根据列表ID和查询条件查询列表
	 * 
	 * @param id
	 *            列表ID
	 * @param qc
	 *            查询条件
	 * @param user
	 *            用户信息
	 * @return 列表
	 */
	// public Tabulation selectTabulationByListId(Long id, QueryCriteria qc,
	// SysUser user);

	/**
	 * Description:根据编码查询列表
	 * 
	 * @param seqcode
	 *            编码
	 * @return 列表
	 */
	public Tabulation selectTabulationBySeqcode(String seqcode);

	/**
	 * Description:保存列表筛选条件信息
	 * 
	 * @param tabulationQuery
	 *            列表筛选条件
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void insertTabulationQuery(TabulationQuery tabulationQuery);

	/**
	 * Description:更新列表筛选条件信息
	 * 
	 * @param tabulationQuery
	 *            列表筛选条件
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void updateTabulationQuery(TabulationQuery tabulationQuery);

	/**
	 * Description:删除列表筛选条件
	 * 
	 * @param id
	 *            列表筛选条件ID
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void deleteTabulationQuery(Long id);

	/**
	 * Description:根据ID查找列表筛选条件
	 * 
	 * @param tabulationQueryId
	 *            列表筛选条件ID
	 * @return 列表筛选条件
	 */
	public TabulationQuery selectTabulationQueryById(Long tabulationQueryId);

	/**
	 * Description:根据列表ID查询列表筛选条件
	 * 
	 * @param tabulationId
	 *            列表ID
	 * @return 列表筛选条件集合
	 */
	@Cacheable(value = "tabulationCache", key = "'selectQueryByTabulationId:'+#tabulationId")
	public List<TabulationQuery> selectQueryByTabulationId(Long tabulationId);

	/**
	 * Description:查询列表操作按钮
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @param currentIndex
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return 列表操作按钮集合
	 */
	@Cacheable(value = "tabulationCache")
	public List selectTabulationOpt(String sql, Object[] array, int currentIndex, int pageSize);

	/**
	 * Description:统计列表操作按钮
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @return 列表操作按钮数量
	 */
	@Cacheable(value = "tabulationCache")
	public int countTabulationOpt(String sql, Object[] array);

	/**
	 * Description:删除列表操作按钮
	 * 
	 * @param id
	 *            列表操作按钮
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void deleteTabulationOpt(Long id);

	/**
	 * Description:更新列表操作按钮信息
	 * 
	 * @param tabulationOpt
	 *            列表操作按钮
	 */
	public void updateTabulationOpt(TabulationOpt tabulationOpt);

	/**
	 * Description:保存列表操作按钮
	 * 
	 * @param tabulationOpt
	 *            列表操作按钮
	 * @return 列表操作按钮ID
	 */
	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public Long insertTabulationQuery(TabulationOpt tabulationOpt);

	/**
	 * Description:根据ID查找列表操作按钮
	 * 
	 * @param tabulationOptId
	 *            列表操作按钮ID
	 * @return 列表操作按钮
	 */
	public TabulationOpt selectTabulationOptById(Long tabulationOptId);

	/**
	 * Description:查询某个表是否有某个字段
	 * 
	 * @param tableName
	 *            表名称
	 * @param columnName
	 *            字段名称
	 * @return boolean
	 */
	public boolean tableHasColumn(String tableName, String columnName);

	/**
	 * Description:根据列表ID查找目录
	 * 
	 * @param listId
	 *            列表ID
	 * @return 目录集合
	 */
	public List findCataByListId(Long listId);

	/**
	 * Description:查询表单是否已经被其他列表引用，若返回的List集合大于0，则已经被其他列表引用
	 * 
	 * @param formId
	 *            表单ID
	 * @param id
	 *            列表ID
	 * @return 列表集合
	 */
	public List useFormCount(Long formId, Long id);

	/**
	 * Description:根据列表ID查找列表操作按钮集合
	 * 
	 * @param tabulationId
	 *            列表ID
	 * @return 列表操作按钮集合
	 */
	public List<TabulationOpt> findTabulationOpt(Long tabulationId);

	/**
	 * Description:根据表单ID查询列表集合
	 * 
	 * @param formid
	 *            表单ID
	 * @return 列表集合
	 */
	public List<Tabulation> queryTabulationByFormid(Long formid);

	@CacheEvict(value = "tabulationCache", allEntries = true, beforeInvocation = true)
	public void logicDelete(Long id);

	@Cacheable(value = "tabulationCache", key = "'compexQuery:'+#sql.hashCode()+'-'+#id")
	public Tabulation compexQuery(String sql, Long id);

	@Cacheable(value = "tabulationCache", key = "'selectColumnByTabulationId:'+#tabulationId")
	public List<FormColumn> selectColumnByTabulationId(Long id);

	@Cacheable(value = "tabulationCache", key = "'selectColumnByFormId:'+#formId")
	public List<FormColumn> selectColumnByFormId(Long formId);

	@Cacheable(value = "tabulationCache")
	public List<FormColumn> queryThead(String listSql, Object[] listParams);

	@Cacheable(value = "resourceCache", key = "'selectByRelation:'+#relationTable+'-'+#relationColumn")
	public List<Code> selectByRelation(String relationTable, String relationColumn);

	@Cacheable(value = "tabulationCache", key = "'selectAllColumnsByTabulationId:'+#id")
	public List<FormColumn> selectAllColumnsByTabulationId(Long id);
}
