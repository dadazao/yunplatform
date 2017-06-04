package com.cloudstong.platform.resource.buttongroup.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:按钮组服务接口
 */
public interface ButtonGroupService {

	/**
	 * 保存或更新按钮组
	 * @param buttonGroup
	 * @return
	 * @throws Exception
	 */
	public Long doSaveAndUpdate(ButtonGroup buttonGroup) throws Exception;

	/**
	 * 根据查询条件查找按钮组集合
	 * @param queryCriteria 查询条件
	 * @return 按钮组集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据查询条件统计按钮组数量
	 * @param queryCriteria 查询条件
	 * @return 按钮组数量
	 * @throws Exception
	 */
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据按钮组ID查找按钮组
	 * @param buttonGroupID 按钮组ID
	 * @return 按钮组
	 * @throws Exception
	 */
	public ButtonGroup findByID(Long buttonGroupID) throws Exception;

	/**
	 * 根据按钮组ID删除按钮组
	 * @param ID 按钮组ID
	 * @return ID
	 * @throws Exception
	 */
	public String doDelete(Long id) throws Exception;

	/**
	 * 根据查询条件查找按钮组集合
	 * @param queryCriteria 按钮组
	 * @return 按钮组集合
	 * @throws Exception
	 */
	/**
	 * 根据查询条件查找按钮组集合
	 * @param queryCriteria 查询条件
	 * @return 按钮组集合
	 * @throws Exception
	 */
	public List queryButtonGroup(QueryCriteria queryCriteria) throws Exception;
}
