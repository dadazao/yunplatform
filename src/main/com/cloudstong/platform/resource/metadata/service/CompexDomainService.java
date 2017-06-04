package com.cloudstong.platform.resource.metadata.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-13
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:配置模块服务接口
 */
public interface CompexDomainService {
	/**
	 * 保存一条记录到数据库
	 * @param domain
	 *            通用数据模型
	 * @return 保存的这条记录的ID
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public Long doSaveDomain(Domain domain, SysUser user);

	/**
	 * 更新数据库中的一条记录
	 * @param domain
	 *            通用数据模型
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateDomain(Domain domain, SysUser user);

	/**
	 * 保存或者更新数据库中的一条记录以及其子记录
	 * @param domains
	 *            通用数据模型集合，一般只有一条数据
	 * @param tables
	 *            主表和子表的集合
	 * @param vos
	 *            表单参数集合，包括主表和子表的信息
	 * @return 保存或更新的记录的ID集合，一般第一条为主记录
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public List<Long> doSaveOrUpdateDomain(List<Domain> domains, List<String> tables, List<DomainVO> vos, SysUser user);

	/**
	 * 删除数据库中的一条或多条记录
	 * @param ds
	 *            表单参数集合
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doDeleteDomains(List<DomainVO> ds, Long formId, SysUser user);

	/**
	 * 删除数据库中的一条或多条记录
	 * @param ds
	 *            表单参数集合
	 * @param mainTable
	 *            主表名称
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doDeleteDomains(List<DomainVO> ds, String mainTable, Long formId, SysUser user);

	/**
	 * 保存过滤器
	 * 
	 * @param filterName
	 * @param tableName
	 * @param queryCriteria
	 */
	public void doSaveFilter(String filterName, String tableName, String queryCriteria);

	/**
	 * 根据tabId和partitionId查询出表单字段的列表
	 * @param tabId
	 *            tab页ID
	 * @param partitionId
	 *            分区ID
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache", key = "'findSubListColumn:'+#tabId+#partitionId")
	public List<FormColumnExtend> findSubListColumn(Long tabId, Long partitionId);

	/**
	 * 根据tab页ID和分区ID查询次表的所有记录
	 * @param queryCriteria
	 *            查询条件
	 * @param tabId
	 *            tab页ID
	 * @param partitionId
	 * @param vo
	 *            表单参数
	 * @return 通用数据模型集合
	 */
	@Cacheable(value = "domainCache", key = "'queryDomain:'+#queryCriteria+#tabId+#partitionId+#vo.domainId+#user.id")
	public List<Domain> queryDomain(QueryCriteria queryCriteria, Long tabId, Long partitionId, DomainVO vo, SysUser user);

	/**
	 * 根据条件的所有记录
	 * @param queryCriteria
	 *            查询条件
	 * @param user
	 *            用户
	 * @return 通用数据模型集合
	 */
	@Cacheable(value = "domainCache", key = "'queryDomain:'+#queryCriteria+#model+#user.id")
	public List<Domain> queryDomain(QueryCriteria queryCriteria, String model, SysUser user);

	/**
	 * 获得子列表总记录数
	 * @param domain
	 *            子表名称
	 * @param vo
	 *            主表信息，包括主表名称
	 * @return 子列表总记录数
	 */
	@Cacheable(value = "domainCache", key = "'countDomain:'+#domain+#vo.domainId")
	public int countDomain(String domain, DomainVO vo);

	/**
	 * 获得列表总记录数
	 * @param domain
	 *            表名称
	 * @param vo
	 *            主表信息，包括主表名称
	 * @param relColumnId
	 *            关联字段
	 * @return 列表总记录数
	 */
	@Cacheable(value = "domainCache", key = "'singleCountDomain:'+#domain+#vo.domainId+#relColumnId")
	public int singleCountDomain(String domain, DomainVO vo, Long relColumnId);

	/**
	 * 保存子表记录
	 * @param domain
	 *            子表通用数据模型
	 * @param domainId
	 *            主表记录ID
	 * @param domainTable
	 *            主表名称
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doSaveSub(Domain domain, Long domainId, String domainTable, SysUser user);

	/**
	 * 修改子表记录
	 * @param domain
	 *            子表通用数据模型
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateSub(Domain domain, SysUser user);

	/**
	 * 公用数据库记录查询方法
	 * @param psTableName
	 *            数据库表名称
	 * @param psParams
	 *            查询条件
	 * @return List<Map<String,Map>>集合
	 */
	@Cacheable(value = "domainCache", key = "'queryTableData:'+#psTableName+#psParams")
	public List queryTableData(String psTableName, String psParams);

