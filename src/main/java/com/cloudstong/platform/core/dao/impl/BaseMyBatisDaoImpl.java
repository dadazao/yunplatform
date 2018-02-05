/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.dao.impl;

import static org.springframework.util.Assert.notNull;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ReflectionUtils;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.BaseMyBatisDao;
import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.mybatis.IbatisSql;
import com.cloudstong.platform.core.util.UniqueIdUtil;

/**
 * @author Allan Created on 2014-8-26
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:
 * 
 */
public class BaseMyBatisDaoImpl<T, PK extends Serializable> extends DaoSupport implements BaseMyBatisDao<T, PK> {

	protected final Log log = LogFactory.getLog(getClass());

	protected SqlSessionFactory sqlSessionFactory;

	protected SqlSessionTemplate sqlSessionTemplate;

	private boolean externalSqlSession;

	@Resource
	protected JdbcTemplate jdbcTemplate;

	@Resource
	Properties configproperties;

	protected String getDbType() {
		return configproperties.getProperty("jdbc.dbType");
	}

	private Class<T> entityClass;

	@Resource(name = "sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		if (!this.externalSqlSession) {
			this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		}
	}

	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		notNull(this.sqlSessionTemplate, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
	}

	public BaseMyBatisDaoImpl() {
		if (ParameterizedType.class.isAssignableFrom(getClass().getGenericSuperclass().getClass())) {
			Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments();
			entityClass = (Class<T>) actualTypeArguments[0];
		}
	}

	@Override
	public void save(T entity) {
		String addStatement = getIbatisMapperNamespace() + ".save";
		if (entity instanceof EntityBase) {
			EntityBase eb = (EntityBase) entity;
			eb.setCreateDate(new Date());
			Long curUserId = AppContext.getCurrentUserId();
			if (curUserId != null) {
				eb.setCreateBy(curUserId);
			}
			if (eb.getId() == null) {
				eb.setId(UniqueIdUtil.genId());
			}
		}
		this.sqlSessionTemplate.insert(addStatement, entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		if (entity instanceof EntityBase) {
			EntityBase eb = (EntityBase) entity;
			if (eb.getId() == null) {
				save(entity);
			} else {
				update(entity);
			}
		}
	}

	@Override
	public int delById(PK pk) {
		String delStatement = getIbatisMapperNamespace() + ".delById";
		int affectCount = this.sqlSessionTemplate.delete(delStatement, pk);
		return affectCount;
	}

	public int delBySqlKey(String sqlKey, Object params) {
		String delStatement = getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = getSqlSessionTemplate().delete(delStatement, params);
		return affectCount;
	}

	@Override
	public int update(T entity) {
		String updStatement = getIbatisMapperNamespace() + ".update";

		if ((entity instanceof EntityBase)) {
			EntityBase eb = (EntityBase) entity;
			eb.setUpdateDate(new Date());

			Long curUserId = AppContext.getCurrentUserId();
			if (curUserId != null) {
				eb.setUpdateBy(curUserId);
			}
		}

		int affectCount = this.sqlSessionTemplate.update(updStatement, entity);
		return affectCount;
	}

	public int update(String sqlKey, Object params) {
		String updStatement = getIbatisMapperNamespace() + "." + sqlKey;
		int affectCount = getSqlSessionTemplate().update(updStatement, params);
		return affectCount;
	}

	@Override
	public Object getById(PK pk) {
		String getStatement = getIbatisMapperNamespace() + ".getById";
		T object = (T) this.sqlSessionTemplate.selectOne(getStatement, pk);
		return object;
	}

	public Object getOne(String sqlKey, Object params) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;
		Object object = getSqlSessionTemplate().selectOne(statement, params);
		return object;
	}

	@Override
	public List<T> getList(String statementName, Object params) {
		Map filters = new HashMap();
		if (params != null) {
			if ((params instanceof Map)) {
				filters.putAll((Map) params);
			} else {
				try {
					Map parameterObject = BeanUtils.describe(params);
					filters.putAll(parameterObject);
				} catch (Exception e) {
					ReflectionUtils.handleReflectionException(e);
				}
			}
		}

		return this.sqlSessionTemplate.selectList(statementName, filters);
	}

	@Override
	public List<T> getList(String statementName, Object params, QueryCriteria queryCriteria) {
		if (queryCriteria == null || queryCriteria.getPageSize() == -1) {
			return getList(statementName, params);
		}

		Map filters = new HashMap();
		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			filters.put("orderField", queryCriteria.getOrderField());
			filters.put("orderDirection", queryCriteria.getOrderDirection());
		}
		if (params != null) {
			if ((params instanceof Map)) {
				filters.putAll((Map) params);
			} else {
				try {
					Map parameterObject = BeanUtils.describe(params);
					filters.putAll(parameterObject);
				} catch (Exception e) {
					ReflectionUtils.handleReflectionException(e);
				}
			}
		}
		return this.sqlSessionTemplate.selectList(statementName, filters,
				new RowBounds(queryCriteria.getCurrentIndex(), queryCriteria.getPageSize()));
	}

	@Override
	public List<T> getList(String statementName, QueryCriteria queryCriteria) {
		if (queryCriteria == null || queryCriteria.getPageSize() == -1) {
			return getList(statementName, (Object) null);
		}

		Map filters = new HashMap();
		if (queryCriteria.getOrderField() != null && !"".equals(queryCriteria.getOrderField())) {
			filters.put("orderField", queryCriteria.getOrderField());
			filters.put("orderDirection", queryCriteria.getOrderDirection());
		}
		Map params = queryCriteria.getQueryCondition();
		if (params != null) {
			if ((params instanceof Map)) {
				filters.putAll((Map) params);
			} else {
				try {
					Map parameterObject = BeanUtils.describe(params);
					filters.putAll(parameterObject);
				} catch (Exception e) {
					ReflectionUtils.handleReflectionException(e);
				}
			}
		}
		return this.sqlSessionTemplate.selectList(statementName, filters,
				new RowBounds(queryCriteria.getCurrentIndex(), queryCriteria.getPageSize()));
	}

	@Override
	public List<T> getAll() {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		return this.sqlSessionTemplate.selectList(statementName, null);
	}

	@Override
	public List<T> getAll(QueryCriteria queryCriteria) {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		return getList(statementName, queryCriteria);
	}

	public List<T> getBySqlKey(String sqlKey, Object params, QueryCriteria queryCriteria) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;
		return getList(statement, params, queryCriteria);
	}

	public List<T> getBySqlKey(String sqlKey, QueryCriteria queryCriteria) {
		return getBySqlKey(sqlKey, queryCriteria.getQueryCondition(), queryCriteria);
	}

	public List<T> getBySqlKey(String sqlKey, Object params) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;
		return this.sqlSessionTemplate.selectList(statement, params);
	}

	public List<T> getBySqlKey(String sqlKey) {
		String statement = getIbatisMapperNamespace() + "." + sqlKey;
		return this.sqlSessionTemplate.selectList(statement);
	}

	@Override
	public PageResult query(QueryCriteria queryCriteria) {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		List contentList = getList(statementName, queryCriteria);
		int count = getCount(queryCriteria);

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);
		return result;
	}

	@Override
	public PageResult query(String statementName, QueryCriteria queryCriteria) {
		List contentList = getList(statementName, queryCriteria);
		int count = getCount(queryCriteria);

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);
		return result;
	}

	@Override
	public PageResult query(String statementName, Object params, QueryCriteria queryCriteria) {
		List contentList = getList(statementName, params, queryCriteria);
		int count = getCount(params);

		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(queryCriteria.getCurrentPage());
		result.setCountPage((count + queryCriteria.getPageSize() - 1) / queryCriteria.getPageSize());
		result.setPageSize(queryCriteria.getPageSize());
		result.setTotalCount(count);
		return result;
	}

	@Override
	public int getCount() {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		QueryCriteria queryCriteria = new QueryCriteria();
		IbatisSql ibatisSql = getIbatisSql(statementName, queryCriteria.getQueryCondition());
		return jdbcTemplate.queryForObject(ibatisSql.getCountSql(), ibatisSql.getParameters(), Integer.class);
	}

	public int getCount(Object params) {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		IbatisSql ibatisSql = getIbatisSql(statementName, params);
		return jdbcTemplate.queryForObject(ibatisSql.getCountSql(), ibatisSql.getParameters(), Integer.class);
	}

	public int getCount(QueryCriteria queryCriteria) {
		String statementName = getIbatisMapperNamespace() + ".getAll";
		if (queryCriteria == null) {
			queryCriteria = new QueryCriteria();
		}
		IbatisSql ibatisSql = getIbatisSql(statementName, queryCriteria.getQueryCondition());
		return jdbcTemplate.queryForObject(ibatisSql.getCountSql(), ibatisSql.getParameters(), Integer.class);
	}

	@Override
	public T getUnique(String sqlKey, Object params) {
		String getStatement = getIbatisMapperNamespace() + "." + sqlKey;
		T object = (T) this.sqlSessionTemplate.selectOne(getStatement, params);
		return object;
	}

	public String getIbatisMapperNamespace() {
		return entityClass.getName();
	}

	public IbatisSql getIbatisSql(String id, Object params) {
		IbatisSql ibatisSql = new IbatisSql();

		Collection coll = sqlSessionFactory.getConfiguration().getMappedStatementNames();
		MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(id);
		BoundSql boundSql = ms.getBoundSql(params);

		List ResultMaps = ms.getResultMaps();
		if ((ResultMaps != null) && (ResultMaps.size() > 0)) {
			ResultMap ResultMap = (ResultMap) ms.getResultMaps().get(0);
			ibatisSql.setResultClass(ResultMap.getType());
		}

		ibatisSql.setSql(boundSql.getSql());

		List parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Object[] parameterArray = new Object[parameterMappings.size()];
			MetaObject metaObject = params == null ? null
					: MetaObject.forObject(params, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(),
							new DefaultReflectorFactory());
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = (ParameterMapping) parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					Object value;
					if (params == null) {
						value = null;
					} else {
						if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(params.getClass())) {
							value = params;
						} else {
							if (boundSql.hasAdditionalParameter(propertyName)) {
								value = boundSql.getAdditionalParameter(propertyName);
							} else if ((propertyName.startsWith("__frch_"))
									&& (boundSql.hasAdditionalParameter(prop.getName()))) {
								value = boundSql.getAdditionalParameter(prop.getName());
								if (value != null)
									value = MetaObject
											.forObject(params, new DefaultObjectFactory(),
													new DefaultObjectWrapperFactory(), new DefaultReflectorFactory())
											.getValue(propertyName.substring(prop.getName().length()));
							} else {
								value = metaObject == null ? null : metaObject.getValue(propertyName);
							}
						}
					}
					parameterArray[i] = value;
				}
			}
			ibatisSql.setParameters(parameterArray);
		}

		return ibatisSql;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.externalSqlSession = true;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
