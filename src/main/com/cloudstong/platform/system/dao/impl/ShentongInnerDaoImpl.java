package com.cloudstong.platform.system.dao.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cloudstong.platform.core.util.DataSourceUtil;
import com.cloudstong.platform.core.util.PathUtil;
import com.cloudstong.platform.resource.metadata.model.DataSources;
import com.cloudstong.platform.resource.metadata.model.SchemaColumns;
import com.cloudstong.platform.resource.metadata.model.SchemaTables;

/**
 * @author sam Created on 2012-11-22
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:数据源管理中的神通数据库迁移操作
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ShentongInnerDaoImpl {

	JdbcTemplate jdbcTemplateSrc = new JdbcTemplate();

	JdbcTemplate jdbcTemplateDesc = new JdbcTemplate();
	BasicDataSource instanceDesc = new BasicDataSource();

	JdbcTemplate jdbcTemplateSchema = new JdbcTemplate();
	BasicDataSource instanceSchema = new BasicDataSource();

	public ShentongInnerDaoImpl() {
	}

	public ShentongInnerDaoImpl(JdbcTemplate jdbcTemplate, DataSources dataSource) {
		createDataSource(jdbcTemplate, dataSource);
	}

	private void createDataSource(JdbcTemplate jdbcTemplate, DataSources dataSource) {
		jdbcTemplateSrc = jdbcTemplate;

		instanceDesc.setUrl(dataSource.getTblDsurl());
		instanceDesc.setDriverClassName(dataSource.getTblDsdriver());
		instanceDesc.setUsername(dataSource.getTblDsuser());
		instanceDesc.setPassword(dataSource.getTblDspasswd());
		jdbcTemplateDesc.setDataSource(instanceDesc);

		Properties prop = new Properties();
		try {
			String path = PathUtil.getWebInfPath()+"/WEB-INF/classes/global.properties";
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

	}

	public void deleteShentongTables() {
		List<SchemaTables> lstAllTables = jdbcTemplateSchema.query(
				"select * from tables where table_schema=? and table_type='base table'",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaTables.class));
		for (SchemaTables table : lstAllTables) {
			try {
				jdbcTemplateDesc.execute("drop table " + table.getTableName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createShentong() {
		try {
			createShentongTables();
			exportData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createShentongTables() {
		try {
			List<SchemaTables> lstAllTables = jdbcTemplateSchema.query(
					"select * from tables where table_schema=? and table_type='base table'",
					new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaTables.class));
			List<SchemaColumns> lstAllColumns = jdbcTemplateSchema.query("select * from columns where table_schema=?",
					new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaColumns.class));
			for (SchemaTables table : lstAllTables) {
				List<SchemaColumns> _lstTmpcolumn = new ArrayList<SchemaColumns>();
				for (SchemaColumns column : lstAllColumns) {
					if (table.getTableName().equals(column.getTableName())) {
						_lstTmpcolumn.add(column);
					}
				}
				StringBuilder _sTableSql = new StringBuilder("create table " + table.getTableName() + "(");
				for (SchemaColumns column : _lstTmpcolumn) {
					_sTableSql.append(V(column.getColumnName()));
					_sTableSql.append(" ");
					if (column.getDataType().equals("varchar")) {
						_sTableSql.append("varchar(" + I(column.getColumnType()) * 2 + ")");
					} else if(column.getDataType().equals("text")){
						_sTableSql.append("clob");
					}else {
						_sTableSql.append(column.getDataType());
					}
					_sTableSql.append(",");
				}
				_sTableSql.append("CONSTRAINT \"" + table.getTableName() + "_PK\" PRIMARY KEY (\"ID\"))");
				jdbcTemplateDesc.execute(_sTableSql.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exportData() {
		List<SchemaTables> lstAllTables = jdbcTemplateSchema.query(
				"select * from tables where table_schema=?  and length(table_name)<27 and table_type='base table'",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaTables.class));
		List<SchemaColumns> lstAllColumns = jdbcTemplateSchema.query("select * from columns where table_schema=?",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaColumns.class));

		for (SchemaTables table : lstAllTables) {
			try {
				List<SchemaColumns> _lstTmpcolumn = new ArrayList<SchemaColumns>();
				for (SchemaColumns column : lstAllColumns) {
					if (table.getTableName().equals(column.getTableName())) {
						_lstTmpcolumn.add(column);
					}
				}
				List<Map<String, Object>> _lstResult = jdbcTemplateSrc.queryForList("select * from "
						+ table.getTableName());
				if (_lstResult != null) {
					for (Map<String, Object> map : _lstResult) {
						StringBuilder _sbdSql = new StringBuilder();
						_sbdSql.append("insert into " + table.getTableName() + "(");
						StringBuilder _sbdTemp = new StringBuilder();
						List _lstTmp = new ArrayList();
						for (SchemaColumns column : _lstTmpcolumn) {
							if (map.get(column.getColumnName()) != null) {
								_sbdSql.append(V(column.getColumnName()) + ",");
								_sbdTemp.append("?,");
								_lstTmp.add(map.get(column.getColumnName()));
							}
						}

						if (_sbdTemp.length() > 0) {
							_sbdSql.delete(_sbdSql.length() - 1, _sbdSql.length());
							_sbdTemp.delete(_sbdTemp.length() - 1, _sbdTemp.length());
							_sbdSql.append(") values (");
							_sbdSql.append(_sbdTemp);
							_sbdSql.append(")");
							Object[] _objTmp = new Object[_lstTmp.size()];
							for (int i = 0; i < _lstTmp.size(); i++) {
								_objTmp[i] = _lstTmp.get(i);
							}
							jdbcTemplateDesc.update(_sbdSql.toString(), _objTmp);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void changeJdbcProp(DataSources pDataSource) {
		Properties prop = new Properties();
		try {
			String path = PathUtil.getWebInfPath()+"/WEB-INF/classes/global.properties";
			InputStream in = new FileInputStream(path);
			prop.load(in);
			prop.setProperty("jdbc.driverClassName", pDataSource.getTblDsdriver());
			prop.setProperty("jdbc.url", pDataSource.getTblDsurl());
			prop.setProperty("jdbc.username", pDataSource.getTblDsuser());
			prop.setProperty("jdbc.password", pDataSource.getTblDspasswd());

			OutputStream fos = new FileOutputStream(path);
			prop.store(fos, "set");

			DataSource dataSource = DataSourceUtil.refreshDataSource();
			jdbcTemplateSrc.setDataSource(dataSource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String V(String colName) {
		String[] argStr = { "VALIDATE", "COMMENT", "NAME", "LEVEL", "PATH", "TYPE", "CONTENT", "ROWS", "TIMES",
				"PASSWORD", "validate", "comment", "name", "level", "path", "type", "content", "rows", "times",
				"password" };
		for (String str : argStr) {
			if (str.equals(colName)) {
				colName = "\"" + colName + "\"";
				break;
			}
		}
		return colName;
	}

	public int I(String colType) {
		String _sTmp = "";
		for (int i = 0; i < colType.getBytes().length; i++) {
			if (colType.getBytes()[i] > 47 && colType.getBytes()[i] < 58)
				_sTmp += colType.charAt(i);
		}
		return Integer.parseInt(_sTmp);
	}
}
