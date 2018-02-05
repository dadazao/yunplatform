package com.cloudstong.platform.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.BaseJpaDao;
import com.cloudstong.platform.core.model.EntityBase;
import com.cloudstong.platform.core.util.DaoUtil;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.system.model.SysUser;

/**
 * 
 * @author Allan Created on 2014-08-12
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description: 泛化的基DAO实现
 */
public class BaseDaoJpa<T extends EntityBase, PK extends Serializable> extends JpaDaoSupport implements BaseJpaDao<T, PK> {
	protected final Log log = LogFactory.getLog(getClass());

	private Class<T> entityClass;

	//@Resource(name = "entityManagerFactory")
	public void setHibernateEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		super.setEntityManagerFactory(entityManagerFactory);
	}

	public BaseDaoJpa() {
		if (ParameterizedType.class.isAssignableFrom(getClass().getGenericSuperclass().getClass())) {
			Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
			entityClass = (Class<T>) actualTypeArguments[0];
		}
	}

	public BaseDaoJpa(final Class<T> persistentClass) {
		this.entityClass = persistentClass;
	}

	public List<T> find(String jpaQL, Object... paramValues) {
		return super.getJpaTemplate().find(jpaQL, paramValues);
	}

	public List<T> find(String hql, List paramValueList) {
		return this.find(hql, paramValueList.toArray());
	}

	public List<T> getAll(final int... paging) {
		return (List<T>) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery("select obj from " + entityClass.getName() + " obj");
				if (paging.length == 2 && paging[0] + paging[1] > 0) {
					query.setFirstResult(paging[0]);
					query.setMaxResults(paging[1]);
				}
				return query.getResultList();
			}
		});
	}

	public T get(PK pk) {
		return (T) (super.getJpaTemplate().find(this.entityClass, pk));
	}

	public Object eagerFindById(Class clazz, PK pk) {
		Object result = super.getJpaTemplate().find(clazz, pk);
		// super.getJpaTemplate().initialize(result);
		return result;
	}

	public Object findById(Class clazz, PK pk) {
		return super.getJpaTemplate().find(clazz, pk);
	}

	public boolean exists(PK pk) {
		T t = this.get(pk);
		if (t == null)
			return false;
		else
			return true;
	}

	public void flush() {
		super.getJpaTemplate().flush();
	}

	public List namedQuery(String queryName, Object... paramValues) {
		return (List) super.getJpaTemplate().findByNamedQuery(queryName, paramValues);
	}

	public List namedQuery(String queryName, List paramValueList) {
		return this.namedQuery(queryName, paramValueList.toArray());
	}

	public List nativeQuery(final String sql, final Object... paramValues) {
		return (List) super.getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNativeQuery(sql);
				for (int i = 0; i < paramValues.length; i++)
					query.setParameter(i, paramValues[i]);
				return query.getResultList();
			}
		});
	}

	public List nativeQuery(String sql, List paramValueList) {
		return this.nativeQuery(sql, paramValueList.toArray());
	}

	public List nativeQuery(final String sql, final Class resultClass, final Object... paramValues) {
		return (List) super.getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNativeQuery(sql, resultClass);
				for (int i = 0; i < paramValues.length; i++)
					query.setParameter(i, paramValues[i]);
				return query.getResultList();
			}
		});
	}

	public List nativeQuery(String sql, Class resultClass, List paramValueList) {
		return this.nativeQuery(sql, resultClass, paramValueList.toArray());
	}

	public PageResult nativeQuery(final String sql, final Class resultClass, final QueryCriteria criteria) {
		List contentList = (List) super.getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createNativeQuery(sql, resultClass);
				Set<Map.Entry<String, Object>> entrySet = criteria.getQueryCondition().entrySet();
				for (Map.Entry<String, Object> entry : entrySet) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
				return query.getResultList();
			}
		});

		Integer count = (Integer) super.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String countSql = DaoUtil.getCountQL(sql);
				Query query = em.createNativeQuery(countSql);
				Set<Map.Entry<String, Object>> entrySet = criteria.getQueryCondition().entrySet();
				for (Map.Entry<String, Object> entry : entrySet) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
				return query.getSingleResult();
			}
		});
		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(criteria.getCurrentPage());
		result.setCountPage((count + criteria.getPageSize() - 1) / criteria.getPageSize());
		result.setTotalCount(count);
		return result;
	}

	public List query(String hql, Object... paramValues) {
		hql = DaoUtil.localizeHql(hql);
		return (List) super.getJpaTemplate().find(hql, paramValues);
	}

	public List query(String hql, List paramValueList) {
		return this.query(hql, paramValueList.toArray());
	}

	public PageResult query(final QueryCriteria criteria) {
		return this.query(this.entityClass, criteria);
	}

	public PageResult query(final Class clazz, final QueryCriteria criteria) {
		try {
			final StringBuffer jpqlBuf = new StringBuffer();
			jpqlBuf.append("select e from ").append(clazz.getSimpleName()).append(" e ");
			// build where clause.
			final StringBuffer whereClauseBuf = new StringBuffer();
			final ArrayList paramValueList = new ArrayList();
			if (criteria.getQueryCondition() != null && criteria.getQueryCondition().size() > 0) {
				whereClauseBuf.append("where 1=1");
				
				Set<Map.Entry> entrySet = (Set<Map.Entry>) (criteria.getQueryCondition().entrySet());
				for (Map.Entry entry : entrySet) {
					whereClauseBuf.append(" and e.").append((String) entry.getKey());
					String v = null;
					if (entry.getValue() instanceof String) {
						v = (String) entry.getValue();
					}
					if (v != null) {
						if (v.startsWith("%") || v.endsWith("%")) {
							whereClauseBuf.append(" like ?");
						} else if (v.startsWith(">")) {
							whereClauseBuf.append(" > ? ");
							v = v.substring(1);
						} else if (v.startsWith("<")) {
							whereClauseBuf.append(" < ? ");
							v = v.substring(1);
						} else if (v.startsWith("=")) {
							whereClauseBuf.append(" = ? ");
							v = v.substring(1);
						} else if (v.startsWith(">=")) {
							whereClauseBuf.append(" >= ? ");
							v = v.substring(2);
						} else if (v.startsWith("<=")) {
							whereClauseBuf.append(" <= ? ");
							v = v.substring(2);
						} else {
							whereClauseBuf.append(" = ? ");
						}
						paramValueList.add(v);
					} else {
						whereClauseBuf.append("=?");
						paramValueList.add(entry.getValue());
					}
				}
			}
			jpqlBuf.append(whereClauseBuf);
			// build order by clause.
			if (criteria.getSortCondition() != null && criteria.getSortCondition().size() > 0) {
				jpqlBuf.append(" order by e.").append(criteria.getSortCondition().get(0));
				for (int i = 1; i < criteria.getSortCondition().size(); i++) {
					jpqlBuf.append(',').append("e." + criteria.getSortCondition().get(i));
				}
			} else if (criteria.getOrderField() != null && criteria.getOrderField().length() > 0) {
				jpqlBuf.append(" order by e.").append(criteria.getOrderField());
				if (criteria.getOrderDirection() != null)
					jpqlBuf.append(' ').append(criteria.getOrderDirection());
			}
			List contentResults = (List) this.getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					String jpql = DaoUtil.localizeHql(jpqlBuf.toString());
					Query query = em.createQuery(jpql);
					query.setFirstResult(criteria.getCurrentIndex());
					query.setMaxResults(criteria.getPageSize());
					for (int i = 0; i < paramValueList.size(); i++) {
						query.setParameter(i + 1, paramValueList.get(i));
					}
					return query.getResultList();
				}
			});

			Long count = (Long) this.getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					String ql = "select count(e) from " + clazz.getSimpleName() + " e " + DaoUtil.localizeHql(whereClauseBuf.toString());
					Query query = em.createQuery(ql);
					for (int i = 0; i < paramValueList.size(); i++) {
						query.setParameter(i + 1, paramValueList.get(i));
					}
					return query.getSingleResult();
				}
			});
			PageResult result = new PageResult();
			result.setContent(contentResults);
			result.setCurrentPage(criteria.getCurrentPage());
			result.setCountPage((count.intValue() + criteria.getPageSize() - 1) / criteria.getPageSize());
			result.setTotalCount(count.intValue());
			result.setPageSize(criteria.getPageSize());
			if (log.isDebugEnabled())
				log.debug("Content count:" + contentResults.size() + ";Total Count:" + count + ";Page Count:" + result.getCountPage());
			return result;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PageResult query(final String hql, final String countHql, final QueryCriteria criteria) {
		final String innerHql = DaoUtil.localizeHql(hql);
		List contentList = (List) super.getJpaTemplate().executeFind(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(innerHql);
				query.setFirstResult(criteria.getCurrentIndex());
				query.setMaxResults(criteria.getPageSize());
				Set<Map.Entry<String, Object>> entrySet = criteria.getQueryCondition().entrySet();
				for (Map.Entry<String, Object> entry : entrySet) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
				return query.getResultList();
			}
		});

		Long count = (Long) super.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(countHql);
				Set<Map.Entry<String, Object>> entrySet = criteria.getQueryCondition().entrySet();
				for (Map.Entry<String, Object> entry : entrySet) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
				return query.getSingleResult();
			}
		});
		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCurrentPage(criteria.getCurrentPage());
		result.setCountPage((count.intValue() + criteria.getPageSize() - 1) / criteria.getPageSize());
		result.setTotalCount(count.intValue());
		return result;
	}

	public PageResult query(final String hql, final QueryCriteria criteria) {
		final String innerHql = DaoUtil.localizeHql(hql);
		List contentList = (List) super.getJpaTemplate().executeFind(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(innerHql);
				query.setFirstResult(criteria.getCurrentIndex());
				query.setMaxResults(criteria.getPageSize());
				Set<Map.Entry<String, Object>> entrySet = criteria.getQueryCondition().entrySet();
				for (Map.Entry<String, Object> entry : entrySet) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
				return query.getResultList();
			}
		});

		Long count = (Long) super.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String countSql = DaoUtil.getCountQL(innerHql);
				Query query = em.createQuery(countSql);
				Set<Map.Entry<String, Object>> entrySet = criteria.getQueryCondition().entrySet();
				for (Map.Entry<String, Object> entry : entrySet) {
					query.setParameter(entry.getKey(), entry.getValue());
				}
				return query.getSingleResult();
			}
		});
		PageResult result = new PageResult();
		result.setContent(contentList);
		result.setCountPage((count.intValue() + criteria.getPageSize() - 1) / criteria.getPageSize());
		result.setTotalCount(count.intValue());
		return result;
	}

	public void remove(T o) {
		this.getJpaTemplate().remove(o);
	}

	public void removeObjects(Collection objs) {
		for (Object obj : objs)
			this.removeObject(obj);
	}

	public void remove(PK pk) {
		this.remove(this.get(pk));
	}

	public void removeById(Class clazz, PK pk) {
		this.removeObject(this.findById(clazz, pk));
	}

	private void removeObject(Object obj) {
		this.getJpaTemplate().remove(obj);
	}

	public T save(T o) {
		this.saveEntity(o, null);
		return o;
	}

	public void save(T o, Date createDate) {
		this.saveEntity(o, createDate);
	}

	public void saveObjects(Collection objs) {
		this.saveObjects(objs, null);
	}

	public void saveObjects(Collection objs, Date createDate) {
		for (Object o : objs)
			this.saveEntity((EntityBase) o, createDate);
	}

	private void saveEntity(EntityBase o, Date createDate) {
		try {
			SysUser user=AppContext.getCurrentUser();
			if (user != null){
				o.setCreateBy(user.getId());
			}
			else {
				o.setCreateBy(-1L);
			}
			o.setUpdateBy(o.getCreateBy());
			if (createDate == null)
				createDate = this.getDbDateTime();
			if (o.getId() == null) {
				o.setId(UniqueIdUtil.genId());
				o.setCreateDate(createDate);
			}
			o.setUpdateDate(createDate);
			super.getJpaTemplate().persist(o);
			super.getJpaTemplate().flush();
		} catch (DataAccessException e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
	}

	public void updateEntityObject(T o) {
		SysUser user = AppContext.getCurrentUser();
		if (user != null)
			o.setUpdateBy(user.getId());
		else
			o.setUpdateBy(-1L);

		Date updateDate = this.getDbDateTime();// new
		if (o != null)
			o.setUpdateDate(updateDate);

		super.getJpaTemplate().merge(o);
		super.getJpaTemplate().flush();
	}

	public void update(T o) {
		this.updateEntity(o, null);
	}

	public void update(T o, Date updateDate) {
		this.updateEntity(o, updateDate);
	}

	public void updateObjects(Collection objs) {
		this.updateObjects(objs, null);
	}

	public void updateObjects(Collection objs, Date updateDate) {
		for (Object o : objs)
			this.updateEntity((EntityBase) o, updateDate);
	}

	private void updateEntity(EntityBase o, Date updateDate) {
		SysUser userInfo = AppContext.getCurrentUser();
		if (userInfo != null)
			o.setUpdateBy(userInfo.getId());
		else
			o.setUpdateBy(-1L);
		if (updateDate == null)
			updateDate = this.getDbDateTime();// new
		if (o != null)
			o.setUpdateDate(updateDate);
		super.getJpaTemplate().merge(o);
	}

	public int execute(final String hql, final Object... paramValues) {
		return (Integer) super.getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery(hql);
				for (int i = 0; i < paramValues.length; i++)
					query.setParameter(i + 1, paramValues[i]);
				return query.executeUpdate();
			}
		});
	}

	public int execute(String hql, List paramValueList) {
		return this.execute(hql, paramValueList.toArray());
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Date getDbDateTime() {
		return new Date();
	}

	public Long getSequenceValue(String sequenceName) {
		List list = this.nativeQuery("SELECT " + sequenceName + ".nextval FROM DUAL");
		BigDecimal b = (BigDecimal) list.get(0);
		return b.longValue();
	}

//	private String getFullEntityName(String simpleEntityName) {
//		SessionFactoryImplementor factory = null;
//		String fullEntityName = factory.getImportedClassName(simpleEntityName);
//		return fullEntityName;
//	}

	public String getEntityPropertyOfNlv(Class clazz) {
		String simpleEntityName = null;
		if (null == clazz)
			simpleEntityName = this.entityClass.getSimpleName();
		else
			simpleEntityName = clazz.getSimpleName();
		char[] chars = simpleEntityName.toCharArray();
		StringBuilder buffer = new StringBuilder();
		int start = 0;
		for (char c : chars) {
			char v = Character.toLowerCase(c);
			buffer.append(v);
			start++;
			break;
		}
		if (start < 1 || start > chars.length - 1)
			throw new IndexOutOfBoundsException();
		buffer.append(chars, start, chars.length - 1);

		return buffer.toString();
	}

	public void mergeEntity(EntityBase o) {
		super.getJpaTemplate().merge(o);
	}

	public T getById(Long id) {
		return (T) (super.getJpaTemplate().find(this.entityClass, id));
	}

	public Object getById(Class clazz, Long id) {
		return super.getJpaTemplate().find(clazz, id);
	}

	public void saveOrUpdate(T o) {
		if (o.getId() == null) {
			o.setId(UniqueIdUtil.genId());
			save(o);
		} else {
			update(o);
		}
	}

	public void refresh(T o) {
		getJpaTemplate().refresh(o);
	}

	public void clear() {
		getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				em.clear();
				return null;
			}
		});
	}
}
