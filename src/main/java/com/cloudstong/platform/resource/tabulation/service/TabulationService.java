package com.cloudstong.platform.resource.tabulation.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationColumn;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.resource.tabulation.model.TabulationQuery;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表服务接口
 */
public interface TabulationService {
	/**
	 * Description:根据查询条件查询列表集合
	 * @param qc 查询条件
	 * @return 列表集合
	 */
	@Cacheable(value="tabulationCache",key="'queryTabulation:'+#qc+'List'")
	public List<Tabulation> queryTabulation(QueryCriteria qc);

	/**
	 * Description:根据查询条件统计列表的数量
	 * @param qc 查询条件
	 * @return 列表数量
	 */
	@Cacheable(value="tabulationCache",key="'countTabulation:'+#qc+'Count'")
	public int countTabulation(QueryCriteria qc);

	/**
	 * Description:保存列表信息
	 * @param tabulation 列表
	 * @return
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public Long doSaveTabulation(Tabulation tabulation);

	/**
	 * Description:更新列表信息
	 * @param tabulation 列表
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulation(Tabulation tabulation);

	/**
	 * Description:更新列表详细信息
	 * @param tabulation 列表
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationDetails(Tabulation tabulation);

	/**
	 * Description:更新列表路径
	 * @param tabulation 列表
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationPath(Tabulation tabulation);

	/**
	 * Description:删除列表
	 * @param selectedIDs 列表ID数组
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulations(Long[] selectedIDs);

	/**
	 * Description:根据列表ID查找列表
	 * @param id 列表ID
	 * @return 列表
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationById:'+#id")
	public Tabulation findTabulationById(Long id);

	/**
	 * Description:根据查询条件查询列表字段
	 * @param queryCriteria 查询条件
	 * @return 列表字段集合
	 */
	@Cacheable(value="tabulationCache",key="'queryTabulationColumn:'+#qc+'TabulationColumn'")
	public List queryTabulationColumn(QueryCriteria queryCriteria);

	/**
	 * Description:根据查询条件统计列表字段
	 * @param queryCriteria 查询条件
	 * @return 列表字段数量
	 */
	@Cacheable(value="tabulationCache",key="'countTabulationColumn:'+#qc+'TabulationColumnCount'")
	public int countTabulationColumn(QueryCriteria queryCriteria);

	/**
	 * Description:根据查询条件查询列表筛选条件
	 * @param queryCriteria 查询条件
	 * @return 列表筛选条件集合
	 */
	@Cacheable(value="tabulationCache",key="'queryTabulationQuery:'+#qc+'TabulationQuery'")
	public List queryTabulationQuery(QueryCriteria queryCriteria);

	/**
	 * Description:根据查询条件统计列表筛选条件
	 * @param queryCriteria 查询条件
	 * @return 列表筛选条件数量
	 */
	@Cacheable(value="tabulationCache",key="'countTabulationQuery:'+#qc+'TabulationQueryCount'")
	public int countTabulationQuery(QueryCriteria queryCriteria);

	/**
	 * Description:更新列表字段信息
	 * @param tabulationColumn 列表字段
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationColumn(TabulationColumn tabulationColumn);

	/**
	 * Description:保存列表字段到数据库
	 * @param tabulationColumn 列表字段
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doSaveTabulationColumn(TabulationColumn tabulationColumn);

	/**
	 * Description:删除列表字段
	 * @param selectedIDs 列表字段ID数组
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationColumns(Long[] selectedIDs);

	/**
	 * Description:根据列表字段ID查找列表字段
	 * @param id 列表字段ID
	 * @return 列表字段
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public TabulationColumn findTabulationColumnById(Long id);

	/**
	 * Description:根据列表ID和查询条件查询列表
	 * @param id 列表ID
	 * @param qc 查询条件
	 * @param user 用户信息
	 * @return 列表
	 */
//	@Cacheable(value="tabulationCache",key="'findTabulationById:'+#id+'-QueryCriteria:'+#qc+#user.id")
//	public Tabulation findTabulationById(Long id, QueryCriteria qc,SysUser user);

	/**
	 * Description:根据列表ID和查询条件查询列表
	 * @param id 列表ID
	 * @param qc 查询条件
	 * @param user 用户信息
	 * @return 列表
	 */
	@Cacheable(value="domainCache",key="'findTabulationByListId:'+#id+'-QueryCriteria:'+#qc+#user.id")
	public Tabulation findTabulationByListId(Long id, QueryCriteria qc, SysUser user);

