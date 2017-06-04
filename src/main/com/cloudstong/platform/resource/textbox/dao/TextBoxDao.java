package com.cloudstong.platform.resource.textbox.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.textbox.model.TextBox;

/**
 * @author michael Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:文本框操作数据库接口
 */
public interface TextBoxDao {

	/**
	 * Description:保存文本框信息
	 * 
	 * @param textBox
	 *            文本框
	 * @throws Exception
	 */
	public long insert(TextBox textBox) throws Exception;

	/**
	 * Description:修改文本框信息
	 * 
	 * @param textBox
	 *            文本框
	 * @throws Exception
	 */
	public long update(TextBox textBox) throws Exception;

	/**
	 * Description:根据文本框ID查找文本框
	 * 
	 * @param ID
	 *            文本框ID
	 * @return 文本框
	 */
	@Cacheable(value = "resourceCache", key = "'TextBox_findByID:'+#id")
	public TextBox findByID(Long id);

	/**
	 * Description:查询文本框
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param startRow
	 *            当前页
	 * @param rowsCount
	 *            每页记录数
	 * @return 文本框集合
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache")
	public List queryForPageList(String where, Object[] args, int startRow, int rowsCount) throws Exception;

	/**
	 * Description:统计文本框
	 * 
	 * @param where
	 *            sql语句
	 * @param args
	 *            参数值
	 * @return 文本框数量
	 * @throws Exception
	 */
	@Cacheable(value = "resourceCache")
	public int getTotalCount(String where, Object[] args) throws Exception;

	/**
	 * Description:根据文本框ID删除文本框
	 * 
	 * @param ID
	 *            文本框
	 * @return 成功标识
	 * @throws Exception
	 */
	public int delete(Long id) throws Exception;
}
