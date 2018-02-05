/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao;

import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.system.model.DataSourcePojo;

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
public interface DataSourceDao extends BaseJdbcDao<DataSourcePojo, Long> {
	/**
	 * Description:数据源切换
	 * 
	 * Steps:
	 * 
	 * @param psId 数据源ID
	 * @return 切换状态标识
	 */
	public String doChangeDataBase(String psId);
}