	/**
	 * Description:根据编码查询列表
	 * @param seqcode 编码
	 * @return 列表
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationBySeqcode:'+#seqcode")
	public Tabulation findTabulationBySeqcode(String seqcode);

	/**
	 * Description:根据ID查找列表筛选条件
	 * @param tabulationQueryId 列表筛选条件ID
	 * @return 列表筛选条件
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationQueryById:'+#tabulationQueryId")
	public TabulationQuery findTabulationQueryById(Long tabulationQueryId);

	/**
	 * Description:删除列表筛选条件
	 * @param selectedIDs 列表筛选条件ID数组
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationQuerys(Long[] selectedIDs);

	/**
	 * Description:更新列表筛选条件信息
	 * @param tabulationQuery 列表筛选条件
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationQuery(TabulationQuery tabulationQuery);

	/**
	 * Description:保存列表筛选条件信息
	 * @param tabulationQuery 列表筛选条件
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doSaveTabulationQuery(TabulationQuery tabulationQuery);

	/**
	 * Description:根据列表ID查询列表筛选条件
	 * @param tabulationId 列表ID
	 * @return 列表筛选条件集合
	 */
	@Cacheable(value="tabulationCache",key="'selectQueryByTabulationId:'+#tabulationId")
	public List<TabulationQuery> selectQueryByTabulationId(Long tabulationId);

	/**
	 * Description:根据查询条件查询列表操作按钮
	 * @param queryCriteria 查询条件
	 * @return 列表操作按钮集合
	 */
	@Cacheable(value="tabulationCache",key="'queryTabulationOpt:'+#queryCriteria")
	public List queryTabulationOpt(QueryCriteria queryCriteria);

	/**
	 * Description:根据查询条件统计列表操作按钮
	 * @param queryCriteria 查询条件
	 * @return 列表操作按钮数量
	 */
	public int countTabulationOpt(QueryCriteria queryCriteria);

	/**
	 * Description:删除列表操作按钮
	 * @param selectedIDs 列表操作按钮ID数组
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationOpts(Long[] selectedIDs);

	/**
	 * Description:更新列表操作按钮信息
	 * @param tabulationOpt 列表操作按钮
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationOpt(TabulationOpt tabulationOpt);

	/**
	 * Description:保存列表操作按钮
	 * @param tabulationOpt 列表操作按钮
	 * @return 列表操作按钮ID
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public Long doSaveTabulationOpt(TabulationOpt tabulationOpt);

	/**
	 * Description:根据ID查找列表操作按钮
	 * @param tabulationOptId 列表操作按钮ID
	 * @return 列表操作按钮
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationOptById:'+#tabulationOptId")
	public TabulationOpt findTabulationOptById(Long tabulationOptId);
	
	/**
	 * Description:查询某个表是否有某个字段
	 * @param tableName 表名称
	 * @param columnName 字段名称
	 * @return boolean
	 */
	public boolean tableHasColumn(String tableName,String columnName);

	/**
	 * Description:查询表单是否已经被其他列表引用，若返回的List集合大于0，则已经被其他列表引用
	 * @param formId 表单ID
	 * @param id 列表ID
	 * @return 列表集合
	 */
	@Cacheable(value="tabulationCache",key="'useFormCount:'+#formId+#id")
	public List useFormCount(Long formId, Long id);
	
	/**
	 * Description:根据列表ID查找列表操作按钮集合
	 * @param tabulationId 列表ID
	 * @return 列表操作按钮集合
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationOpt:'+#tabulationId")
	public List<TabulationOpt> findTabulationOpt(Long tabulationId);
	
	/**
	 * Description:根据列表ID查找列表按钮集合
	 * @param tabulationId 列表ID
	 * @return 列表按钮集合
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationButton:'+#tabulationId")
	public List<TabulationButton> findTabulationButton(Long tabulationId);

	/**
	 * Description:更新列表按钮显示顺序
	 * @param tabulationButton 列表按钮
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateButtonOrder(TabulationButton tabulationButton,Long tabulationButtonId);

	/**
	 * Description:更新列表操作按钮的显示顺序
	 * @param tabulationOpt 列表操作按钮
	 * @param tabulationOptId 列表操作按钮ID
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doUpdateOptOrder(TabulationOpt tabulationOpt, Long tabulationOptId);
	
	/**
	 * Description:根据表单ID查询列表集合
	 * @param formid 表单ID
	 * @return 列表集合
	 */
	@Cacheable(value="tabulationCache",key="'queryTabulationByFormid:'+#formid")
	public List<Tabulation> queryTabulationByFormid(Long formid);

	/**
	 * Description:删除列表
	 * @param selectedIDs 列表ID数组
	 */
	@CacheEvict(value={"tabulationCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void doLogicDeleteTabulations(Long[] selectedIDs);
	
	/**
	 * Description:根据列表ID和查询条件查询列表,列表中只包含列表信息和列表筛选条件信息,不受默认分页组件影响，可自己设置每页记录数
	 * @param id 列表ID
	 * @param qc 查询条件
	 * @param user 用户信息
	 * @return 列表
	 */
	public Tabulation selectNoPageTabulationByListId(Long id, QueryCriteria qc, SysUser user);
	
	/**
	 * 
	 * Description:
	 * 根据列表ID和查询条件查询列表,列表中只包含列表信息和列表筛选条件信息,不受默认分页组件影响，可自己设置每页记录数。同时将所有的列都查询出来。
	 * @param id 列表id
	 * @param qc 查询条件
	 * @param user 当前用户
	 * @return 列表
	 */
	public Tabulation selectNoPageAllColumnsTabulationByListId(Long id,QueryCriteria qc,SysUser user);
	
}
