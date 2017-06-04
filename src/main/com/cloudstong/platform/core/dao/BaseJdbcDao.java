/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan
 * 
 *         Created on 2014-8-27
 * 
 *         Description:
 * 
 */
public interface BaseJdbcDao<T, PK extends Serializable> {

	public List<Map<String, Object>> select(String sql, Object[] args, int startRow, int rowsCount);

	public List<T> select(String sql, Object[] args, int startRow, int rowsCount, RowMapper<T> rowMap);

	public List<Map<String, Object>> select(String sql, Object[] args);

	public List<T> select(String sql, Object[] args, RowMapper<T> rowMap);
	
	public List<Map<String, Object>> getAll(String tableName);

	public List<T> getAll(RowMapper<T> rowMap);

	public void update(String sql, Object[] args);

	public void batchUpdate(String sql, List<Object[]> batchArgs);

	public void batchUpdate(String sql, BatchPreparedStatementSetter setter);

	public void insert(String sql, Object[] args);

	public T selectById(PK pk, RowMapper<T> rowMap);

	public Object selectById(String table, PK pk, RowMapper rowMap);

	public void delete(PK pk);

	public void delete(String table, PK pk);

	public int count(String sql, Object[] args);

	public int count(QueryCriteria queryCriteria);

	public int count(QueryCriteria queryCriteria, String table);

	public int count();

	public PageResult query(String sql, QueryCriteria queryCriteria);

	public PageResult query(String sql, QueryCriteria queryCriteria, RowMapper rowMap);

	public PageResult query(String sql, Object[] args, QueryCriteria queryCriteria, RowMapper rowMap);

	public PageResult query(QueryCriteria queryCriteria);

	public PageResult query(QueryCriteria queryCriteria, String table);

	public PageResult query(QueryCriteria queryCriteria, RowMapper rowMap);

	public PageResult query(QueryCriteria queryCriteria, String table, RowMapper rowMap);

}
