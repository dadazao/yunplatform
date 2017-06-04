package com.cloudstong.platform.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.resource.metadata.model.SchemaColumns;
import com.cloudstong.platform.resource.metadata.model.SchemaTables;
import com.cloudstong.platform.system.dao.MysqlDao;
import com.cloudstong.platform.system.service.MysqlService;

/**
 * @author sam
 * Created on 2014-2-18
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:MYSQL备份与恢复实现
 */
@Repository("mysqlService")
public class MysqlServiceImpl implements MysqlService {

	@Resource
	private MysqlDao mysqlDao;

	@Override
	public List<SchemaTables> getSchemaTables() {
		return mysqlDao.getSchemaTables();
	}

	@Override
	public List<SchemaColumns> getSchemaColumns() {
		return mysqlDao.getSchemaColumns();
	}

	@Override
	public List<Map<String, Object>> getDatas(String tableName) {
		return mysqlDao.getDatas(tableName);
	}

	public void doSaveMysqlBackup(String filename,Long userid){
		mysqlDao.saveMysqlBackup(filename, userid);
	}
	
	@Override
	public void doMysqlRecover(String sql) {
		mysqlDao.mysqlRecover(sql);
	}

	
}
