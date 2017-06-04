package com.cloudstong.platform.resource.form.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.model.DomainVO;
import com.cloudstong.platform.resource.form.model.Form;
import com.cloudstong.platform.resource.form.model.FormColumn;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.template.model.Partition;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:表单服务接口
 */
public interface FormService {
	/**
	 * 保存表单到数据库
	 * @param form
	 *            表单
	 * @return 保存的记录的ID
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public Long doSaveForm(Form form);

	/**
	 * 根据Id删除表单
	 * @param id
	 *            表单ID
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteForm(Long id);

	/**
	 * 更新表单
	 * @param form
	 *            表单
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doUpdateForm(Form form);

	/**
	 * 根据Id查询表单
	 * @param id
	 *            表单ID
	 * @return 表单
	 */
	public Form findFormById(Long id);

	/**
	 * 根据查询条件查询表单集合
	 * @param qc
	 *            查询条件
	 * @return 表单集合
	 */
	public List<Form> queryForm(QueryCriteria qc);

	/**
	 * 根据查询条件获得表单总记录数
	 * @param qc
	 *            查询条件
	 * @return 表单总记录数
	 */
	public int countForm(QueryCriteria qc);

	/**
	 * 通过查询条件获得表单元素总记录数
	 * @param qc
	 *            查询条件
	 * @return 表单元素总记录数
	 */
	public int countFormColumn(QueryCriteria qc);

	/**
	 * 删除单个或多个表单
	 * @param selectedIDs
	 *            表单ID数组
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteForms(Long[] selectedIDs);

	/**
	 * 根据查询条件查询表单元素
	 * @param queryCriteria
	 *            查询条件
	 * @return 表单元素集合
	 */
	public List queryFormColumn(QueryCriteria queryCriteria);

	/**
	 * 根据ID查找表单元素
	 * @param id
	 *            表单元素ID
	 * @return 表单元素
	 */
	public FormColumn findFormColumnById(Long id);

	/**
	 * 更新表单元素
	 * @param formColumn
	 *            表单元素
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doUpdateFormColumn(FormColumn formColumn);

	/**
	 * 添加表单元素
	 * @param formColumn
	 *            表单元素
	 * @return 添加的表单元素的ID
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public Long doSaveFormColumn(FormColumn formColumn);

	/**
	 * 批量删除表单元素
	 * @param selectedIDs
	 *            表单元素ID数组
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteFormColumns(Long[] selectedIDs);

	/**
	 * 删除表单元素
	 * @param id
	 *            表单元素ID
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteFormColumn(Long id);

	/**
	 * 得到当前表单Id
	 * @return 当前表单ID
	 */
	public Long getCurrentFormId();

