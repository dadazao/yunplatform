package com.cloudstong.platform.resource.form.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:表单操作数据库接口
 */
public interface FormDao {
	/**
	 * Description:查询表单集合
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param startRow
	 *            当前页
	 * @param rowsCount
	 *            每页记录数
	 * @return 表单集合
	 */
	@Cacheable(value = "formCache")
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * Description:更新表单
	 * 
	 * @param form
	 *            表单
	 */
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void update(Form form);

	/**
	 * Description:向数据库插入一个表单
	 * 
	 * @param form
	 *            表单
	 * @return 表单ID
	 */
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public Long insert(Form form);

	/**
	 * Description:根据表单ID查询表单
	 * 
	 * @param id
	 *            表单ID
	 * @return 表单
	 */
	@Cacheable(value = "formCache", key = "'form_selectById:'+#id")
	public Form selectById(Long id);

	/**
	 * Description:根据表单Id和domains集合查询Form以及Form相关的其他对象。
	 * 
	 * @param id
	 *            表单ID
	 * @param domains
	 * @param user
	 *            用户信息
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value = "formCache", key = "'selectByIdAndDomainVO:'+#id+'-'+#domains+#user.id")
	public Form selectByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user) throws Exception;

	/**
	 * Description:根据表单Id和domains集合查询Form以及Form相关的其他对象。
	 * 
	 * @param id
	 *            表单ID
	 * @param domains
	 * @param user
	 *            用户信息
	 * @return 表单
	 */
	public Form getByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user);

	/**
	 * Description:根据表单ID删除表单
	 * 
	 * @param id
	 *            表单ID
	 */
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void delete(Long id);

	/**
	 * Description:获得表单总记录数
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @return 表单总记录数
	 */
	@Cacheable(value = "formCache")
	public int count(String sql, Object[] array);

	/**
	 * Description:获得表单字段总记录数
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @return 表单总记录数
	 */
	@Cacheable(value = "formCache")
	public int countFormColumn(String sql, Object[] array);

	/**
	 * Description:查询表单字段集合
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @param currentIndex
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache")
	public List selectFormColumn(String sql, Object[] array, int currentIndex, int pageSize);

	/**
	 * Description:根据表单字段ID查找表单字段
	 * 
	 * @param id
	 *            表单字段ID
	 * @return 表单字段
	 */
	@Cacheable(value = "formCache", key = "'selectFormColumnById:'+#id")
	public FormColumn selectFormColumnById(Long id);

	/**
	 * Description:修改表单字段
	 * 
	 * @param formColumn
	 *            表单字段
	 */
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void updateFormColumn(FormColumn formColumn);

	/**
	 * Description:向数据库中插入一个表单字段
	 * 
	 * @param formColumn
	 *            表单字段
	 * @return 表单字段ID
	 */
	@CacheEvict(value={"tabulationCache","formCache","domainCache"},allEntries=true,beforeInvocation=true)
	public Long insertFormColumn(FormColumn formColumn);

	/**
	 * Description:根据表单字段ID删除表单字段
	 * 
	 * @param id
	 *            表单字段ID
	 */
	@CacheEvict(value={"tabulationCache","formCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void deleteColumn(Long id);

	/**
	 * Description:查找最大的表单ID，目前所有的ID都采用UUID，此方法已废弃，不建议使用
	 * 
	 * @return 表单ID
	 */
	public Long selectMaxId();

	/**
	 * Description:根据选项卡ID查找表单字段集合
	 * 
	 * @param tabId
	 *            选项卡ID
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache", key = "'selectByTabId:'+#tabId")
	public List<FormColumn> selectByTabId(Long tabId);

	/**
	 * Description:根据选项卡ID查找可编辑的表单字段集合
	 * 
	 * @param tabId
	 *            选项卡ID
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache", key = "'selectEditByTabId:'+#tabId")
	public List<FormColumn> selectEditByTabId(String tabId);

	/**
	 * Description:根据选项卡ID和分区ID查找表单字段集合
	 * 
	 * @param tabId
	 *            选项卡ID
	 * @param partitionId
	 *            分区ID
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache", key = "'selectEditByTabId:'+#tabId+#partitionId")
	public List<FormColumn> selectByTabAndPid(Long tabId, Long partitionId);

	/**
	 * Description:根据选项卡ID查找表单字段集合
	 * 
	 * @param tabId
	 *            选项卡ID
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache", key = "'selectListByTabId:'+#tabId")
	public List<FormColumn> selectListByTabId(String tabId);

	/**
	 * Description:根据编码查找表单
	 * 
	 * @param seqcode
	 *            编码
	 * @return 表单
	 */
	@Cacheable(value = "formCache", key = "'selectFormBySeqcode:'+#seqcode")
	public Form selectFormBySeqcode(String seqcode);

	/**
	 * Description:查找分区及分区关联对象的信息
	 * 
	 * @param model
	 *            表名称
	 * @param tabId
	 *            选项卡ID
	 * @param partitionId
	 *            分区ID
	 * @param subDomainId
	 *            子记录ID
	 * @param user
	 *            用户信息
	 * @return 分区
	 */
	@Cacheable(value = "formCache", key = "'findDomainById:'+#model+#tabId+#partitionId+#subDomainId+#user.id")
	public Partition findDomainById(String model, Long tabId, Long partitionId, Long subDomainId, SysUser user);

	/**
	 * Description:根据表单Id和domains集合查询Form以及Form相关的其他对象，Form关联的formcolumn为Edit状态。
	 * 
	 * @param id
	 *            表单ID
	 * @param domains
	 * @param user
	 *            用户信息
	 * @return 表单
	 */
	@Cacheable(value = "formCache", key = "'getEditByIdAndDomainVO:'+#id+#domainVOs+#user.id")
	public Form getEditByIdAndDomainVO(Long id, List<DomainVO> domainVOs, SysUser user);

	/**
	 * Description:根据构件ID和录入类型查找构件
	 * 
	 * @param compexId
	 *            构件ID
	 * @param inputType
	 *            录入类型
	 * @return 构件集合
	 */
	@Cacheable(value = "formCache", key = "'getCompById:'+#compexId+#inputType")
	public List getCompById(Long compexId, int inputType);

	/**
	 * Description:根据构件ID和录入类型查找构件是否使用过，若返回的List大于0,则使用过
	 * 
	 * @param compexId
	 *            构件ID
	 * @param inputType
	 *            录入类型
	 * @return 构件集合
	 */
	public List findIsUse(Long compexId, String inputType);

	/**
	 * Description:根据按钮组ID查询此按钮组是否被使用过
	 * 
	 * @param buttonGroupId
	 *            按钮组ID
	 * @return 是否使用过
	 */
	public String findIsUseButtonGroup(Long buttonGroupId);

	/**
	 * Description:根据按钮ID查询此按钮是否被使用过
	 * 
	 * @param buttonId
	 *            按钮ID
	 * @return 是否使用过
	 */
	public String findIsUseButton(Long buttonId);

	/**
	 * Description:根据查询组件ID查询此查询组件是否被使用过
	 * 
	 * @param queryId
	 *            查询组件ID
	 * @return 是否使用过
	 */
	public String findIsUseQuery(Long queryId);

	/**
	 * Description:根据列表组件ID查询此列表组件是否被使用过
	 * 
	 * @param queryId
	 *            列表组件ID
	 * @return 是否使用过
	 */
	public String findIsUseList(Long listId);

	/**
	 * Description:查询列表选择列，操作列,序号列等是否被使用过
	 * 
	 * @param controlId
	 *            构件ID
	 * @param columnname
	 *            选择列，操作列，序号列等在列表组件表中的字段名称
	 * @return 是否使用过
	 */
	public String findIsUseListControl(Long controlId, String columnname);

	/**
	 * Description:根据录入类型获得默认的组件
	 * 
	 * @param inputType
	 *            录入类型
	 * @return 组件ID
	 */
	@Cacheable(value = "formCache", key = "'getDefaultCompex:'+#inputType")
	public Long getDefaultCompex(int inputType);

	/**
	 * Description:根据字段ID统计字段在表单设计中用的次数
	 * 
	 * @param columnId
	 *            字段ID
	 * @return 字段在表单设计中用的次数
	 */
	public int countColumnInForm(Long columnId);

	/**
	 * Description:根据表单ID查询哪些列表关联了此表单
	 * 
	 * @param formId
	 *            表单ID
	 * @return 列表集合
	 */
	@Cacheable(value = "formCache", key = "'findTabuByFormId:'+#formId")
	public List findTabuByFormId(Long formId);

	/**
	 * Description:根据表单ID查找表单信息，只包含表单信息
	 * 
	 * @param id
	 *            表单ID
	 * @return
	 */
	@Cacheable(value = "formCache", key = "'findSimpleFormById:'+#id")
	public Form findSimpleFormById(Long id);

	/**
	 * Description:查找表单字段是否重复添加，返回的List大于0，则表示重复添加
	 * 
	 * @param formColumn
	 *            表单字段
	 * @return List
	 */
	public List findReplyColumn(FormColumn formColumn);

	/**
	 * Description:根据所属表ID查询表单集合
	 * 
	 * @param tableid
	 *            所属表ID
	 * @return 表单集合
	 */
	@Cacheable(value = "formCache", key = "'queryFormByTableid:'+#tableid")
	public List<Form> queryFormByTableid(Long tableid);

	/**
	 * 根据高级查询组件ID查询此高级查询组件是否被使用过
	 * 
	 * @param queryId
	 *            高级查询组件ID
	 * @return 是否使用过
	 */
	public String findIsUseAdvQuery(Long queryId);

	/**
	 * Description:根据表单ID查找表单下的所有字段
	 * 
	 * @param id
	 *            表单ID
	 * @return
	 */
	@Cacheable(value = "formCache", key = "'findFormColumnByFormId:'+#id")
	public List findFormColumnByFormId(Long id);

	/**
	 * Description:根据表单ID删除表单
	 * 
	 * @param id
	 *            表单ID
	 */
	@CacheEvict(value={"tabulationCache","formCache","domainCache"},allEntries=true,beforeInvocation=true)
	public void logicDelete(Long id);

}
