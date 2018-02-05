package com.cloudstong.platform.resource.listcontrol.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.resource.listcontrol.model.ListControl;

/**
 * @author michael
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表组件操作数据库接口
 */
public interface ListControlDao {
	/**
	 * Description:查询所有的列表组件
	 * @return 列表组件集合
	 */
	public List<ListControl> selectAllListControl();
	
	/**
	 * Description:根据列表组件ID查找列表组件
	 * @param listControlId 列表组件ID
	 * @return 列表组件
	 */
	@Cacheable(value="resourceCache",key="'findListControlById:'+#listControlId")
	public ListControl findListControlById(Long listControlId);
}
