/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.system.dao.SysLogDao;
import com.cloudstong.platform.system.model.SysLog;
import com.cloudstong.platform.system.service.SysLogService;

/**
 * @author michael
 * Created on 2012-12-6
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统日志服务接口实现类
 * 
 */
@Repository("sysLogService")
public class SysLogServiceImpl implements SysLogService {

	@Resource
	private SysLogDao sysLogDao;
	
	public SysLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(SysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}

	@Override
	public void doSaveLog(SysLog sysLog) {
		sysLogDao.doSaveLog(sysLog);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysLog> queryLog(QueryCriteria qc, String logTableName) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select * from "+logTableName+" l where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and l." + entry.getKey() + " like ? ");
			param.add(entry.getValue());
		}
		sql.append("order by l.comm_createDate desc");
		return this.sysLogDao.selectLogs(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean checkTableExists(String logTableName) {
		Boolean b = false;
		List _list = sysLogDao.checkTableExists(logTableName);
		if(_list.size()>0){
			b=true;
		}
		return b;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int countLog(QueryCriteria qc, String logTableName) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from "+logTableName+" l where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			sql.append(" and l." + entry.getKey() + " like ? ");
			param.add(entry.getValue());
		}
		return sysLogDao.countLog(sql.toString(), param.toArray());
	}

}
