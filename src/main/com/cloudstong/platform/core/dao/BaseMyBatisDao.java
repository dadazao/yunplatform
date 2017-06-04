/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.dao;

import java.io.Serializable;
import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;

/**
 * @author Allan Created on 2014-8-26
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:
 * 
 */
public interface BaseMyBatisDao<T, PK extends Serializable> {

	public void save(T entity);

	public void saveOrUpdate(T entity);

	public int delById(PK pk);

	public int delBySqlKey(String sqlKey, Object params);

	public int update(T entity);

	public int update(String sqlKey, Object params);

	public Object getById(PK pk);

	public int getCount();

	public int getCount(QueryCriteria queryCriteria);

	public List<T> getList(String statementName, Object params);

	public List<T> getList(String statementName, QueryCriteria queryCriteria);

	public List<T> getList(String statementName, Object params, QueryCriteria queryCriteria);

	public List<T> getAll();

	public List<T> getAll(QueryCriteria queryCriteria);

	public PageResult query(QueryCriteria queryCriteria);

	public PageResult query(String statementName, QueryCriteria queryCriteria);

	public PageResult query(String statementName, Object params, QueryCriteria queryCriteria);

	public T getUnique(String sqlKey, Object paramObject);

	public Object getOne(String sqlKey, Object params);

	public List<T> getBySqlKey(String sqlKey, Object params, QueryCriteria queryCriteria);

	public List<T> getBySqlKey(String sqlKey, QueryCriteria queryCriteria);

	public List<T> getBySqlKey(String sqlKey, Object params);

}
