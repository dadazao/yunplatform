package com.cloudstong.platform.system.dao.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.PathUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.metadata.model.SchemaColumns;
import com.cloudstong.platform.resource.metadata.model.SchemaTables;
import com.cloudstong.platform.system.dao.MysqlDao;

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
@Repository("mysqlDao")
public class MysqlDaoImpl implements MysqlDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;
	
	Log logger = LogFactory.getLog(this.getClass());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SchemaTables> getSchemaTables() {
		JdbcTemplate jdbcTemplateSchema = getJdbcTemplateSchema();
		return jdbcTemplateSchema.query(
				"select * from tables where table_schema=? and table_name not like ? and table_type='base table'",
				new Object[] { "yunplatform", "jbpm4_%" }, new BeanPropertyRowMapper(SchemaTables.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SchemaColumns> getSchemaColumns() {
		JdbcTemplate jdbcTemplateSchema = getJdbcTemplateSchema();
		return jdbcTemplateSchema.query("select * from columns where table_schema=?",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaColumns.class));
	}

	@Override
	public List<Map<String, Object>> getDatas(String tableName) {
		return jdbcTemplate.queryForList("select * from " + tableName);
	}
	
	public void saveMysqlBackup(String filename,Long userid){
		Object[] objs = new Object[] { UniqueIdUtil.genId(), 
				filename, userid, new Date(System.currentTimeMillis()), 
				userid, new Date(System.currentTimeMillis())};
		jdbcTemplate.update("insert into sys_mysqlbackup(id,tbl_filename,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate) values(?, ?, ?, ?, ?, ?)", objs);
	}
	
	@Override
	public void mysqlRecover(String sql) {
		try {
			jdbcTemplate.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private JdbcTemplate getJdbcTemplateSchema() {
		JdbcTemplate jdbcTemplateSchema = new JdbcTemplate();
		BasicDataSource instanceSchema = new BasicDataSource();
		Properties prop = new Properties();
		try {
			String path = PathUtil.getWebInfPath() + "/WEB-INF/classes/global.properties";
			InputStream in = new FileInputStream(path);
			prop.load(in);
			instanceSchema.setUrl(prop.getProperty("jdbc.url").replace("yunplatform", "information_schema"));
			instanceSchema.setDriverClassName(prop.getProperty("jdbc.driverClassName"));
			instanceSchema.setUsername(prop.getProperty("jdbc.username"));
			instanceSchema.setPassword(prop.getProperty("jdbc.password"));
			jdbcTemplateSchema.setDataSource(instanceSchema);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jdbcTemplateSchema;
	}
}