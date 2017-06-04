package com.cloudstong.platform.resource.querycontrol.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

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
 * Description:查询组件操作数据库接口
 */
public interface QueryControlDao {
	/**
	 * 查询所有的查询组件
	 * @return 查询组件集合
	 * @throws Exception
	 */
	List<QueryControl> selectAllQueryControl();
	
	/**
	 * 根据ID查询记录
	 * @return 查询组件
	 * @param queryControlId
	 * @throws Exception
	 */
	@Cacheable(value="resourceCache",key="'findQueryControlById:'+#queryControlId")
	QueryControl findQueryControlById(String queryControlId);
	
	/**
	 * 查询高级查询组件
	 * @return 高级查询组件集合
	 */
	List<AdvanceQueryControl> selectAllAdvanceQueryControl();
	
	/**
	 * 根据ID查询记录
	 * @param advanceQueryControlId 高级查询组件ID
	 * @return 高级查询组件
	 * @throws Exception
	 */
	@Cacheable(value="resourceCache",key="'findAdvanceQueryControlById:'+#advanceQueryControlId")
	AdvanceQueryControl findAdvanceQueryControlById(Long advanceQueryControlId);

}
