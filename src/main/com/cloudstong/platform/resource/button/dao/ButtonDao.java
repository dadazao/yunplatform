package com.cloudstong.platform.resource.button.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.resource.button.model.Button;

/**
 * @author michael Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description: 按钮操作数据库接口
 */
public interface ButtonDao {

	/**
	 * 向按钮表中插入一条记录
	 * 
	 * @param button
	 *            按钮信息
	 * @return 插入的记录的ID
	 * @throws Exception
	 */
	public Long insert(Button button) throws Exception;

	/**
	 * 更新按钮表的的一条记录
	 * 
	 * @param button
	 *            要更新的按钮信息
	 * @return 更新的记录的ID
	 * @throws Exception
	 */
	public long update(Button button) throws Exception;

	/**
	 * 根据按钮ID查找按钮
	 * 
	 * @param Id
	 *            按钮ID
	 * @return 按钮
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache")
	public Button findByID(Long ID);

	/**
	 * 查询按钮的集合
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param startRow
	 *            当前页
	 * @param rowsCount
	 *            每页记录数
	 * @return 按钮集合
	 */
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * 查询按钮的集合
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param startRow
	 *            当前页
	 * @param rowsCount
	 *            每页记录数
	 * @return 按钮集合
	 */
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * 统计按钮的数量
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @return 按钮数量
	 * @throws Exception
	 */
	public int getTotalCount(String where, Object[] args) throws Exception;

	/**
	 * 根据按钮ID删除ID
	 * 
	 * @param ID
	 *            按钮ID
	 * @return 删除是否成功
	 * @throws Exception
	 */
	public int delete(Long id) throws Exception;
}
