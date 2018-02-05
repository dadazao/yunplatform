package com.cloudstong.platform.resource.date.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.date.model.DateControl;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:日期组件服务接口
 */
public interface DateService {
	
	/**
	 * 保存或更新日期组件
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Long doSaveOrUpdate(DateControl date) throws Exception;

	/**
	 * 根据日期组件ID查找日期组件
	 * @param id 日期组件ID
	 * @return 日期组件
	 * @throws Exception
	 */
	public DateControl findById(Long id) throws Exception;

	/**
	 * 根据日期组件ID删除日期组件
	 * @param id 日期组件ID
	 * @return 是否删除成功
	 * @throws Exception
	 */
	public String doDelete(Long id) throws Exception;

	/**
	 * 根据查询条件查询日期组件列表
	 * @param queryCriteria 查询条件
	 * @return 日期组件列表
	 * @throws Exception
	 */
	public List queryForPageList(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据查询条件统计日期组件的数量
	 * @param queryCriteria 查询条件
	 * @return 日期组件的数量
	 * @throws Exception
	 */
	public int getTotalCount(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据查询条件查询录入类型包含日期组件的表单
	 * @param queryCriteria 查询条件
	 * @return 表单集合
	 * @throws Exception
	 */
	public List queryFormList(QueryCriteria queryCriteria) throws Exception;

	/**
	 * 根据查询条件统计录入类型包含日期组件的表单数量
	 * @param queryCriteria 查询条件
	 * @return 表单数量
	 * @throws Exception
	 */
	public int countFormList(QueryCriteria queryCriteria) throws Exception;
}
