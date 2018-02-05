package com.cloudstong.platform.resource.template.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.template.model.Partition;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分区操作数据库接口
 */
public interface PartitionDao {
	/**
	 * Description:根据模板ID统计分区
	 * @param templateId 模板ID
	 * @return 分区数量
	 */
	public int count(String templateId);

	/**
	 * Description:查询分区
	 * @param sql sql语句
	 * @param params 参数值
	 * @param startRow 当前页
	 * @param rowsCount 每页记录数
	 * @return 分区集合
	 */
	public List select(String sql, Object[] params, int startRow, int rowsCount);

	/**
	 * Description:添加分区
	 * @param partition 分区
	 */
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void insert(Partition partition);

	/**
	 * Description:修改分区
	 * @param partition 分区
	 */
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void update(Partition partition);

	/**
	 * Description:删除分区
	 * @param id 分区ID
	 */
	@CacheEvict(value = { "tabulationCache", "formCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void delete(Long id);

	/**
	 * Description:根据分区ID查找分区
	 * @param id 分区ID
	 * @return 分区
	 */
	@Cacheable(value = "formCache")
	public Partition selectById(Long id);
}
