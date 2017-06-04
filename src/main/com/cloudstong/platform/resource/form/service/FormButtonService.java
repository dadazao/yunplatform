package com.cloudstong.platform.resource.form.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.form.model.FormButton;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单按钮服务接口
 */
public interface FormButtonService {
	/**
	 * 更新表单按钮或表单按钮组
	 * @param formButton
	 */
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public void doUpdateFormButton(FormButton formButton);

	/**
	 * 添加表单按钮或表单按钮组
	 * @param formButton
	 */
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public Long doSaveFormButton(FormButton formButton);

	/**
	 * 根据查询条件查询表单按钮
	 * @param queryCriteria 查询条件
	 * @return 表单按钮集合
	 */
	@Cacheable(value="formCache",key="'queryFormButton:'+#queryCriteria")
	public List queryFormButton(QueryCriteria queryCriteria);

	/**
	 * 根据查询条件统计表单按钮的数量
	 * @param queryCriteria 查询条件
	 * @return 表单按钮的数量
	 */
	@Cacheable(value="formCache",key="'countFormButton:'+#queryCriteria")
	public int countFormButton(QueryCriteria queryCriteria);

	/**
	 * 根据表单按钮ID查找表单按钮
	 * @param id 表单按钮ID
	 * @return 表单按钮
	 */
	@Cacheable(value="formCache",key="'findFormButtonById:'+#id")
	public FormButton findFormButtonById(Long id);

	/**
	 * 批量删除表单按钮
	 * @param selectedIDs 要删除的表单按钮ID数组
	 */
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public void doDeleteFormButtons(Long[] selectedIDs);

	/**
	 * 查询表单按钮是否重复添加，若List集合大于0，则视为重复添加
	 * @param formButton 表单按钮
	 * @return List集合
	 */
	@Cacheable(value="formCache")
	public List findReplyResult(FormButton formButton);
	
	/**
	 * Description:通过表单ID和选项卡ID查找表单按钮，返回表单按钮集合，若需查询整个表单的表单按钮，则tabId设置成-1
	 * @param formId 表单ID
	 * @param tabId 选项卡ID
	 * @return 表单按钮集合
	 */
	@Cacheable(value="formCache",key="'findFormButton:'+#formId+#tabId")
	public List<FormButton> findFormButton(Long formId,Long tabId);
	
	/**
	 * Description:通过选项卡ID和分区ID查找表单按钮集合
	 * @param tabId 选项卡ID
	 * @param pId 分区ID
	 * @return 表单按钮集合
	 */
	@Cacheable(value="formCache",key="'findFormButtonByTabAndPid:'+#tabId+#pId")
	public List<FormButton> findFormButtonByTabAndPid(Long tabId,Long pId);

	/**
	 * Description:更新表单按钮显示顺序
	 * @param formButton 表单按钮
	 */
	@CacheEvict(value="formCache",allEntries=true,beforeInvocation=true)
	public void doUpdateOrder(FormButton formButton);
	
	/**
	 * Description:通过表单ID和选项卡ID查找表单按钮，返回表单按钮集合，若需查询整个表单的表单按钮，则tabId设置成-1
	 * @param formId 表单ID
	 * @param tabId 选项卡ID
	 * @return 表单按钮集合
	 */
	public List<FormButton> findFormButton(Long formId);
}
