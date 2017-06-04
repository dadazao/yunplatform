package com.cloudstong.platform.resource.metadata.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.Domain;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael
 * Created on 2012-11-13
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:配置模块操作数据库接口
 */
public interface CompexDomainDao {
	/**
	 * 更新数据库中的一条记录
	 * @param domain 通用数据模型
	 */
	public void update(Domain domain,SysUser user);

	/**
	 * 向数据库中插入一条记录
	 * @param domain 通用数据模型
	 * @return 插入的这条记录的ID
	 */
	public Long insert(Domain domain,SysUser user);
	
	/**
	 * 向中间表中插入一条记录
	 * @param table 中间表的名称
	 * @param mainid 主表记录ID
	 * @param subid 次表记录ID
	 */
	public void insertMiddleTable(String table, Long mainid, Long subid);

	/**
	 * 删除中间表中的一条记录
	 * @param table 中间表的名称
	 * @param mainId 主表记录ID
	 * @param subId 次表记录ID
	 */
	public void deleteMiddleTable(String table, Long mainId, Long subId);

	/**
	 * 删除数据库中的一条记录
	 * @param domain 表名称
	 * @param id 记录ID
	 */
	public void delete(String domain, Long id, Long formId, SysUser user);

	/**
	 * 删除中间表中tbl_subid为id的数据
	 * @param domain 表名称
	 * @param id tbl_subid的值
	 */
	public void deleteTemp(String domain, Long id);

	/**
	 * 根据tabId和partitionId查询出表单字段的列表
	 * @param tabId tab页ID
	 * @param partitionId 分区ID
	 * @return 表单字段集合
	 */
	public List<FormColumnExtend> selectSubListColumn(Long tabId,
			Long partitionId);

	/**
	 * 根据tab页ID和分区ID查询次表的所有记录
	 * @param queryCriteria 查询条件
	 * @param tabId tab页ID
	 * @param partitionId
	 * @param vo 表单参数
	 * @return 通用数据模型集合
	 */
	public List<Domain> selectDomains(QueryCriteria queryCriteria, Long tabId,
			Long partitionId, DomainVO vo,SysUser user);

	/**
	 * 获得子列表总记录数
	 * @param domain 子表名称
	 * @param vo 主表信息，包括主表名称
	 * @return 子列表总记录数
	 */
	public int count(String domain, DomainVO vo);

	/**
	 * 获得列表总记录数
	 * @param domain 表名称
	 * @param vo 主表信息，包括主表名称
	 * @return 子列表总记录数
	 */
	@Cacheable(value = "domainCache", key = "'singleCount:'+#domain+#vo+#relColumnId")
	public int singleCount(String domain, DomainVO vo, Long relColumnId);
	
	/**
	 * 保存子表记录
	 * @param domain 子表通用数据模型
	 * @param domainId 主表记录ID
	 * @param domainTable 主表名称
	 */
	public void doSaveSub(Domain domain, Long domainId, String domainTable, SysUser user);

	/**
	 * 修改子表记录
	 * @param domain 子表通用数据模型
	 */
	public void doUpdateSub(Domain domain,SysUser user);

	/**
	 * 公用数据库记录查询方法
	 * @param psTableName 数据库表名称
	 * @param psParams 查询条件
	 * @return List<Map<String,Map>>集合
	 */
	@Cacheable(value = "domainCache", key = "'queryTableData:'+#psTableName+#psParams")
	public List queryTableData(String psTableName, String psParams);
	
	/**
	 * 公用数据库记录删除方法
	 * @param psTableName 数据库表名称
	 * @param psParams 删除条件
	 */
	public void deleteTableData(String psTableName, String psParams);
	
	/**
	 * 查找字段值在数据库中是否已经存在，若id不为空则需排除此条记录，返回的List大于0则表示已经存在
	 * @param belongTable 表名称
	 * @param columnName 字段名称
	 * @param value 字段值
	 * @param id 记录id
	 * @return List集合
	 */
	public List findReplyResult(String belongTable, String columnName, String value ,Long id);

