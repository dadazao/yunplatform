/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.dao;

import java.util.List;

import com.cloudstong.platform.system.model.SysLog;

/**
 * @author michael
 * Created on 2012-12-6
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统日志操作数据库接口
 * 
 */
public interface SysLogDao {
	/**
	 * Description:插入一条系统日志
	 * @param sysLog 系统日志
	 */
	void doSaveLog(SysLog sysLog);

	/**
	 * Description:根据表名称查询日志
	 * 
	 * Steps:
	 * 
	 * @param string
	 * @param array
	 * @param currentIndex
	 * @param pageSize
	 * @return
	 */
	List<SysLog> selectLogs(String sql, Object[] array, int currentIndex, int pageSize);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param logTableName
	 * @return
	 */
	List checkTableExists(String logTableName);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param string
	 * @param array
	 * @return
	 */
	int countLog(String sql, Object[] array);

}
