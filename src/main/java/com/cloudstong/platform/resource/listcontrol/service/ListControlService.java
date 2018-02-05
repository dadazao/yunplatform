package com.cloudstong.platform.resource.listcontrol.service;

import java.util.List;

import com.cloudstong.platform.resource.listcontrol.model.ListControl;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表组件服务接口
 */
public interface ListControlService {
	/**
	 * Description:查询所有的列表组件
	 * @return 列表组件集合
	 * @throws Exception
	 */
	public List<ListControl> findAllListControl() throws Exception;

	/**
	 * Description:根据列表组件ID查找列表组件
	 * @param listControlId 列表组件ID
	 * @return 列表组件
	 * @throws Exception
	 */
	public ListControl getListControlById(Long listControlId)
			throws Exception;
}
