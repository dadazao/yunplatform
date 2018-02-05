package com.cloudstong.platform.resource.buttongroup.dao;

import java.util.List;

import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.buttongroup.model.ButtonAndGroup;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 按钮和按钮组中间表对象操作数据库接口
 */
public interface ButtonAndGroupDao {

	/**
	 * 向按钮和按钮组的中间表中插入一条记录
	 * @param buttonAndGroup
	 * @return 按钮和按钮组的中间表记录ID
	 * @throws Exception
	 */
	public Long insert(ButtonAndGroup buttonAndGroup) throws Exception;

	/**
	 * 更新按钮和按钮组的中间表中的一条记录
	 * @param buttonAndGroup
	 * @return 操作是否成功
	 * @throws Exception
	 */
	public int update(ButtonAndGroup buttonAndGroup) throws Exception;

	/**
	 * 更新按钮和按钮组的中间表的删除标识
	 * @param buttonAndGroupID 中间表ID
	 * @return 是否成功
	 * @throws Exception
	 */
	public int updateStatus(String buttonAndGroupID) throws Exception;
	
	/**
	 * 根据ID删除按钮和按钮组的中间表的一条记录
	 * @param buttonAndGroupID 中间表ID
	 * @return 是否删除成功
	 */
	public int delete(Long buttonAndGroupID);

	/**
	 * 查询按钮和按钮组的中间表记录的集合
	 * @param where sql语句
	 * @param args 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 按钮和俺喝酒组中间表记录的集合
	 * @throws Exception
	 */
	public List queryForPageList(String where, Object[] args, int startRow,
			int rowsCount) throws Exception;

	/**
	 * 统计按钮和按钮组的中间表记录的数量
	 * @param where sql语句
	 * @param args 参数值
	 * @return 中间表记录的数量
	 * @throws Exception
	 */
	public int getTotalCount(String where, Object[] args) throws Exception;

	/**
	 * 根据ID查找按钮和按钮组的中间表记录
	 * @param buttonAndGroupID 中间表ID
	 * @return 按钮和按钮组的中间表记录
	 * @throws Exception
	 */
	public ButtonAndGroup findByID(String buttonAndGroupID) throws Exception;

	/**
	 * 根据按钮组ID查找按钮和按钮组的中间表记录集合
	 * @param buttonGroupId 按钮组ID
	 * @return 按钮和按钮组的中间表记录集合
	 */
	public List<ButtonAndGroup> selectByButtonGroupId(String buttonGroupId);

	/**
	 * 统计某个按钮组中按钮的数量
	 * @param buttonGroupId 按钮组ID
	 * @return 按钮数量
	 */
	public int countButton(Long buttonGroupId);
}
