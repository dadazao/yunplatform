/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.BaseJdbcDao;
import com.cloudstong.platform.core.dialect.Dialect;

/**
 * @author Allan
 * 
 *         Created on 2014-8-27
 * 
 *         Description:
 * 
 */
public abstract class BaseJdbcDaoImpl<T, PK extends Serializable> implements BaseJdbcDao<T, PK> {

	@Resource
	protected JdbcTemplate jdbcTemplate;
	@Resource
	protected Dialect dialect;

	public abstract String getTable();

	@Override
	public List<Map<String, Object>> select(String sql, Object[] args, int startRow, int rowsCount) {
		String pageSql = dialect.getLimitString(sql, startRow, rowsCount);
		return jdbcTemplate.queryForList(pageSql, args);
	}

	@Override
	public List<T> select(String sql, Object[] args, int startRow, int rowsCount, RowMapper<T> rowMap) {
		String pageSql = dialect.getLimitString(sql, startRow, rowsCount);
		return jdbcTemplate.query(pageSql, args, rowMap);
	}

	@Override
	public List<Map<String, Object>> select(String sql, Object[] args) {
		return jdbcTemplate.queryForList(sql, args);
	}

	@Override
	public List<T> select(String sql, Object[] args, RowMapper<T> rowMap) {
		return jdbcTemplate.query(sql, args, rowMap);
	}

	@Override
	public List<Map<String, Object>> getAll(String table) {
		String sql = "select * from " + table;
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<T> getAll(RowMapper<T> rowMap) {
		String sql = "select * from " + getTable();
		return jdbcTemplate.query(sql, rowMap);
	}

	@Override
	public void update(String sql, Object[] args) {
		jdbcTemplate.update(sql, args);
	}

	@Override
	public void insert(String sql, Object[] args) {
		jdbcTemplate.update(sql, args);
	}

	@Override
	public T selectById(PK pk, RowMapper<T> rowMap) {
		String sql = "select * from " + getTable() + " where id=?";
		Object[] args = new Object[] { pk };
		return jdbcTemplate.queryForObject(sql, args, rowMap);
	}

	@Override
	public void delete(PK pk) {
		String sql = "delete from " + getTable() + " where id=?";
		Object[] args = new Object[] { pk };
		jdbcTemplate.update(sql, args);
	}

	@Override
	public Object selectById(String table, PK pk, RowMapper rowMap) {
		String sql = "select * from " + table + " where id=?";
		Object[] args = new Object[] { pk };
		return jdbcTemplate.queryForObject(sql, args, rowMap);
	}

	@Override
	public void delete(String table, PK pk) {
		String sql = "delete from " + table + " where id=?";
		Object[] args = new Object[] { pk };
		jdbcTemplate.update(sql, args);
	}

	@Override
	public int count(String sql, Object[] args) {
		return jdbcTemplate.queryForObject(sql, args, Integer.class);
	}

	@Override
	public int count(QueryCriteria queryCriteria) {
		return count(queryCriteria, getTable());
	}

	@Override
	public int count() {
		String sql = "select count(*) from " + getTable();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public int count(QueryCriteria queryCriteria, String table) {
		StringBuffer sql = new StringBuffer("select count(*) from " + table + " where 1=1 ");
		List paramValueList = new ArrayList();
		String where = assembWhere(queryCriteria.getQueryCondition(), paramValueList);
		sql.append(where);
		return jdbcTemplate.queryForObject(sql.toString(), paramValueList.toArray(), Integer.class);
	}

	@Override
	public PageResult query(QueryCriteria queryCriteria, String table) {
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append("select * from " + table + " where 1=1 ");

		List paramValueList = new ArrayList();
		String where = assembWhere(queryCriteria.getQueryCondition(), paramValueList);
		sqlsb.append(where);

		String countSql = dialect.getCountSql(sqlsb.toString());
		int count = count(countSql, paramValueList.toArray());
		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			sqlsb.append(" order by " + queryCriteria.getOrderField() + " " + queryCriteria.getOrderDirection());
		}
		String pageSql = dialect.getLimitString(sqlsb.toString(), queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());

		List<Map<String, Object>> contentList = jdbcTemplate.queryForList(pageSql, paramValueList.toArray());

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);

		return result;
	}

	@Override
	public PageResult query(QueryCriteria queryCriteria, String table, RowMapper rowMap) {
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append("select * from " + table + " where 1=1 ");

		List paramValueList = new ArrayList();
		String where = assembWhere(queryCriteria.getQueryCondition(), paramValueList);
		sqlsb.append(where);

		String countSql = dialect.getCountSql(sqlsb.toString());
		int count = count(countSql, paramValueList.toArray());
		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			sqlsb.append(" order by " + queryCriteria.getOrderField() + " " + queryCriteria.getOrderDirection());
		}
		String pageSql = dialect.getLimitString(sqlsb.toString(), queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());

