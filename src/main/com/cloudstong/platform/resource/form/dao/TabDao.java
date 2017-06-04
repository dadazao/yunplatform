package com.cloudstong.platform.resource.form.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.form.model.Tab;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:选项卡操作数据库接口
 */
public interface TabDao { 

	/**
	 * Description:查询选项卡的集合
	 * @param where sql语句
	 * @param args 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 选项卡集合
	 */
	@Cacheable(value = "formCache")
	public List select(String where, Object[] args, int startRow, int rowsCount);

	/**
	 * Description: 更新选项卡信息
	 * @param tab 选项卡
	 */
	public void update(Tab tab);

	/**
	 * Description:保存选项卡
	 * @param tab 选项卡
	 * @return 选项卡ID
	 */
	public Long insert(Tab tab);

	/**
	 * Description:根据选项卡ID查找选项卡
	 * @param id 选项卡ID
	 * @return 选项卡
	 */
	public Tab selectById(Long id);

	/**
	 * Description:删除选项卡
	 * @param id 选项卡ID
	 */
	public void delete(Long id);

	/**
	 * Description:统计选项卡的数量
	 * @param where sql语句
	 * @param args 参数值
	 * @return 选项卡数量
	 */
	@Cacheable(value = "resourceCache")
	public int count(String where, Object[] args);

	/**
	 * Description:获得当前最大的选项卡ID，目前所有表的ID都改成了UUID，此方法已废弃，不建议使用
	 * @return 选项卡ID
	 */
	public Long selectMaxId();

	/**
	 * Description:根据表单ID查找选项卡集合
	 * @param formId 表单ID 
	 * @return 选项卡集合
	 */
	@Cacheable(value = "formCache", key = "'tab_selectByFormId:'+#formId")
	public List<Tab> selectByFormId(Long formId);

	// 第二版新增方法
	/**
	 * Description:根据表单ID查找
	 * @param formId 表单ID 
	 * @return 选项卡集合
	 */
	@Cacheable(value = "formCache")
	public List<Tab> selectListByFormId(Long formId);

	/**
	 * Description: 根据选项卡ID删除表单字段
	 * @param id 选项卡ID
	 */
	public void deleteColumns(Long id);

	/**
	 * Description:查询是否有重复的选项卡,若有重复的，则返回的List长度大于0
	 * @param tableName 表名称
	 * @param columnName 字段名称
	 * @param tabName 选项卡名称
	 * @param id 选项卡ID
	 * @param formId 表单ID
	 * @return List
	 */
	public List findReplyResult(String tableName, String columnName,
			String tabName, Long id, Long formId);

	/**
	 * Description:更新选项卡显示顺序
	 * @param tab 选项卡
	 * @param tabId 选项卡ID
	 */
	public void updateOrder(Tab tab, Long tabId);

	/**
	 * Description:绑定选项卡对应的表
	 * @param tab 选项卡
	 */
	public void updateTabTable(Tab tab);
}
