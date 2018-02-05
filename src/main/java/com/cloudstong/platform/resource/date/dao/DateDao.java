package com.cloudstong.platform.resource.date.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.date.model.DateControl;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:日期组件操作数据库接口
 */
public interface DateDao {
	/**
	 * 向数据库中插入一条日期组件记录
	 * 
	 * @param date
	 *            日期组件
	 * @return 插入的记录ID
	 * @throws Exception
	 */
	public Long insert(DateControl date) throws Exception;

	/**
	 * 更新数据库中的一条日期组件记录
	 * 
	 * @param date
	 *            日期组件
	 * @return 更新的记录的ID
	 * @throws Exception
	 */
	public Long update(DateControl date) throws Exception;

	/**
	 * 根据日期组件ID查找日期组件
	 * 
	 * @param id
	 *            日期组件ID
	 * @return 日期组件
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache", key = "'Date_findById:'+#id")
	public DateControl findById(Long id) throws Exception;

	/**
	 * 查询日期组件列表
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param startRow
	 *            当前页
	 * @param rowsCount
	 *            每页记录数
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow, int rowsCount) throws Exception;

	/**
	 * 统计日期组件的数量
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @return 日期组件的数量
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache", key = "'Date_getTotalCount:'+#where.hashCode()+#args")
	public int getTotalCount(String where, Object[] args) throws Exception;

	/**
	 * 根据日期组件ID删除日期组件
	 * 
	 * @param id
	 *            日期组件ID
	 * @return 是否删除成功
	 * @throws Exception
	 */
	public int delete(Long id) throws Exception;

	/**
	 * 查询录入类型包含日期组件的表单
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
	 * @throws Exception
	 */
	public List selectFormList(String where, Object[] args, int startRow, int rowsCount) throws Exception;

	/**
	 * 根据查询条件统计录入类型包含日期组件的表单数量
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @return 表单数量
	 * @throws Exception
	 */
	public int countFormList(String where, Object[] args) throws Exception;
}
