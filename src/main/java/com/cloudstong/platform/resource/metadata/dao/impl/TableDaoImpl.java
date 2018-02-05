package com.cloudstong.platform.resource.metadata.dao.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dialect.Dialect;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.dao.TableDao;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.Meta;
import com.cloudstong.platform.resource.metadata.model.Table;
import com.cloudstong.platform.resource.metadata.model.TableRowMapper;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:数据表数据操作Dao实现
 */
@Repository("tableDao")
public class TableDaoImpl implements TableDao {

	Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	@Resource
	private ColumnDao columnDao;
	
	@Resource
	private Dialect dialect;

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public ColumnDao getColumnDao() {
		return columnDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	public JdbcTemplateExtend getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Cacheable(value = "resourceCache")
	public List select(String where, Object[] args, int startRow, int rowsCount) {
		List retList = new ArrayList();
		try {
			if (rowsCount == -1) {// 查询出所有记录
				retList = this.jdbcTemplate.query(where, args, new TableRowMapper());
			} else {
				retList = this.jdbcTemplate.querySP(where, args, startRow, rowsCount, new TableRowMapper());
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return retList;
	}

	public Table selectById(Long id) {
		String sql = "select t.*,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_tables t left join sys_user u on t.comm_createBy = u.id left join sys_user u2 on t.comm_updateBy = u2.id where t.id=?";
		final Table u = new Table();
		final Object[] params = new Object[] { id };
		try {
			query(sql, params, u);
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return u;
	}

	private void query(String sql, Object[] params, final Table u) {
		jdbcTemplate.query(sql, params, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				u.setId(rs.getLong("id"));
				u.setTableFunction(rs.getString("tbl_tableFunction"));
				u.setTableName(rs.getString("tbl_tableName"));
				u.setTableZhName(rs.getString("tbl_tableZhName"));
				u.setTableEnName(rs.getString("tbl_tableEnName"));
				u.setTableSchema(rs.getString("tbl_tableSchema"));
				u.setTableCode(rs.getString("tbl_tableCode"));
				u.setTableType(rs.getString("tbl_tableType"));
				u.setTableRelationType(rs.getString("tbl_tableRelationType"));
				u.setHasCommonColumn(rs.getInt("tbl_hasCommonColumn"));
				u.setPrimaryKey(rs.getString("tbl_primaryKey"));
				u.setHasForeignKey(rs.getInt("tbl_hasForeignKey"));
				u.setHasIndex(rs.getInt("tbl_hasIndex"));
				u.setStatus(rs.getInt("comm_status"));
				u.setCreateBy(rs.getLong("comm_createBy"));
				u.setCreateDate(rs.getTimestamp("comm_createDate"));
				u.setUpdateBy(rs.getLong("comm_updateBy"));
				u.setUpdateDate(rs.getTimestamp("comm_updateDate"));
				u.setRemark(rs.getString("tbl_remark"));
				u.setUserName(rs.getString("tbl_createname"));
				u.setUpdateName(rs.getString("tbl_updatename"));
				u.setGroup(rs.getString("tbl_systemteam"));
				u.setVersion(rs.getString("tbl_version"));
			}
		});
	}

	/**
	 * 初始化管理表和字段表
	 */
	public void initializeDataTable() {
		// 创建管理表
		createTable(Table.class);
		// 创建字段表
		createTable(Column.class);
		// 将管理表存入到管理表
		Table sysTable = new Table();
		sysTable.setTableName("sys_tables");
		sysTable.setTableZhName("管理表");
		sysTable.setCreateDate(new Date());
		sysTable.setTableType("0");// 基本表
		insert(sysTable);
		// 将字段表存入到管理表
		Table colTable = new Table();
		colTable.setTableName("sys_columns");
		colTable.setTableZhName("字段表");
		colTable.setCreateDate(new Date());
		colTable.setTableType("0");// 基本表
		insert(colTable);
		// 将管理表类的属性插入到字段表中
		columnDao.insertFieldsToColumn(Table.class);
		columnDao.insertFieldsToColumn(Column.class);
	}

	public void createTable(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer sql = new StringBuffer("Create table ");
		sql.append("`" + clazz.getSimpleName() + "s` " + "(");
		for (Field field : fields) {
			sql.append("`" + field.getName() + "`");
			if (field.getType().isAssignableFrom(String.class)) {
				sql.append(" varchar(100) DEFAULT NULL,");
			} else if (field.getType().isAssignableFrom(int.class)) {
				sql.append(" int DEFAULT NULL,");
			} else if (field.getType().isAssignableFrom(Long.class)) {
				if ("id".equals(field.getName())) {
					sql.append(" bigint(20) NOT NULL AUTO_INCREMENT,");
				} else {
					sql.append(" bigint(20) DEFAULT NULL,");
				}
			} else if (field.getType().isAssignableFrom(java.util.Date.class)) {
				sql.append(" timestamp NULL DEFAULT NULL,");
			}
		}
		sql.append("PRIMARY KEY (`id`)");
		sql.append(")");
		logger.info("sql=>" + sql);
		jdbcTemplate.execute(sql.toString());
	}

	@Override
	public void insertTables(List<Table> tables) {
		for (Table table : tables) {
			insert(table);
		}
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id) {
		String sql = "delete from sys_tables where id=?";
		Object[] params = new Object[] { id };

		Table table = selectById(id);
		String columnSql = "delete from sys_columns where tbl_tableId=?";
		Object[] columnParams = new Object[] { id };

		String dropSql = "drop table " + table.getTableName();

		jdbcTemplate.update(columnSql, columnParams);
		jdbcTemplate.update(sql, params);
		jdbcTemplate.execute(dropSql);
	}

	@Override
	public int count(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer("select count(*) from sys_tables where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getValue() instanceof String) {
				sql.append(" and " + entry.getKey() + " like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
			} else {
				sql.append(" and " + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}
		}
		sql.append(" order by id desc ");
		List counts = jdbcTemplate.query(sql.toString(), param.toArray(), new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	public String findValueByColumn(String tableName, final String columnName) {
		String sql = "select " + columnName + " from sys_tables  where tbl_tableName=?";
		Object[] params = new Object[] { tableName };
		try {
			List values = jdbcTemplate.query(sql, params, new ColumnRowMapper());
			if (values != null && values.size() > 0 && values.get(0) != null) {
				return values.get(0).toString();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	class ColumnRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Object ret = rs.getObject(1);
			return ret;
		}
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void update(Table table) {
		String sql = "update sys_tables set tbl_systemteam=?,tbl_tableName=?,tbl_tableZhName=?,tbl_tableEnName=?,tbl_tableSchema=?,tbl_tableType=?,comm_updateBy=?,comm_updateDate=?,tbl_tableFunction=?,tbl_tableCode=?,tbl_tableRelationType=?,tbl_hasCommonColumn=?,tbl_primaryKey=?,tbl_hasForeignKey=?,tbl_hasIndex=?,comm_status=?,tbl_remark=? where id=?";
		Object[] params = new Object[] { table.getGroup(), table.getTableName(), table.getTableZhName(),table.getTableEnName(), table.getTableSchema(),
				table.getTableType(), table.getUpdateBy(), new Date(), table.getTableFunction(), table.getTableCode(), table.getTableRelationType(),
				table.getHasCommonColumn(), table.getPrimaryKey(), table.getHasForeignKey(), table.getHasIndex(), table.getStatus(),
				table.getRemark(), table.getId() };
		List plist = new ArrayList(Arrays.asList(params));
		Table t = selectById(table.getId());
		StringBuffer alertSql = new StringBuffer("alter table ");
		alertSql.append(t.getTableName());
		alertSql.append(" rename ");
		alertSql.append(table.getTableName());

		jdbcTemplate.execute(alertSql.toString());
		jdbcTemplate.update(sql, plist.toArray());
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public Long insert(Table u) {
		String sql = "insert into sys_tables(id,tbl_systemteam,tbl_tableName,tbl_tableZhName,tbl_tableEnName,tbl_tableSchema,tbl_tableType,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,comm_status,tbl_tableFunction,tbl_tableCode,tbl_tableRelationType,tbl_hasCommonColumn,tbl_primaryKey,tbl_hasForeignKey,tbl_hasIndex,tbl_remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Long id = UniqueIdUtil.genId();
		final Object[] params = new Object[] { id, u.getGroup(), u.getTableName(), u.getTableZhName(),u.getTableEnName(), u.getTableSchema(), u.getTableType(),
				u.getCreateBy(), u.getCreateDate(), u.getUpdateBy(), u.getUpdateDate(), u.getStatus(), u.getTableFunction(), u.getTableCode(),
				u.getTableRelationType(), u.getHasCommonColumn(), u.getPrimaryKey(), u.getHasForeignKey(), u.getHasIndex(), u.getRemark() };
		StringBuffer createSql = new StringBuffer("create table " + u.getTableName() + "(id varchar(50) NOT NULL,");
		if (u.getHasCommonColumn() == 1) {
			createSql.append("comm_createBy varchar(50) NULL,comm_createDate timestamp NULL,comm_updateBy varchar(50) NULL,comm_updateDate timestamp NULL,comm_opt_counter int(10) default 0,comm_mark_for_delete int(1) default 0,");
		}
		if (u.getTableType().equals("4")) {
			createSql.append("tbl_name varchar(100) NULL,tbl_parentId varchar(50) NULL,");
		}
		createSql.append("PRIMARY KEY (`id`))");

		if (!"sys_tables".equals(u.getTableName()) && !"sys_columns".equals(u.getTableName())) {
			jdbcTemplate.execute(dialect.getCreateTableString(u));
		}

		jdbcTemplate.update(sql, params);
		// 建立树类型表时自动插入固有字段和根节点；
		if (u.getTableType().equals("4")) {
			columnDao.insert(buildColumn("tbl_name", "名称", "name", "varchar", 100, id, u.getTableName(), String.valueOf(u.getCreateBy()), "名称"), false);
			columnDao.insert(buildColumn("tbl_parentId", "父ID","parentId", "varchar", 50, id, u.getTableName(), String.valueOf(u.getCreateBy()), "父ID"), false);

			String initTableSql = "insert into " + u.getTableName() + " (id,tbl_parentId,tbl_name) values (1,0,'全部')";
			jdbcTemplate.execute(initTableSql);
		}

		if (u.getHasCommonColumn() == 1) {
			columnDao.insert(buildColumn("comm_createBy","创建人", "createBy", "varchar", 50, id, u.getTableName(), String.valueOf(u.getCreateBy()), "创建记录的人员"), false);
			columnDao.insert(buildColumn("comm_createDate", "创建时间", "createDate", "timestamp", 50, id, u.getTableName(), String.valueOf(u.getCreateBy()), "创建记录的时间"), false);
			columnDao.insert(buildColumn("comm_updateBy", "修改人", "updateBy", "varchar", 50, id, u.getTableName(), String.valueOf(u.getCreateBy()), "修改记录的人员"), false);
			columnDao.insert(buildColumn("comm_updateDate", "修改时间", "updateDate", "timestamp", 50, id, u.getTableName(), String.valueOf(u.getCreateBy()), "修改记录的时间"), false);
			columnDao.insert(buildColumn("comm_opt_counter", "版本号", "version", "int", 10, id, u.getTableName(), String.valueOf(u.getCreateBy()), "版本号"), false);
			columnDao.insert(buildColumn("comm_mark_for_delete", "删除标识", "deleteMark", "int", 1, id, u.getTableName(), String.valueOf(u.getCreateBy()), "删除标识"), false);
		}

		// // 初始化管理表和字段表
		// initializeDataTable();
		// // 实际创建表
		// jdbcTemplate.execute(createSql.toString());
		// // 增加业务表
		// jdbcTemplate.update(sql, params);
		return id;
	}

	public Column buildColumn(String columnName, String columnZhName,String columnEnName, String dataType, int length, Long tableId, String belongTable,
			String comm_createBy, String comment) {
		Column c = new Column();
		c.setColumnName(columnName);
		c.setColumnZhName(columnZhName);
		c.setColumnEnName(columnEnName);
		c.setDataType(dataType);
		c.setLength(length);
		c.setTableId(tableId);
		c.setBelongTable(belongTable);
		c.setCreateBy(Long.valueOf(comm_createBy));
		c.setCreateDate(new Date());
		c.setUpdateBy(Long.valueOf(comm_createBy));
		c.setUpdateDate(new Date());
		c.setComment(comment);
		return c;
	}

	@Override
	public List queryTableByField(String tableZhName, String field) {
		String sql = "select * from sys_tables  where " + field + "=?";
		Object[] params = new Object[] { tableZhName };
		return jdbcTemplate.query(sql, params, new ColumnMapRowMapper());
	}

	@Override
	public List<Table> findTables(String tableId) {
		String _msql = "select tbl_subid from sys_relation where tbl_mainid = ?";
		Object[] _mparams = new Object[] { tableId };
		List<Map<String, Object>> list = jdbcTemplate.queryForList(_msql, _mparams);
		StringBuffer _sql = new StringBuffer(
				"select t.*,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_tables t left join sys_user u on t.comm_createBy = u.id   left join sys_user u2 on t.comm_updateBy = u2.id   where t.id in(?");
		List<String> _params = new ArrayList<String>();
		_params.add(tableId);
		for (int i = 0; i < list.size(); i++) {
			_sql.append(",?");
			_params.add(list.get(i).get("tbl_subid").toString());
		}
		_sql.append(")");
		List tableList = jdbcTemplate.query(_sql.toString(), _params.toArray(), new TableRowMapper());
		return tableList;
	}

	@Override
	public String findTableTypeByName(String _tableName) {
		String _sql = "select tbl_tableType from sys_tables where tbl_tableName = ?";
		Object[] _params = new Object[] { _tableName };
		return jdbcTemplate.queryForObject(_sql, _params, String.class);
	}

	@Override
	public Map findTableById(Long modelId) {
		String _sql = "select * from sys_tables where id = ?";
		Object[] params = new Object[] { modelId };
		return jdbcTemplate.queryForObject(_sql, params, new ColumnMapRowMapper());
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void updateStatus(Long id, int i) {
		String sql = "update sys_tables set comm_status=? where id=?";
		Object[] params = new Object[] { i, id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void logicDelete(Long id) {
		String sql = "update sys_tables set tbl_dstatus = 1 where id=?";
		Object[] params = new Object[] { id };

		Table table = selectById(id);
		String columnSql = "update sys_columns set tbl_dstatus = 1 where tbl_tableId=?";
		Object[] columnParams = new Object[] { id };

		// String dropSql = "drop table " + table.getTableName();

		jdbcTemplate.update(columnSql, columnParams);
		jdbcTemplate.update(sql, params);
		// jdbcTemplate.execute(dropSql);
	}

	@Override
	public List<Table> findTablesByTeam(Long tableId, String systemTeam) {
		String _msql = "select tbl_subid from sys_relation where tbl_mainid = ?";
		Object[] _mparams = new Object[] { tableId };
		List<Map<String, Object>> list = jdbcTemplate.queryForList(_msql, _mparams);
		StringBuffer _sql = new StringBuffer(
				"select t.*,u.tbl_yonghuxingming as tbl_createname,u2.tbl_yonghuxingming as tbl_updatename from sys_tables t left join sys_user u on t.comm_createBy = u.id   left join sys_user u2 on t.comm_updateBy = u2.id   where t.id in(?");
		List<String> _params = new ArrayList<String>();
		_params.add(String.valueOf(tableId));
		for (int i = 0; i < list.size(); i++) {
			_sql.append(",?");
			_params.add(list.get(i).get("tbl_subid").toString());
		}
		_sql.append(") and t.tbl_systemteam = ?");
		_params.add(systemTeam);
		List tableList = jdbcTemplate.query(_sql.toString(), _params.toArray(), new TableRowMapper());
		return tableList;
	}

	@Override
	public void autoCreateLogTable() {
		try {
			String tableName = "sys_log"+new SimpleDateFormat("yyyyMM").format(new Date(System.currentTimeMillis()));
			String createSql = "CREATE TABLE if not exists "+tableName+" (id bigint(20) primary key NOT NULL auto_increment,comm_createBy varchar(50) DEFAULT NULL,comm_createDate timestamp NULL DEFAULT NULL,comm_updateBy varchar(50) DEFAULT NULL,comm_updateDate timestamp NULL DEFAULT NULL,comm_opt_counter int(10) DEFAULT '0',comm_mark_for_delete int(1) DEFAULT '0',tbl_userid varchar(50) DEFAULT NULL,tbl_username varchar(50) DEFAULT NULL,tbl_loginip varchar(50) DEFAULT NULL,tbl_module varchar(200) DEFAULT NULL,tbl_type varchar(50) DEFAULT NULL,tbl_operation varchar(1024) DEFAULT NULL,tbl_remark varchar(1024) DEFAULT NULL)";
			jdbcTemplate.execute(createSql);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insertMeta(Meta u) {
		try {
			String id = UniqueIdUtil.getUUID();
			String sql = "insert into sys_metadataversion(id,tbl_version,tbl_columnCode,tbl_columnName," +
					"tbl_columnZhName,tbl_tableId,tbl_belongTable,tbl_columnId," +
					"tbl_dataType,tbl_defaultValue,tbl_length,tbl_isPrimaryKey,tbl_isNullable,tbl_isPublish," +
					"tbl_comment,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,tbl_remark," +
					"tbl_isRequired,tbl_isRepeat,tbl_min,tbl_max,tbl_metaType) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] params = new Object[] { id,u.getVersion(),u.getColumnCode(),u.getColumnName(),u.getColumnZhName(),
					u.getTableId(),u.getBelongTable(),u.getColumnId(),u.getDataType(), u.getDefaultValue(),u.getLength(), 
					u.getIsPrimaryKey(), u.getIsNullable(),u.getIsPublish(),u.getComment(), u.getCreateBy(),
					u.getCreateDate(), u.getUpdateBy(), u.getUpdateDate(), u.getRemark(),
					u.getIsRequired(),u.getIsRepeat(),u.getMin(),u.getMax(),u.getMetaType()};
			jdbcTemplate.update(sql,params);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void updateVersion(String version, String id) {
		String sql = "update sys_tables set tbl_version = ? where id = ?";
		Object[] params = new Object[] {version,id};
		jdbcTemplate.update(sql, params);
	}
	
	@Override
	public List selectHistoryById(String id) {
		String sql = "select * from sys_metadataversion where tbl_tableId=? group by tbl_version";
		Object[] params = new Object[] { id };
		return jdbcTemplate.query(sql,params,new ColumnMapRowMapper());
	}

	@Override
	public List selectMetaHistoryById(String id, String version) {
		String sql = "select * from sys_metadataversion where tbl_tableId=? and tbl_version=?";
		Object[] params = new Object[] { id, version };
		return jdbcTemplate.query(sql,params,new ColumnMapRowMapper());
	}
	
}