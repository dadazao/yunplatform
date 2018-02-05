package com.cloudstong.platform.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;


/**
 * 
 * @author Allan
 * Created on 2014-08-12
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 泛化的基类DAO接口
 */
public interface BaseJpaDao<T, PK extends Serializable> {
	
	public T save(T o);
	
	public void save(T o, Date createDate);

	public void saveObjects(Collection objs);

	public void saveObjects(Collection objs, Date createDate);

	public void update(T o);
	
	public void update(T o, Date updateDate);

	public void updateObjects(Collection objs);

	public void updateObjects(Collection objs, Date updateDate);
	
	public void saveOrUpdate(T o);
	
	public void remove(PK id);
	
	public void remove(T o);

	public void removeObjects(Collection objs);

	public void removeById(Class clazz, PK pk);
	
	public void refresh(T o);
	
	public void flush();
	
	public void clear();
	
	public boolean exists(PK id);
	
	public T get(PK id);
	
	public T getById(Long id);

	public Object getById(Class clazz, Long id);

	public List<T> getAll(int... paging);
	
	public Object findById(Class clazz, PK pk);

	public List<T> find(String hql, Object... paramValues);

	public List<T> find(String hql, List paramValueList);

	public PageResult query(final QueryCriteria criteria);

	public PageResult query(final Class clazz, final QueryCriteria criteria);
	
	public PageResult query(final String hql, final String countHql, final QueryCriteria criteria);

	public PageResult query(final String hql, final QueryCriteria criteria);
	
	public List namedQuery(String queryName, Object... paramValues);

	public List namedQuery(String queryName, List paramValueList);

	public List nativeQuery(final String sql, final Object... paramValues);

	public List nativeQuery(String sql, List paramValueList);

	public List nativeQuery(final String sql, final Class resultClass, final Object... paramValues);

	public List nativeQuery(String sql, Class resultClass, List paramValueList);

	public PageResult nativeQuery(final String sql, final Class resultClass, final QueryCriteria criteria);

	public Class<T> getEntityClass();

	public void setEntityClass(Class<T> entityClass);
	
}