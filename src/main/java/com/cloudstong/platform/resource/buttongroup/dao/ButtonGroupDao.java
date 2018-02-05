package com.cloudstong.platform.resource.buttongroup.dao;

import java.util.List;

import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮组操作数据库接口
 */
public interface ButtonGroupDao {

	/**
	 * 向数据库中插入一个按钮组
	 * @param buttonGroup 按钮组
	 * @return 插入的按钮组记录ID
	 * @throws Exception
	 */
	public Long insert(ButtonGroup buttonGroup) throws Exception;

	/**
	 * 更新数据库中的一个按钮组
	 * @param buttonGroup 按钮组
	 * @return 是否成功
	 * @throws Exception
	 */
	public int update(ButtonGroup buttonGroup) throws Exception;

	/**
	 * 根据按钮ID更新按钮组的删除状态
	 * @param buttonGroupID
	 * @return 是否成功
	 * @throws Exception
	 */
	public int updateStatus(Long buttonGroupID) throws Exception;

	/**
	 * 查找按钮组集合
	 * @param where sql语句
	 * @param args 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 按钮组集合
	 * @throws Exception
	 */
	public List queryForPageList(String where, Object[] args, int startRow,
			int rowsCount) throws Exception;

	/**
	 * 统计按钮组数量
	 * @param where sql语句
	 * @param args 参数值
	 * @return 按钮组数量
	 * @throws Exception
	 */
	public int getTotalCount(String where, Object[] args) throws Exception;

	/**
	 * 根据按钮组ID查找按钮组
	 * @param buttonGroupID 按钮组ID
	 * @return 按钮组
	 */
	public ButtonGroup findByID(Long buttonGroupID);

	/**
	 * 查找按钮组集合
	 * @param where sql语句
	 * @param args 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 按钮组结合
	 */
	public List select(String where, Object[] args, int startRow, int rowsCount);
}
