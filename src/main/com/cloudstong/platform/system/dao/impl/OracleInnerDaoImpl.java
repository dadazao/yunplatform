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
 *         Description:数据源管理中的oracle数据库迁移操作
 */
public class OracleInnerDaoImpl {

	JdbcTemplate jdbcTemplateSrc = new JdbcTemplate();

	JdbcTemplate jdbcTemplateDesc = new JdbcTemplate();
	BasicDataSource instanceDesc = new BasicDataSource();

	JdbcTemplate jdbcTemplateSchema = new JdbcTemplate();
	BasicDataSource instanceSchema = new BasicDataSource();

	public OracleInnerDaoImpl(JdbcTemplate jdbcTemplate, DataSources dataSource) {
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

	public void createOrcle(String psUser) {
		createOralceTables(psUser);
		exportData(psUser);
	}

	private void createOralceDatabase(String psUser) {
		try {
			String _tmpUser = psUser;
			String _sTmpTabelspace = "CREATE SMALLFILE TEMPORARY TABLESPACE \"TMP_" + _tmpUser
					+ "\" TEMPFILE 'D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\ORCL\\TMP_" + _tmpUser
					+ "' SIZE 100M AUTOEXTEND ON NEXT 1M MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1M";
			String _sTabelspace = "CREATE SMALLFILE TABLESPACE \""
					+ _tmpUser
					+ "\" DATAFILE 'D:\\ORACLE\\PRODUCT\\10.2.0\\ORADATA\\ORCL\\"
					+ _tmpUser
					+ "' SIZE 100M AUTOEXTEND ON NEXT 1M MAXSIZE UNLIMITED LOGGING EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO";
			String _sUser = "CREATE USER \"" + _tmpUser + "\" PROFILE \"DEFAULT\" IDENTIFIED BY \"" + _tmpUser
					+ "\" DEFAULT TABLESPACE \"" + _tmpUser + "\" TEMPORARY TABLESPACE \"TMP_" + _tmpUser
					+ "\" ACCOUNT UNLOCK";
			String _sRoleConnect = "GRANT \"CONNECT\" TO \"" + _tmpUser + "\" ";
			String _sRoleDba = "GRANT \"DBA\" TO \"" + _tmpUser + "\" ";
			String _sRoleExp = "GRANT \"EXP_FULL_DATABASE\" TO \"" + _tmpUser + "\" ";
			String _sRoleImp = "GRANT \"IMP_FULL_DATABASE\" TO \"" + _tmpUser + "\" ";
			jdbcTemplateDesc.execute(_sTmpTabelspace);
			jdbcTemplateDesc.execute(_sTabelspace);
			jdbcTemplateDesc.execute(_sUser);
			jdbcTemplateDesc.execute(_sRoleConnect);
			jdbcTemplateDesc.execute(_sRoleDba);
			jdbcTemplateDesc.execute(_sRoleExp);
			jdbcTemplateDesc.execute(_sRoleImp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createOralceTables(String psUser) {

		List<SchemaTables> lstAllTables = this.jdbcTemplateSchema.query(
				"select * from tables where table_schema=?  and length(table_name)<27 and table_type='base table'",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaTables.class));
		List<SchemaColumns> lstAllColumns = this.jdbcTemplateSchema.query("select * from columns where table_schema=?",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaColumns.class));
		for (SchemaTables table : lstAllTables) {
			if (table.getTableName().length() < 30) {
				List<SchemaColumns> _lstTmpcolumn = new ArrayList<SchemaColumns>();
				for (SchemaColumns column : lstAllColumns) {
					if (table.getTableName().equals(column.getTableName())) {
						column = C(column);
						_lstTmpcolumn.add(column);
					}
				}
				StringBuilder _sTableSql = new StringBuilder("CREATE TABLE \"" + psUser + "\".\""
						+ table.getTableName().toUpperCase() + "\"(");
				for (SchemaColumns column : _lstTmpcolumn) {
					_sTableSql.append("\"" + column.getColumnName().toUpperCase() + "\"");
					_sTableSql.append(" ");
					_sTableSql.append(column.getDataType());
					if (column.getDataType().equalsIgnoreCase("NUMBER")) {
						_sTableSql.append("(");
						_sTableSql.append(column.getCharacterMaximumLength() + ",0");
						_sTableSql.append(") ");
						if(null != column.getColumnDefault()){
							_sTableSql.append(" DEFAULT " + column.getColumnDefault());
						}
					} else if (column.getDataType().equalsIgnoreCase("VARCHAR2")) {
						_sTableSql.append("(");
						if (I(column.getColumnType()) > 4000) {
							_sTableSql.append(4000 + " CHAR");
						} else {
							_sTableSql.append(I(column.getColumnType())  + " CHAR");
						}
						_sTableSql.append(")");
						if(null != column.getColumnDefault()){
							_sTableSql.append(" DEFAULT '" + column.getColumnDefault() + "'");
						}
					} else if (column.getDataType().equalsIgnoreCase("DATA")) {
						_sTableSql.append(" default sysdate");
					}
					_sTableSql.append(",");
				}
				_sTableSql.append("CONSTRAINT \"" + table.getTableName().toUpperCase() + "_PK\" PRIMARY KEY (\"ID\"))");
				try {
					jdbcTemplateDesc.execute(_sTableSql.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exportData(String psUser) {
		List<SchemaTables> lstAllTables = this.jdbcTemplateSchema.query(
				"select * from tables where table_schema=?  and length(table_name)<27 and table_type='base table'",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaTables.class));
		List<SchemaColumns> lstAllColumns = this.jdbcTemplateSchema.query("select * from columns where table_schema=?",
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
						_sbdSql.append("insert into " + psUser + "." + table.getTableName().toUpperCase() + "(");
						StringBuilder _sbdTemp = new StringBuilder();
						List _lstTmp = new ArrayList();
						for (SchemaColumns column : _lstTmpcolumn) {
							if (map.get(column.getColumnName()) != null) {
								_sbdSql.append("\"" + column.getColumnName().toUpperCase() + "\",");
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
							try {
								jdbcTemplateDesc.update(_sbdSql.toString(), _objTmp);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public void deleteOralceTables(String psUser) {
		List<SchemaTables> lstAllTables = this.jdbcTemplateSchema.query(
				"select * from tables where table_schema=?  and length(table_name)<27 and table_type='base table'",
				new Object[] { "yunplatform" }, new BeanPropertyRowMapper(SchemaTables.class));
		String _sTableName = "";
		for (SchemaTables table : lstAllTables) {
			_sTableName = table.getTableName().toUpperCase();
			try {
				jdbcTemplateDesc.execute("drop table " + psUser + "." + _sTableName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public SchemaColumns C(SchemaColumns psColumn) {
		if (psColumn.getDataType().equals("varchar")) {
			psColumn.setDataType("VARCHAR2");
		} else if (psColumn.getDataType().equals("bigint")) {
			psColumn.setDataType("NUMBER");
			psColumn.setCharacterMaximumLength(20l);
		} else if (psColumn.getDataType().equals("int")) {
			psColumn.setDataType("NUMBER");
			psColumn.setCharacterMaximumLength(10l);
		} else if (psColumn.getDataType().equals("timestamp")) {
			psColumn.setDataType("DATE");
		} else if (psColumn.getDataType().equals("text")) {
			psColumn.setDataType("CLOB");
		}
		return psColumn;
	}

	public int I(String colType) {
		String _sTmp = "";
		for (int i = 0; i < colType.getBytes().length; i++) {
			if (colType.getBytes()[i] > 47 && colType.getBytes()[i] < 58)
				_sTmp += colType.charAt(i);
		}
		return Integer.parseInt(_sTmp);
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
}
