package com.cloudstong.platform.resource.form.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.form.model.Tab;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- ------- ----------------------------------------------------
 * 
 *         Description:选项卡服务接口
 */

public interface TabService {

	/**
	 * Description:根据查询条件查询选项卡的集合
	 * @param queryCriteria
	 *            查询条件
	 * @return 选项卡集合
	 */
	@Cacheable(value = "formCache")
	public List<Tab> queryTab(QueryCriteria queryCriteria);

	/**
	 * Description:根据查询条件统计选项卡的数量
	 * @param queryCriteria
	 *            查询条件
	 * @return 选项卡数量
	 */
	@Cacheable(value = "formCache")
	public int countTab(QueryCriteria queryCriteria);

	/**
	 * Description:根据选项卡ID查找选项卡
	 * @param id
	 *            选项卡ID
	 * @return 选项卡
	 */
	@Cacheable(value = "formCache")
	public Tab findTabById(Long id);

	/**
	 * Description: 更新选项卡信息
	 * @param tab
	 *            选项卡
	 */
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTab(Tab tab);

	/**
	 * Description:获取当前的选项卡ID，此方法已废弃，不建议使用
	 * @return 选项卡ID
	 */
	public Long getCurrentTabId();

	/**
	 * Description:删除一个或多个选项卡
	 * @param selectedIDs
	 *            选项卡ID数组
	 */
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doDeleteTabs(Long[] selectedIDs);

	/**
	 * Description:保存选项卡
	 * @param tab
	 *            选项卡
	 * @return 选项卡ID
	 */
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public Long doSaveTab(Tab tab);

	/**
	 * Description:删除选项卡
	 * @param id
	 *            选项卡ID
	 */
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doDeleteTab(Long id);

	/**
	 * Description:查询是否有重复的选项卡,若有重复的，则返回的List长度大于0
	 * @param tableName
	 *            表名称
	 * @param columnName
	 *            字段名称
	 * @param tabName
	 *            选项卡名称
	 * @param id
	 *            选项卡ID
	 * @param formId
	 *            表单ID
	 * @return List
	 */
	@Cacheable(value = "formCache", key = "'findReplyResult:'+#tableName+#columnName+#tabName+#id+#formId")
	public List findReplyResult(String tableName, String columnName, String tabName, Long id, Long formId);

	/**
	 * Description:通过表单ID查找选项卡集合
	 * @param id
	 *            表单ID
	 * @return 选项卡集合
	 */
	@Cacheable(value = "formCache", key = "'findTabListByFormId:'+#id")
	public List<Tab> findTabListByFormId(Long id);

	/**
	 * Description:更新选项卡显示顺序
	 * @param tab
	 *            选项卡
	 * @param tabId
	 *            选项卡ID
	 */
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doUpdateOrder(Tab tab, Long tabId);

	/**
	 * Description:绑定选项卡对应的表
	 * @param tab
	 *            选项卡
	 */
	@CacheEvict(value = "formCache", allEntries = true, beforeInvocation = true)
	public void doUpdateTabTable(Tab tab);

}
