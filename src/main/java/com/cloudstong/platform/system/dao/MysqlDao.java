package com.cloudstong.platform.system.dao;

import java.util.List;
import java.util.Map;

import com.cloudstong.platform.resource.metadata.model.SchemaColumns;
import com.cloudstong.platform.resource.metadata.model.SchemaTables;
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
public interface MysqlDao {

	public List<SchemaTables> getSchemaTables();
	public List<SchemaColumns> getSchemaColumns();
	public List<Map<String, Object>> getDatas(String tableName);
	public void saveMysqlBackup(String filename,Long userid);
	public void mysqlRecover(String sql);
}
