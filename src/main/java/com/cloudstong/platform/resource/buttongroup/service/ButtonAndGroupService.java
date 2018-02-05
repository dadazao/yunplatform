package com.cloudstong.platform.resource.buttongroup.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.buttongroup.model.ButtonAndGroup;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮和按钮组的中间表对象服务接口
 */
public interface ButtonAndGroupService {

	/**
	 * 保存或更新按钮和按钮组的中间表中的记录
	 * @param buttonAndGroup
	 * @return
	 * @throws Exception
	 */
	public Long doSaveAndUpdate(ButtonAndGroup buttonAndGroup) throws Exception;

	/**
	 * 根据查询条件查询按钮和按钮组的中间表中的记录集合
	 * @param queryCriteria 查询条件
	 * @return 按钮和按钮组的中间表中的记录集合
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据查询条件统计按钮和按钮组的中间表中的记录数量
	 * @param queryCriteria 查询条件
	 * @return 按钮和按钮组的中间表中的记录数量
	 * @throws Exception
	 */
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据ID查找按钮和按钮组的中间表记录
	 * @param buttonAndGroupID 中间表ID
	 * @return 按钮和按钮组的中间表记录
	 * @throws Exception
	 */
	public ButtonAndGroup findByID(String buttonAndGroupID) throws Exception;

	/**
	 * 根据ID删除按钮和按钮组的中间表的一条记录
	 * @param buttonAndGroupID 中间表ID
	 * @return 是否删除成功
	 */
	public String doDelete(Long id) throws Exception;
	
	/**
	 * 统计某个按钮组中按钮的数量
	 * @param buttonGroupId 按钮组ID
	 * @return 按钮数量
	 */
	public int countButton(Long buttonGroupId);

}
