package com.cloudstong.platform.resource.querycontrol.service;

import java.util.List;

import com.cloudstong.platform.resource.querycontrol.model.AdvanceQueryControl;
import com.cloudstong.platform.resource.querycontrol.model.QueryControl;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 查询组件服务接口
 */
public interface QueryControlService {
	/**
	 * 查询所有的查询组件
	 * @return 查询组件集合
	 * @throws Exception
	 */
	public List<QueryControl> findAllQueryControl() throws Exception;

	/**
	 * 根据ID查询记录
	 * @param queryControlId
	 * @return 查询组件
	 * @throws Exception
	 */
	public QueryControl getQueryControlById(String queryControlId)
			throws Exception;

	/**
	 * 查询高级查询组件
	 * @return 高级查询组件集合
	 */
	public List<AdvanceQueryControl> findAllAdvanceQueryControl();

	/**
	 * 根据ID查询记录
	 * @param valueOf 高级查询组件ID
	 * @return 高级查询组件
	 * @throws Exception
	 */
	public AdvanceQueryControl getAdvanceQueryControlById(Long advanceQueryControlId);
}
