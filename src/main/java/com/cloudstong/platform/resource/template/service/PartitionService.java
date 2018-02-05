package com.cloudstong.platform.resource.template.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.resource.template.model.Partition;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:分区服务接口
 */
public interface PartitionService {
	/**
	 * Description:根据模板ID统计分区
	 * @param templateId 模板ID
	 * @return 分区数量
	 */
	public int countPartition(String templateId);

	/**
	 * Description:根据查询条件查询分区
	 * @param queryCriteria 查询条件
	 * @return 分区集合
	 */
	public List queryPartition(QueryCriteria queryCriteria);

	/**
	 * Description:保存分区
	 * @param partition 分区
	 */
	public void doSavePartition(Partition partition);

	/**
	 * Description:更新分区
	 * @param partition 分区
	 */
	public void doUpdatePartition(Partition partition);

	/**
	 * Description:根据分区ID查找分区
	 * @param id 分区ID
	 * @return 分区
	 */
	public Partition findPartitionById(Long id);

	/**
	 * Description:删除分区
	 * @param selectedIDs 分区ID数组
	 */
	public void doDeletePartitions(Long[] selectedIDs);

}