	/**
	 * 根据formId和domains集合查询Form以及Form相关的其他对象。
	 * @param id
	 *            表单ID
	 * @param domains
	 * @param user
	 *            用户信息
	 * @return 表单
	 * @throws Exception
	 */
	@Cacheable(value = "formCache", key = "'findFormByIdAndDomainVO:'+#id+#domains.toString()+#user.id")
	public Form findFormByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user) throws Exception;

	/**
	 * 根据formId和domains集合查询Form以及Form相关的其他对象。
	 * @param id
	 *            表单ID
	 * @param domains
	 * @param user
	 *            用户信息
	 * @return 表单
	 */
	@Cacheable(value = "domainCache", key = "'getFormByIdAndDomainVO:'+#id+#domains.toString()+#user.id")
	public Form getFormByIdAndDomainVO(Long id, List<DomainVO> domains, SysUser user);

	/**
	 * 根据表单编码查询表单
	 * @param seqcode
	 *            表单编码
	 * @return 表单
	 */
	@Cacheable(value = "formCache", key = "'findFormBySeqcode:'+#seqcode")
	public Form findFormBySeqcode(String seqcode);

	/**
	 * 查找分区
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
	 * 根据formId和domains集合查询Form以及Form相关的其他对象,表单字段对象为Edit状态。
	 * @param id
	 *            表单ID
	 * @param domains
	 * @return 表单
	 */
	@Cacheable(value = "formCache", key = "'getEditFormByIdAndDomainVO:'+#formId+#domainVOs.toString()+#user.id")
	public Form getEditFormByIdAndDomainVO(Long formId, List<DomainVO> domainVOs, SysUser user);

	/**
	 * 根据构件ID和录入类型查找构件
	 * @param compexId
	 *            构件ID
	 * @param inputType
	 *            录入类型
	 * @return 构件集合
	 */
	@Cacheable(value = "formCache", key = "'findCompById:'+#compexId+#inputType")
	public List findCompById(Long compexId, int inputType);

	/**
	 * 根据构件ID和录入类型查找构件是否使用过，若返回的List大于0,则使用过
	 * @param compexId
	 *            构件ID
	 * @param inputType
	 *            录入类型
	 * @return 构件集合
	 */
	@Cacheable(value = "formCache", key = "'findIsUse:'+#compexId+#inputType")
	public List findIsUse(Long compexId, String inputType);

	/**
	 * 根据按钮组ID查询此按钮组是否被使用过
	 * @param buttonGroupId
	 *            按钮组ID
	 * @return 是否使用过
	 */
	@Cacheable(value = "formCache", key = "'findIsUseButtonGroup:'+#buttonGroupId")
	public String findIsUseButtonGroup(Long buttonGroupId);

	/**
	 * 根据按钮ID查询此按钮是否被使用过
	 * @param buttonId
	 *            按钮ID
	 * @return 是否使用过
	 */
	@Cacheable(value = "formCache", key = "'findIsUseButton:'+#buttonId")
	public String findIsUseButton(Long buttonId);

	/**
	 * 根据查询组件ID查询此查询组件是否被使用过
	 * @param queryId
	 *            查询组件ID
	 * @return 是否使用过
	 */
	@Cacheable(value = "formCache", key = "'findIsUseQuery:'+#queryId")
	public String findIsUseQuery(Long queryId);

	/**
	 * 根据列表组件ID查询此列表组件是否被使用过
	 * @param queryId
	 *            列表组件ID
	 * @return 是否使用过
	 */
	@Cacheable(value = "formCache", key = "'findIsUseList:'+#listId")
	public String findIsUseList(Long listId);

	/**
	 * 查询列表选择列，操作列,序号列等是否被使用过
	 * @param controlId
	 *            构件ID
	 * @param columnname
	 *            选择列，操作列，序号列等在列表组件表中的字段名称
	 * @return 是否使用过
	 */
	@Cacheable(value = "formCache", key = "'findIsUseListControl:'+#controlId+#columnname")
	public String findIsUseListControl(Long controlId, String columnname);

	/**
	 * 根据录入类型查找默认的组件
	 * @param inputType
	 *            录入类型
	 * @return 组件ID
	 */
	@Cacheable(value = "formCache", key = "'findDefaultCompex:'+#inputType")
	public Long findDefaultCompex(int inputType);

	/**
	 * 根据字段ID统计字段在表单设计中用的次数
	 * @param columnId
	 *            字段ID
	 * @return 字段在表单设计中用的次数
	 */
	@Cacheable(value = "formCache", key = "'countColumnInForm:'+#columnId")
	public int countColumnInForm(Long columnId);

	/**
	 * Description:根据表单ID查找表单信息，只包含表单信息
	 * @param id
	 *            表单ID
	 */
	@Cacheable(value = "formCache", key = "'findSimpleFormById:'+#id")
	public Form findSimpleFormById(Long id);

	/**
	 * Description:根据选项卡ID查找表单字段集合
	 * @param tabId
	 *            选项卡ID
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache", key = "'findFormColumnByTabId:'+#tabId")
	public List<FormColumn> findFormColumnByTabId(Long tabId);

	/**
	 * Description:根据选项卡ID和分区ID查找表单字段集合
	 * @param tabId
	 *            选项卡ID
	 * @param pId
	 *            分区ID
	 * @return 表单字段集合
	 */
	@Cacheable(value = "formCache", key = "'findFormColumnByTabAndPid:'+#tabId+#pId")
	public List<FormColumn> findFormColumnByTabAndPid(Long tabId, Long pId);

	/**
	 * Description:查找表单字段是否重复添加，返回的List大于0，则表示重复添加
	 * @param formColumn
	 *            表单字段
	 * @return List
	 */
	@Cacheable(value = "formCache")
	public List findReplyColumn(FormColumn formColumn);

	/**
	 * Description:根据所属表ID查询表单集合
	 * @param tableid
	 *            所属表ID
	 * @return 表单集合
	 */
	@Cacheable(value = "formCache", key = "'queryFormByTableid:'+#tableid")
	public List<Form> queryFormByTableid(Long tableid);

	/**
	 * 根据高级查询组件ID查询此高级查询组件是否被使用过
	 * @param queryId
	 *            高级查询组件ID
	 * @return 是否使用过
	 */
	@Cacheable(value = "formCache", key = "'findIsUseAdvQuery:'+#queryId")
	public String findIsUseAdvQuery(Long queryId);

	/**
	 * Description:根据表单ID查找表单下的所有字段
	 * @param id
	 *            表单ID
	 * @return
	 */
	@Cacheable(value = "formCache", key = "'findFormColumnByFormId:'+#id")
	public List findFormColumnByFormId(Long id);

	/**
	 * 根据Id删除表单
	 * @param id
	 *            表单ID
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doLogicDeleteForm(Long id);

	/**
	 * 删除单个或多个表单
	 * @param selectedIDs
	 *            表单ID数组
	 */
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doLogicDeleteForms(Long[] selectedIDs);

}
