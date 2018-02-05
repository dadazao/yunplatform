package com.cloudstong.platform.system.service;

import java.io.IOException;
import java.util.List;

import com.cloudstong.platform.system.model.DataSearch;

/**
 * 
 * @author sam
 * 
 */

public interface IDataSearchService {	
	
	
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
