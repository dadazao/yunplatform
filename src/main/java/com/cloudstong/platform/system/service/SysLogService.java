/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service;

import java.util.List;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.model.SysLog;

/**
 * @author michael
 * Created on 2012-12-6
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统日志服务接口
 * 
 */
public interface SysLogService {
	/**
	 * Description:保存一条系统日志
	 * @param sysLog 系统日志
	 */
	public void doSaveLog(SysLog sysLog);

	/**
	 * Description:根据表名称查询日志
	 * 
	 * Steps:
	 * 
	 * @param qc
	 * @param logTableName
	 * @return
	 */
	public List<SysLog> queryLog(QueryCriteria qc, String logTableName);

	/**
	 * Description:判断数据库中是否存在某张表
	 * 
	 * Steps:
	 * 
	 * @return
	 */
	public Boolean checkTableExists(String logTableName);

	/**
	 * Description:
	 * 
	 * Steps:
	 * 
	 * @param queryCriteria
	 * @param logTableName
	 * @return
	 */
	public int countLog(QueryCriteria queryCriteria, String logTableName); 
	
}
