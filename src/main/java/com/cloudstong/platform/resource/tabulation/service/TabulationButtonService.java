package com.cloudstong.platform.resource.tabulation.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表按钮服务接口
 */
public interface TabulationButtonService {
	/**
	 * Description:更新列表按钮
	 * @param tabulationButton 列表按钮
	 */
	@CacheEvict(value="tabulationCache",allEntries=true,beforeInvocation=true)
	public void doUpdateTabulationButton(TabulationButton tabulationButton);

	/**
	 * Description:保存列表按钮
	 * @param tabulationButton 列表按钮
	 * @return 列表按钮ID
	 */
	@CacheEvict(value="tabulationCache",allEntries=true,beforeInvocation=true)
	public Long doSaveTabulationButton(TabulationButton tabulationButton);

	/**
	 * Description:根据查询条件查询列表按钮
	 * @param queryCriteria 查询条件
	 * @return 列表按钮集合
	 */
	@Cacheable(value="tabulationCache",key="'queryTabulationButton:'+#queryCriteria")
	public List queryTabulationButton(QueryCriteria queryCriteria);

	/**
	 * Description:根据查询条件统计列表按钮
	 * @param queryCriteria 查询条件
	 * @return 列表按钮数量
	 */
	@Cacheable(value="tabulationCache",key="'countTabulationButton:'+#queryCriteria")
	public int countTabulationButton(QueryCriteria queryCriteria);

	/**
	 * Description:根据ID查找列表按钮
	 * @param id 列表按钮ID
	 * @return 列表按钮
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationButtonById:'+#id")
	public TabulationButton findTabulationButtonById(Long id);

	/**
	 * Description:删除列表按钮
	 * @param selectedIDs 列表按钮ID数组
	 */
	@CacheEvict(value="tabulationCache",allEntries=true,beforeInvocation=true)
	public void doDeleteTabulationButtons(Long[] selectedIDs);

	/**
	 * Description:根据ID查找列表操作按钮
	 * @param id 列表操作按钮ID
	 * @return 列表操作按钮
	 */
	@Cacheable(value="tabulationCache",key="'findTabulationOptById:'+#id")
	public TabulationOpt findTabulationOptById(Long id);

	/**
	 * Description:查找列表按钮是否重复添加，若返回的List集合大于0，则为重复添加
	 * @param tabulationButton 列表按钮
	 * @return List
	 */
	@Cacheable(value="tabulationCache")
	public List findReplyResult(TabulationButton tabulationButton);

	/**
	 * Description:查找列表操作按钮是否重复添加，若返回的List集合大于0，则为重复添加
	 * @param tabulationOpt 列表操作按钮
	 * @return List
	 */
	@Cacheable(value="tabulationCache")
	public List findReplyOptResult(TabulationOpt tabulationOpt);
}