	/**
	 * 设置为默认
	 * @param mainTable 表名称
	 * @param column 默认的字段名称
	 * @param _sId 记录ID
	 */
	public void setdefault(String mainTable, String column, Long psId);

	/**
	 * 发布
	 * @param tableName 表名称
	 * @param recordId 记录ID
	 */
	public void passed(String tableName,Long recordId);

	/**
	 * 撤回
	 * @param tableName 表名称
	 * @param recordId 记录ID
	 */
	public void recall(String tableName, Long recordId);

	/**
	 * 更新编码
	 * @param tableName 表名称
	 * @param columnName 编码字段名
	 * @param id 记录ID
	 * @param code 编码
	 */
	public void updateCode(String tableName, String columnName, Long id, String code);

	/**
	 * 多文件上传组件移除文件
	 * @param model 表名称
	 * @param column 字段名称
	 * @param fileid 要移除的文件ID
	 * @param recordid 记录ID
	 */
	public void removeFile(String model, String column, Long fileid, Long recordid);

	/**
	 * 查找在同一父节点下树名称在数据库中是否存在，若_domainId不为空则需排除此条记录，返回的List大于0则表示已经存在
	 * @param _tableName 表名称
	 * @param _name 树名称
	 * @param _parentId 父节点ID
	 * @param _domainId 记录ID
	 * @return List集合
	 */
	public List findTreeTypeReply(String _tableName, String _name,
			Long _parentId, Long _domainId);

	/**
	 * 修改子记录时更新父记录的修改人和修改时间
	 * @param tableName 表名称
	 * @param mainId 主记录ID
	 * @param user 当前登录用户
	 */
	public void updateMainInfo(String tableName, Long mainId, SysUser user);

	/**
	 * Description:公用数据库记录更新方法,更新单个字段
	 * @param tableName 表名称
	 * @param columnName 字段名称
	 * @param value 
	 * @param id
	 */
	public void updateTableData(String tableName, String columnName, String value, Long id);

	/**
	 * Description:公用数据库记录更新方法,更新多个字段
	 * @param tableName 表名称
	 * @param columnNames 字段名称列表
	 * @param values 字段值列表
	 * @param id
	 */
	public void updateTableData(String tableName, List<String> columnNames, List<String> values, Long id);

	/**
	 * 查找和主表有关系的次表的所有记录
	 * @param queryCriteria 查询条件
	 * @param _form 表单信息
	 * @param tabId tab页ID
	 * @param associationColumn 关联字段
	 * @param vo 表单参数
	 * @return 通用数据模型集合
	 */
	public List<Domain> selectSingleDomain(QueryCriteria queryCriteria, Long tabId, DomainVO vo, SysUser user);
	
	/**
	 * 
	 * Description:
	 * 
	 * 根据条件查询表的所有记录
	 * 
	 * @param queryCriteria
	 * @param model
	 * @param user
	 * @return
	 */
	public List<Domain> selectDomains(QueryCriteria queryCriteria, String model, SysUser user);

	/**
	 * 删除数据库中的一条记录(逻辑删除)
	 * @param domain 表名称
	 * @param domainId 记录ID
	 */
	public void logicDelete(String table, Long domainId, Long formId, SysUser user);
	
	/**
	 * Description:统计列表的数量
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @return 列表数量
	 */
	@Cacheable(value = "domainCache")
	public int count(String sql, Object[] args);

	public boolean hasDefaultCol(String model);

	public boolean isDefaultData(String model, String column, Long domainId);

	public void saveFileter(String filterName, String tableName, String queryCriteria);

	@Cacheable(value = "domainCache")
	public List selectData(String sql, Object[] array);

	@Cacheable(value = "domainCache")
	public List selectData(String sql, Object[] array, int currentIndex, int pageSize);

	public Object queryColumnValue(String model, String column, Long domainId);
}