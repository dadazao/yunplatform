/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.DataSourcePojo;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author liuqi
 * Created on 2014-10-9 15:14:38
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
public interface DataSourceService {

	/**
	 * Description:数据源切换
	 * 
	 * Steps:
	 * 
	 * @param psId 数据源ID
	 * @return 切换状态标识
	 */
	public String doChangeDataBase(String psId);
	
	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param dataSource
	 * @param user
	 * @return
	 */
	Long doSaveDataSource(DataSourcePojo dataSource, SysUser user);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param id
	 * @return
	 */
	DataSourcePojo findDataSourceById(Long id);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param dataSourceIDs
	 */
	void doDeleteDataSources(Long[] dataSourceIDs);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @return
	 */
	PageResult queryDataSource(QueryCriteria queryCriteria);


}
