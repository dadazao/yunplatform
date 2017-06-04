package com.cloudstong.platform.resource.button.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.button.model.Button;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮服务接口
 */
public interface ButtonService { 
	/**
	 * 保存或更新按钮信息
	 * @param button 按钮信息
	 * @throws Exception
	 */
	public void doSaveOrUpdate(Button button) throws Exception;

	/**
	 * 根据按钮ID查找按钮
	 * @param Id 按钮ID
	 * @return 按钮
	 * @throws Exception
	 */
	public Button findByID(Long Id) throws Exception;

	/**
	 * 根据按钮ID删除ID
	 * @param Id 按钮
	 * @throws Exception
	 */
	public void doDelete(Long id) throws Exception;

	/**
	 * 根据查询条件查询按钮的集合
	 * @param queryCriteria 查询条件
	 * @return 按钮集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据查询条件查询按钮的集合
	 * @param queryCriteria 查询条件
	 * @return 按钮集合
	 * @throws Exception
	 */
	public List queryButton(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据查询条件统计按钮的数量
	 * @param queryCriteria 查询条件
	 * @return 按钮数量
	 * @throws Exception
	 */
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception;
}
