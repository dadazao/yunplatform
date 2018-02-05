package com.cloudstong.platform.resource.tabulation.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;

/**
 * @author michael Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:列表按钮操作数据库接口
 */
public interface TabulationButtonDao {

	/**
	 * Description:更新列表按钮
	 * 
	 * @param tabulationButton
	 *            列表按钮
	 */
	public void updateTabulationButton(TabulationButton tabulationButton);

	/**
	 * Description:保存列表按钮
	 * 
	 * @param tabulationButton
	 *            列表按钮
	 * @return 列表按钮ID
	 */
	public Long insertTabulationButton(TabulationButton tabulationButton);

	/**
	 * Description:查询列表按钮
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @param currentIndex
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return 列表按钮集合
	 */
	@Cacheable(value = "tabulationCache")
	public List selectTabulationButton(String sql, Object[] array, int currentIndex, int pageSize);

	/**
	 * Description:统计列表按钮
	 * 
	 * @param sql
	 *            sql语句
	 * @param array
	 *            参数值
	 * @return 列表按钮数量
	 */
	@Cacheable(value = "tabulationCache")
	public int countTabulationButton(String sql, Object[] array);

	/**
	 * Description:根据ID查找列表按钮
	 * 
	 * @param id
	 *            列表按钮ID
	 * @return 列表按钮
	 */
	public TabulationButton selectTabulationButtonById(Long id);

	/**
	 * Description:删除列表按钮
	 * 
	 * @param id
	 *            列表按钮ID
	 */
	public void deleteTabulationButton(Long id);

	/**
	 * Description:删除列表按钮
	 * 
	 * @param id
	 *            列表ID
	 */
	public void deleteTabulationButtonByTabulationId(Long id);

	/**
	 * Description:根据列表ID查询列表按钮
	 * 
	 * @param tabulationId
	 *            列表ID
	 * @return 列表按钮集合
	 */
	@Cacheable(value = "tabulationCache", key = "'selectByTabulationId:'+#tabulationId")
	public List<TabulationButton> selectByTabulationId(Long tabulationId);

	/**
	 * Description:根据ID查找列表操作按钮
	 * 
	 * @param id
	 *            列表操作按钮ID
	 * @return 列表操作按钮
	 */
	public TabulationOpt selectTabulationOptById(Long id);

	/**
	 * Description:查找列表按钮是否重复添加，若返回的List集合大于0，则为重复添加
	 * 
	 * @param tabulationButton
	 *            列表按钮
	 * @return List
	 */
	public List findReplyResult(TabulationButton tabulationButton);

	/**
	 * Description:查找列表操作按钮是否重复添加，若返回的List集合大于0，则为重复添加
	 * 
	 * @param tabulationOpt
	 *            列表操作按钮
	 * @return List
	 */
	public List findReplyOptResult(TabulationOpt tabulationOpt);

	/**
	 * Description:更新列表按钮显示顺序
	 * 
	 * @param tabulationButton
	 *            列表按钮
	 * @param tabulationButton
	 *            列表按钮ID
	 */
	public void updateButtonOrder(TabulationButton tabulationButton, Long tabulationButtonId);

	/**
	 * Description:更新列表操作按钮显示顺序
	 * 
	 * @param tabulationButton
	 *            列表操作按钮
	 * @param tabulationButton
	 *            列表操作按钮ID
	 */
	public void updateOptOrder(TabulationOpt tabulationOpt, Long tabulationOptId);
}
