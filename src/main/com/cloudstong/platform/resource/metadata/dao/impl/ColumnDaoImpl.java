package com.cloudstong.platform.resource.metadata.dao.impl;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.jdbc.JdbcTemplateExtend;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.resource.metadata.dao.ColumnDao;
import com.cloudstong.platform.resource.metadata.model.Column;
import com.cloudstong.platform.resource.metadata.model.ColumnRowMapper;
import com.cloudstong.platform.resource.metadata.model.ConfigColumn;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:字段数据操作Dao实现
 */
@Repository("columnDao")
public class ColumnDaoImpl implements ColumnDao {

	@Resource
	private JdbcTemplateExtend jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplateExtend jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List select(String where, Object[] params, int startRow, int rowsCount) {
		List retList = new ArrayList();
		try {
			if (rowsCount == -1) {// 查询出所有记录
				retList = this.jdbcTemplate.query(where, params, new ColumnRowMapper());
			} else {
				retList = this.jdbcTemplate.querySP(where, params, startRow, rowsCount, new ColumnRowMapper());
			}
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return retList;
	}

	public Column selectById(Long id) {
		String sql = "select a.*,b.tbl_tableZhName as tbl_tableZhName,u.tbl_yonghuxingming as createname,u2.tbl_yonghuxingming as updatename from sys_columns a left join sys_tables b on a.tbl_tableId=b.id left join sys_user u on a.comm_createBy = u.id left join sys_user u2 on a.comm_updateBy = u2.id where a.id=?";
		final Column u = new Column();
		final Object[] params = new Object[] { id };
		try {
			jdbcTemplate.query(sql, params, new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					u.setId(rs.getLong("id"));
					u.setColumnName(rs.getString("tbl_columnName"));
					u.setColumnZhName(rs.getString("tbl_columnZhName"));
					u.setColumnEnName(rs.getString("tbl_columnEnName"));
					u.setComment(rs.getString("tbl_comment"));
					u.setDataType(rs.getString("tbl_dataType"));
					u.setBelongTable(rs.getString("tbl_belongTable"));
					u.setDefaultValue(rs.getString("tbl_defaultValue"));
					u.setLength(rs.getInt("tbl_length"));
					u.setIsPrimaryKey(rs.getInt("tbl_isPrimaryKey"));
					u.setIsNullable(rs.getInt("tbl_isNullable"));
					u.setIsPublish(rs.getInt("tbl_isPublish"));
					u.setCreateBy(rs.getLong("comm_createBy"));
					u.setCreateDate(rs.getTimestamp("comm_createDate"));
					u.setUpdateBy(rs.getLong("comm_updateBy"));
					u.setUpdateDate(rs.getTimestamp("comm_updateDate"));
					u.setStatus(rs.getInt("comm_status"));
					u.setTableId(rs.getLong("tbl_tableId"));
					u.setTableZhName(rs.getString("tbl_tableZhName"));
					u.setUserName(rs.getString("createname"));
					u.setUpdateName(rs.getString("updatename"));
					u.setRemark(rs.getString("tbl_remark"));
				}
			});
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
		}
		return u;
	}

	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void update(Column column) {
		String sql = "update sys_columns set tbl_columnName=?,tbl_columnZhName=?,tbl_columnEnName=?,tbl_tableId=?,tbl_belongTable=?,tbl_showType=?,tbl_dataType=?,tbl_defaultValue=?,tbl_length=?,tbl_isPrimaryKey=?,tbl_isNullable=?,tbl_isPublish=?,tbl_comment=?,comm_updateBy=?,comm_updateDate=? where id=?";
		Object[] params = new Object[] { column.getColumnName(), column.getColumnZhName(), column.getColumnEnName(), column.getTableId(), column.getBelongTable(),
				column.getShowType(), column.getDataType(), column.getDefaultValue(), column.getLength(), column.getIsPrimaryKey(),
				column.getIsNullable(), column.getIsPublish(), column.getComment(), column.getUpdateBy(), column.getUpdateDate(), column.getId() };

		Column c = selectById(column.getId());
		StringBuffer alterSql = new StringBuffer("alter table ");
		alterSql.append(column.getBelongTable());
		if (c.getColumnName().equals(column.getColumnName())) {
			alterSql.append(" modify " + column.getColumnName() + " " + column.getDataType() + "(" + column.getLength() + ")");
		} else {
			alterSql.append(" change column " + c.getColumnName() + " " + column.getColumnName() + " " + column.getDataType() + "("
					+ column.getLength() + ")");
		}
		jdbcTemplate.update(sql, params);
		jdbcTemplate.execute(alterSql.toString());
	}

	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insert(Column u, boolean flag) {
		String sql = "insert into sys_columns(id,tbl_columnName,tbl_columnZhName,tbl_columnEnName,tbl_tableId,tbl_belongTable,tbl_dataType,tbl_defaultValue,tbl_length,tbl_isPrimaryKey,tbl_isNullable,tbl_isPublish,tbl_comment,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,comm_status,tbl_remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { UniqueIdUtil.genId(), u.getColumnName(), u.getColumnZhName(),u.getColumnEnName(), u.getTableId(), u.getBelongTable(),
				u.getDataType(), u.getDefaultValue(), u.getLength(), u.getIsPrimaryKey(), u.getIsNullable(), u.getIsPublish(), u.getComment(),
				u.getCreateBy(), u.getCreateDate(), u.getUpdateBy(), u.getUpdateDate(), u.getStatus(), u.getRemark() };

		StringBuffer alterSql = new StringBuffer("alter table ");
		alterSql.append(u.getBelongTable());
		alterSql.append(" add column ");
		if ("timestamp".equals(u.getDataType()) || "date".equals(u.getDataType())) {
			alterSql.append(u.getColumnName() + " " + u.getDataType() + " NULL");
		} else {
			alterSql.append(u.getColumnName() + " " + u.getDataType() + "(" + u.getLength() + ")");
		}
		if (u.getDefaultValue() != null && !"".equals(u.getDefaultValue())) {
			alterSql.append(" DEFAULT '" + u.getDefaultValue() + "'");
		}
		jdbcTemplate.update(sql, params);
		if (flag == true) {
			jdbcTemplate.execute(alterSql.toString());
		}
	}

	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insertFieldsToColumn(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			Column column = new Column();
			column.setBelongTable(clazz.getSimpleName().toLowerCase() + "s");
			column.setColumnName(field.getName());
			if (field.getType().isAssignableFrom(String.class)) {
				column.setDataType("varchar");
				column.setLength(100);
			} else if (field.getType().isAssignableFrom(int.class)) {
				column.setDataType("int");
				column.setLength(20);
			} else if (field.getType().isAssignableFrom(Long.class)) {
				column.setDataType("bigint");
				column.setLength(20);
			} else if (field.getType().isAssignableFrom(java.util.Date.class)) {
				column.setDataType("timestamp");
			}
			if (!"id".equals(field.getName())) {
				insert(column, false);
			}
		}
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void delete(Long id) {
		String sql = "delete from sys_columns where id=?";
		Object[] params = new Object[] { id };

		Column column = selectById(id);
		String columnSql = "alter table " + column.getBelongTable() + " drop column " + column.getColumnName();

		jdbcTemplate.update(sql, params);
		jdbcTemplate.execute(columnSql);
	}

	@Override
	public int count(QueryCriteria qc) {
		StringBuffer sql = new StringBuffer("select count(*) from sys_columns a left join sys_tables b on a.tbl_tableId=b.id  where 1=1 ");
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getKey().equalsIgnoreCase("tbl_belongTable")) {
				sql.append(" and b.tbl_tableZhName like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
				continue;
			}
			if (entry.getValue() instanceof String) {
				sql.append(" and " + entry.getKey() + " like ? ");
				param.add("%" + ((String)entry.getValue()).trim() + "%");
			} else {
				sql.append(" and " + entry.getKey() + " =? ");
				param.add(entry.getValue());
			}
		}
		sql.append(" order by tbl_tableId desc ");
		List counts = jdbcTemplate.query(sql.toString(), param.toArray(), new CountRowMapper());
		return (Integer) (counts.get(0));
	}

	class CountRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			int ret = rs.getInt(1);
			return ret;
		}
	}

	@Override
	public String findValueByColumn(String tableName, String columnName, String whereColumn) {
		String sql = "select " + columnName + " from sys_columns where tbl_belongTable=? and tbl_columnName=?";
		Object[] params = new Object[] { tableName, whereColumn };
		try {
			List values = jdbcTemplate.query(sql, params, new ColumnValueRowMapper());
			if (values != null && values.size() > 0 && values.get(0) != null) {
				return values.get(0).toString();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	class ColumnValueRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Object ret = rs.getObject(1);
			return ret;
		}
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void update(Column column, Map<String, String> dyncMap) {
		String sql = "update sys_columns set tbl_columnName=?,tbl_remark=?,tbl_columnZhName=?,tbl_columnEnName=?,tbl_tableId=?,tbl_belongTable=?,tbl_dataType=?,tbl_defaultValue=?,tbl_length=?,tbl_isPrimaryKey=?,tbl_isNullable=?,tbl_isPublish=?,tbl_comment=?,comm_updateBy=?,comm_updateDate=? where id=?";
		Object[] params = new Object[] { column.getColumnName(), column.getRemark(), column.getColumnZhName(),column.getColumnEnName(),  column.getTableId(),
				column.getBelongTable(), column.getDataType(), column.getDefaultValue(), column.getLength(), column.getIsPrimaryKey(),
				column.getIsNullable(), column.getIsPublish(), column.getComment(), column.getUpdateBy(), column.getUpdateDate(), column.getId() };

		List plist = new ArrayList(Arrays.asList(params));
		if (dyncMap != null) {
			for (Map.Entry<String, String> entry : dyncMap.entrySet()) {
				String key = entry.getKey();
				sql = sql.replace("set ", "set " + key + "=?,");
				plist.add(0, entry.getValue());
			}
		}

		Column c = selectById(column.getId());
		StringBuffer alterSql = new StringBuffer("alter table ");
		alterSql.append(column.getBelongTable());
		if (c.getColumnName().equals(column.getColumnName())) {
			alterSql.append(" modify ");
			if ("timestamp".equals(column.getDataType()) || "date".equals(column.getDataType())) {
				alterSql.append(column.getColumnName() + " " + column.getDataType() + " NULL");
			} else if ("longtext".equals(column.getDataType())) {
				alterSql.append(column.getColumnName() + " " + column.getDataType());
			} else {
				alterSql.append(column.getColumnName() + " " + column.getDataType() + "(" + column.getLength() + ")");
			}
		} else {
			alterSql.append(" change column ");
			if ("timestamp".equals(column.getDataType()) || "date".equals(column.getDataType())) {
				alterSql.append(c.getColumnName() + " " + column.getColumnName() + " " + column.getDataType() + " NULL");
			} else if ("longtext".equals(column.getDataType())) {
				alterSql.append(column.getColumnName() + " " + column.getDataType());
			} else {
				alterSql.append(c.getColumnName() + " " + column.getColumnName() + " " + column.getDataType() + "(" + column.getLength() + ")");
			}
		}
		if (column.getDefaultValue() != null && !column.getDefaultValue().equals("")) {
			alterSql.append(" DEFAULT '" + column.getDefaultValue() + "'");
		}
		jdbcTemplate.execute(alterSql.toString());
		jdbcTemplate.update(sql, plist.toArray());

	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void insert(Column u, boolean flag, Map<String, String> dyncMap) {
		String sql = "insert into sys_columns(id,tbl_columnName,tbl_columnZhName,tbl_columnEnName,tbl_tableId,tbl_belongTable,tbl_dataType,tbl_defaultValue,tbl_length,tbl_isPrimaryKey,tbl_isNullable,tbl_isPublish,tbl_comment,comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,comm_status,tbl_remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[] { UniqueIdUtil.genId(), u.getColumnName(), u.getColumnZhName(),u.getColumnEnName(), u.getTableId(), u.getBelongTable(),
				u.getDataType(), u.getDefaultValue(), u.getLength(), u.getIsPrimaryKey(), u.getIsNullable(), u.getIsPublish(), u.getComment(),
				u.getCreateBy(), u.getCreateDate(), u.getUpdateBy(), u.getUpdateDate(), u.getStatus(), u.getRemark() };
		List plist = new ArrayList(Arrays.asList(params));
		if (dyncMap != null) {
			for (Map.Entry<String, String> entry : dyncMap.entrySet()) {
				String key = entry.getKey();
				sql = sql.replace("columns(", "columns(" + key + ",");
				sql = sql.replace("?)", "?,?)");
				plist.add(0, entry.getValue());
			}
		}
		StringBuffer alterSql = new StringBuffer("alter table ");
		alterSql.append(u.getBelongTable());
		alterSql.append(" add column ");
		if ("timestamp".equals(u.getDataType()) || "date".equals(u.getDataType())) {
			alterSql.append(u.getColumnName() + " " + u.getDataType() + " NULL");
		} else if ("longtext".equals(u.getDataType())) {
			alterSql.append(u.getColumnName() + " " + u.getDataType());
		} else {
			alterSql.append(u.getColumnName() + " " + u.getDataType() + "(" + u.getLength() + ")");
		}
		if (u.getDefaultValue() != null && !"".equals(u.getDefaultValue())) {
			alterSql.append(" DEFAULT '" + u.getDefaultValue() + "'");
		}
		if (flag == true) {
			jdbcTemplate.execute(alterSql.toString());
		}
		jdbcTemplate.update(sql, plist.toArray());
	}

	@Override
	public List queryColumnByField(Long tableId, String columnZhName, String field) {
		String sql = "select * from sys_columns  where tbl_tableId=? and " + field + "=?";
		Object[] params = new Object[] { tableId, columnZhName };
		return jdbcTemplate.query(sql, params, new ColumnFieldRowMapper());
	}

	class ColumnFieldRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Column u = new Column();
			u.setId(rs.getLong("id"));
			u.setColumnName(rs.getString("tbl_columnName"));
			u.setColumnZhName(rs.getString("tbl_columnZhName"));
			u.setColumnEnName(rs.getString("tbl_columnEnName"));
			u.setComment(rs.getString("tbl_comment"));
			u.setDataType(rs.getString("tbl_dataType"));
			u.setBelongTable(rs.getString("tbl_belongTable"));
			u.setDefaultValue(rs.getString("tbl_defaultValue"));
			u.setLength(rs.getInt("tbl_length"));
			u.setRemark(rs.getString("tbl_remark"));
			return u;
		}
	}

	@Override
	public List<Map<String, Object>> findColumnsByTableId(String modelTableId) {
		String sql = "select * from sys_columns  where tbl_tableId=?";
		Object[] params = new Object[] { modelTableId };
		return jdbcTemplate.query(sql, params, new ColumnMapRowMapper());
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void deleteByTableId(Long tableId) {
		String sql = "delete from sys_columns where tbl_tableId=?";
		Object[] params = new Object[] { tableId };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void updateStatus(Long id, int i) {
		String sql = "update sys_columns set comm_status=? where id=?";
		Object[] params = new Object[] { i, id };
		jdbcTemplate.update(sql, params);
	}

	@Override
	@CacheEvict(value={"resourceCache","domainCache","formCache","tabulationCache"},allEntries=true,beforeInvocation=true)
	public void logicDelete(Long id) {
		String sql = "update sys_columns set tbl_dstatus = 1 where id=?";
		Object[] params = new Object[] { id };

		// Column column = selectById(id);
		// String columnSql = "alter table " + column.getBelongTable() +
		// " drop column " + column.getColumnName();

		jdbcTemplate.update(sql, params);
		// jdbcTemplate.execute(columnSql);
	}

	/*
	 * @see com.cloudstongplatform.resource.metadata.dao.ColumnDao#
	 * clearCfgColumnByTableName(java.lang.String)
	 */
	@Override
	public void clearCfgColumnByTableName(String tableName) {
		String sql = "delete from sys_columnconfig where tbl_belongtable =  ?";
		jdbcTemplate.update(sql, tableName);
	}

	/*
	 * @see
	 * com.cloudstongplatform.resource.metadata.dao.ColumnDao#saveCfgColumns(java
	 * .util.List)
	 */
	@Override
	public void saveCfgColumns(final List<LinkedHashMap<String, Object>> list) {
		String sql = "insert into sys_columnconfig(id,tbl_isview,tbl_isedit,tbl_formorder,tbl_listorder,"
				+ "tbl_defaultvalue,tbl_inputtype,tbl_notnull,tbl_isquery,tbl_isinlist,tbl_belongtable,tbl_enname,tbl_chname)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@SuppressWarnings("unchecked")
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Collection<Object> column = list.get(i).values();
				Object[] objs = column.toArray();
				LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) objs[1];
				ps.setLong(1, Long.valueOf(map.get("id").toString()));
				ps.setInt(2, Integer.valueOf(map.get("isView").toString()));
				ps.setInt(3, Integer.valueOf(map.get("isEdit").toString()));
				ps.setInt(4, Integer.valueOf(map.get("formOrder").toString()));
				ps.setInt(5, Integer.valueOf(map.get("listOrder").toString()));
				ps.setString(6, map.get("defaultValue") == null ? "" : map.get("defaultValue").toString());
				ps.setInt(7, Integer.valueOf(map.get("inputType").toString()));
				ps.setInt(8, Integer.valueOf(map.get("notNull").toString()));
				ps.setInt(9, Integer.valueOf(map.get("isQuery").toString()));
				ps.setInt(10, Integer.valueOf(map.get("isInList").toString()));
				ps.setString(11, map.get("belongTable")==null ? "":map.get("belongTable").toString());
				ps.setString(12, map.get("enName")==null?"":map.get("enName").toString());
				ps.setString(13, map.get("zhName")==null?"":map.get("zhName").toString());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
	}

	/*
	 * @see com.cloudstongplatform.resource.metadata.dao.ColumnDao#
	 * selectCfgColumnByTableName(java.lang.String)
	 */
	@Override
	public List<ConfigColumn> selectCfgColumnByTableName(final String tableName) {
		List<ConfigColumn> list = null;
		String sql = "select * from sys_columnconfig where tbl_belongtable = ? order by tbl_formorder asc,tbl_listorder asc";
		list = jdbcTemplate.query(sql, new RowMapper<ConfigColumn>() {

			@Override
			public ConfigColumn mapRow(ResultSet rs, int i) throws SQLException {
				ConfigColumn c = new ConfigColumn();
				c.setId(rs.getLong("id"));
				c.setIsView(rs.getInt("tbl_isview"));
				c.setIsEdit(rs.getInt("tbl_isedit"));
				c.setFormOrder(rs.getInt("tbl_formorder"));
				c.setListOrder(rs.getInt("tbl_listorder"));
				c.setDefaultValue(rs.getObject("tbl_defaultvalue"));
				c.setInputType(rs.getInt("tbl_inputtype"));
				c.setNotNull(rs.getInt("tbl_notnull"));
				c.setIsQuery(rs.getInt("tbl_isquery"));
				c.setIsInList(rs.getInt("tbl_isinlist"));
				c.setBelongTable(rs.getString("tbl_belongtable"));
				c.setEnName(rs.getString("tbl_enname"));
				c.setZhName(rs.getString("tbl_chname"));
				return c;
			}

		}, tableName);
		return list;
	}

	/*
	 * @see
	 * com.cloudstongplatform.resource.metadata.dao.ColumnDao#selectCfgColumn(
	 * java.lang.String)
	 */
	@Override
	public List<ConfigColumn> selectCfgColumn(String tableName) {
		List<ConfigColumn> list = null;
		String sql = "select * from (select * from sys_columns cfg where cfg.tbl_belongtable = ? and cfg.tbl_columnName not like 'comm_%' ORDER BY cfg.comm_createDate asc) a "
				+ "union all "
				+ "select * from (select * from sys_columns cfg1 where cfg1.tbl_belongtable = ? and cfg1.tbl_columnName like 'comm_%' ORDER BY cfg1.comm_createDate asc ) b";
		list = jdbcTemplate.query(sql, new RowMapper<ConfigColumn>() {
			@Override
			public ConfigColumn mapRow(ResultSet rs, int i) throws SQLException {
				ConfigColumn c = new ConfigColumn();
				c.setId(rs.getLong("id"));
				String columnName = rs.getString("tbl_columnName");
				c.setEnName(rs.getString("tbl_columnEnName"));
				c.setZhName(rs.getString("tbl_columnZhName"));

				if (columnName.startsWith("comm_")) {
					c.setIsView(0);
					c.setIsEdit(0);
					c.setIsInList(0);
					c.setIsQuery(0);
					c.setNotNull(0);
				} else {
					c.setIsView(1);
					c.setIsEdit(1);
					c.setIsQuery(1);
					c.setIsInList(1);
					c.setNotNull(Math.abs(rs.getInt("tbl_isNullable") - 1));
				}
				String dataType = rs.getString("tbl_dataType");
				if (dataType.equalsIgnoreCase("date") || dataType.equalsIgnoreCase("timestamp")) {
					c.setInputType(4);
				} else {
					c.setInputType(1);
				}
				c.setFormOrder(0);
				c.setListOrder(0);
				c.setDefaultValue("");
				c.setBelongTable(rs.getString("tbl_belongTable"));

				return c;
			}
		}, tableName, tableName);
		return list;
	}

	@Override
	public int count(String sql, Object[] array) {
		List counts = jdbcTemplate.query(sql, array, new CountRowMapper());
		return (Integer) (counts.get(0));
	}

}