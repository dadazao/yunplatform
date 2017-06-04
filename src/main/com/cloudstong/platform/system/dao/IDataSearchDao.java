package com.cloudstong.platform.system.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.cloudstong.platform.system.model.DataSearch;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:数据查询数据库接口
 */
public interface IDataSearchDao {
	
	
	/**
	 * Description:获取所有的数据查询策略
	 * 
	 * Steps:
	 * 
	 * @param plRoleId
	 *           用户的角色ID
	 * @param plTableId
	 *           用户所要操作的模块/表
	 * @return
	 */
	@Cacheable(value="resourceCache",key="'getDataSearchContent:'+#plRoleId+#plTableId")
	public List<DataSearch> getDataSearchContent(String plRoleId, String plTableId);
	
	/**
	 * Description:获取查询策略表数据
	 * 
	 * Steps:
	 * 
	 * @param psColumnName
	 *           需要依赖的字段
	 * @param plColumnValue
	 *           字段对应的值
	 * @return
	 */
	public List<DataSearch> queryDataSearch(String psColumnName, String plColumnValue);
	
	/**
	 * Description:测试查询策略所需要的sql语句是否正确
	 * 
	 * Steps:
	 * 
	 * @param psSql
	 *           需要测试的sql语句
	 * @return
	 * @throws IOException
	 */
	public boolean testSql(String psSql);
}