	/**
	 * 公用数据库记录删除方法
	 * @param psTableName
	 *            数据库表名称
	 * @param psParams
	 *            删除条件
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doDeleteTableData(String psTableName, String psParams);

	/**
	 * 查找字段值在数据库中是否已经存在，若id不为空则需排除此条记录，返回的List大于0则表示已经存在
	 * @param belongTable
	 *            表名称
	 * @param columnName
	 *            字段名称
	 * @param value
	 *            字段值
	 * @param id
	 *            记录id
	 * @return List集合
	 */
	public List findReplyResult(String belongTable, String columnName, String value, Long id);

	/**
	 * 设置为默认
	 * @param mainTable
	 *            表名称
	 * @param column
	 *            默认的字段名称
	 * @param _sId
	 *            记录ID
	 */
	public void doDefault(String mainTable, String column, Long _sId);

	/**
	 * 发布
	 * @param tableName
	 *            表名称
	 * @param recordId
	 *            记录ID
	 */
	public void doPassed(String tableName, Long recordId);

	/**
	 * 撤回
	 * @param tableName
	 *            表名称
	 * @param recordId
	 *            记录ID
	 */
	public void doRecall(String tableName, Long recordId);

	/**
	 * 更新编码
	 * @param tableName
	 *            表名称
	 * @param columnName
	 *            编码字段名
	 * @param id
	 *            记录ID
	 * @param code
	 *            编码
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateCode(String tableName, String columnName, Long id, String code);

	/**
	 * 多文件上传组件移除文件
	 * @param model
	 *            表名称
	 * @param column
	 *            字段名称
	 * @param fileid
	 *            要移除的文件ID
	 * @param recordid
	 *            记录ID
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doRemoveFile(String model, String column, Long fileid, Long recordid);

	/**
	 * 查找在同一父节点下树名称在数据库中是否存在，若_domainId不为空则需排除此条记录，返回的List大于0则表示已经存在
	 * @param _tableName
	 *            表名称
	 * @param _name
	 *            树名称
	 * @param _parentId
	 *            父节点ID
	 * @param _domainId
	 *            记录ID
	 * @return List集合
	 */
	public List findTreeTypeReply(String _tableName, String _name, Long _parentId, Long _domainId);

	/**
	 * 修改子记录时更新父记录的修改人和修改时间
	 * @param tableName
	 *            表名称
	 * @param mainId
	 *            主记录ID
	 * @param user
	 *            当前登录用户
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateMainInfo(String tableName, Long mainId, SysUser user);

	/**
	 * Description:公用数据库记录更新方法,更新单个字段
	 * @param tableName
	 *            表名称
	 * @param columnName
	 *            字段名称
	 * @param value
	 * @param id
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTableData(String tableName, String columnName, String value, Long id);

	/**
	 * Description:公用数据库记录更新方法,更新多个字段
	 * @param tableName
	 *            表名称
	 * @param columnNames
	 *            字段名称列表
	 * @param values
	 *            字段值列表
	 * @param id
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTableData(String tableName, List<String> columnNames, List<String> values, Long id);

	/**
	 * @author Jason 2012-12-13 Description: 保存多条记录到数据库 Steps:
	 * 
	 * @param domainList
	 *            数据记录List
	 * @param user
	 *            当前用户
	 * @return
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public int doSaveDomainList(List<Domain> domainList, SysUser user);

	/**
	 * 查找和主表有关系的次表的所有记录
	 * @param queryCriteria
	 *            查询条件
	 * @param _form
	 *            表单信息
	 * @param tabId
	 *            tab页ID
	 * @param vo
	 *            表单参数
	 * @return 通用数据模型集合
	 */
	@Cacheable(value = "domainCache", key = "'querySingleDomain:'+#queryCriteria+#tabId+#vo+#user.id")
	public List<Domain> querySingleDomain(QueryCriteria queryCriteria, Long tabId, DomainVO vo, SysUser user);

	public void doGeneratorCode(Long currentSaveId, List<Domain> domains, Form form, HttpServletRequest request);

	/**
	 * 删除数据库中的一条或多条记录(逻辑删除)
	 * @param ds
	 *            表单参数集合
	 */
	@CacheEvict(value = "domainCache", allEntries = true, beforeInvocation = true)
	public void doLogicDeleteDomains(List<DomainVO> ds, Long formId, SysUser user);

	/**
	 * 是否有默认值
	 * @param model
	 * @return
	 */
	public boolean hasDefaultCol(String model);

	/**
	 * 是否是默认值
	 * @param model
	 * @param column
	 * @param domainId
	 * @return
	 */
	public boolean isDefaultData(String model, String column, Long domainId);
	
	/**
	 * 查询字段对应的值
	 * @param model
	 * @param column
	 * @param domainId
	 * @return
	 */
	public Object queryColumnValue(String model, String column, Long domainId);
}