		List contentList = jdbcTemplate.query(pageSql, paramValueList.toArray(), rowMap);

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);

		return result;
	}

	@Override
	public PageResult query(String sql, QueryCriteria queryCriteria) {
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append(sql);

		List paramValueList = new ArrayList();
		String where = assembWhere(queryCriteria.getQueryCondition(), paramValueList);
		sqlsb.append(where);

		String countSql = dialect.getCountSql(sqlsb.toString());
		int count = count(countSql, paramValueList.toArray());

		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			sqlsb.append(" order by " + queryCriteria.getOrderField() + " " + queryCriteria.getOrderDirection());
		}
		String pageSql = dialect.getLimitString(sqlsb.toString(), queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());

		List<Map<String, Object>> contentList = jdbcTemplate.queryForList(pageSql, paramValueList.toArray());

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);

		return result;
	}

	@Override
	public PageResult query(String sql, QueryCriteria queryCriteria, RowMapper rowMap) {
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append(sql);

		List paramValueList = new ArrayList();
		String where = assembWhere(queryCriteria.getQueryCondition(), paramValueList);
		sqlsb.append(where);

		String countSql = dialect.getCountSql(sqlsb.toString());
		int count = count(countSql, paramValueList.toArray());

		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			sqlsb.append(" order by " + queryCriteria.getOrderField() + " " + queryCriteria.getOrderDirection());
		}
		String pageSql = dialect.getLimitString(sqlsb.toString(), queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());

		List contentList = jdbcTemplate.query(pageSql, paramValueList.toArray(), rowMap);

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);

		return result;
	}

	@Override
	public PageResult query(String sql, Object[] args, QueryCriteria queryCriteria, RowMapper rowMap) {
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append(sql);

		List paramValueList = new ArrayList();
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				paramValueList.add(args[i]);
			}
		}
		String where = assembWhere(queryCriteria.getQueryCondition(), paramValueList);
		sqlsb.append(where);

		String countSql = dialect.getCountSql(sqlsb.toString());
		int count = count(countSql, paramValueList.toArray());

		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			sqlsb.append(" order by m." + queryCriteria.getOrderField() + " " + queryCriteria.getOrderDirection());
		}
		String pageSql = dialect.getLimitString(sqlsb.toString(), queryCriteria.getCurrentIndex(), queryCriteria.getPageSize());

		List contentList = jdbcTemplate.query(pageSql, paramValueList.toArray(), rowMap);

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);
		return result;
	}

	@Override
	public PageResult query(QueryCriteria queryCriteria) {
		return query(queryCriteria, getTable());
	}

	@Override
	public PageResult query(QueryCriteria queryCriteria, RowMapper rowMap) {
		return query(queryCriteria, getTable(), rowMap);
	}

	protected String assembWhere(Map queryCondition, List paramValueList) {
		StringBuffer where = new StringBuffer(" ");
		Set<Map.Entry> entrySet = (Set<Map.Entry>) (queryCondition.entrySet());
		for (Map.Entry entry : entrySet) {
			where.append(" and ").append((String) entry.getKey());
			String v = null;
			if (entry.getValue() instanceof String) {
				v = ((String) entry.getValue()).trim();
			}
			if (v != null) {
				if (v.startsWith("%") || v.endsWith("%")) {
					where.append(" like ?");
				} else if (v.startsWith(">")) {
					where.append(" > ? ");
					v = v.substring(1);
				} else if (v.startsWith("<")) {
					where.append(" < ? ");
					v = v.substring(1);
				} else if (v.startsWith("=")) {
					where.append(" = ? ");
					v = v.substring(1);
				} else if (v.startsWith(">=")) {
					where.append(" >= ? ");
					v = v.substring(2);
				} else if (v.startsWith("<=")) {
					where.append(" <= ? ");
					v = v.substring(2);
				} else if (v.startsWith("!=")) {
					where.append(" != ? ");
					v = v.substring(2);
				} else {
					where.append(" = ? ");
				}
				paramValueList.add(v);
			} else {
				where.append(" = ? ");
				paramValueList.add(entry.getValue());
			}
		}
		return where.toString();
	}

	@Override
	public void batchUpdate(String sql, List<Object[]> batchArgs) {
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	@Override
	public void batchUpdate(String sql, BatchPreparedStatementSetter setter) {
		jdbcTemplate.batchUpdate(sql, setter);
	}

}
