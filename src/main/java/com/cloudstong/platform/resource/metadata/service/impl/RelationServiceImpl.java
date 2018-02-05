package com.cloudstong.platform.resource.metadata.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.resource.metadata.dao.RelationDao;
import com.cloudstong.platform.resource.metadata.model.Relation;
import com.cloudstong.platform.resource.metadata.service.RelationService;

/**
 * @author michael Created on 2012-11-21
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:表关系服务接口实现类
 */
@Repository("relationService")
public class RelationServiceImpl implements RelationService {

	@Resource
	private RelationDao relationDao;

	public RelationDao getRelationDao() {
		return relationDao;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	@Override
	public Relation selectByMainIdAndSubId(Long mainId, Long subId) {
		return relationDao.selectByMainIdAndSubId(mainId, subId);
	}

	@Override
	public List<Relation> queryRelation(QueryCriteria qc) {
		Map<String, Object> map = qc.getQueryCondition();
		Iterator iterator = map.entrySet().iterator();
		StringBuffer sql = new StringBuffer(
				"select r.*,t1.tbl_tableZhName as tbl_mainTableName,t2.tbl_tableZhName as tbl_subTableName from sys_relation r left join sys_tables t1 on r.tbl_mainid = t1.id left join sys_tables t2 on r.tbl_subid = t2.id where 1=1 ");
		List param = new ArrayList();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
			if (entry.getValue() instanceof String) {
				sql.append(" and " + entry.getKey() + " like ? ");
			} else {
				sql.append(" and " + entry.getKey() + " = ? ");
			}
			param.add(entry.getValue());
		}
		sql.append(" order by r.id desc ");
		return this.relationDao.select(sql.toString(), param.toArray(), qc.getCurrentIndex(), qc.getPageSize());
	}

	@Override
	public int countRelation() {
		return this.relationDao.count();
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public Long doSaveRelation(Relation relation) {
		if ("2".equals(relation.getConnecttype())) {
			return relationDao.doSave(relation);
		} else {
			return relationDao.createForeignKey(relation);
		}
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doUpdateRelation(Relation relation) {
		if ("2".equals(relation.getConnecttype())) {
			relationDao.doUpdate(relation);
		} else {
			relationDao.updateForeignKey(relation);
			// to do...
		}
	}

	@Override
	@CacheEvict(value = { "formCache", "tabulationCache", "domainCache" }, allEntries = true, beforeInvocation = true)
	public void doDeleteRelations(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			this.relationDao.delete(id);
		}
	}

	@Override
	public Relation queryById(Long id) {
		return this.relationDao.selectById(id);
	}

	@Override
	public List findReplyRelation(Relation relation) {
		return this.relationDao.findReplyRelation(relation);
	}

	@Override
	public Relation selectByMainNameAndSubName(String mainTable, String subTable) {
		return relationDao.selectByMainNameAndSubName(mainTable, subTable);
	}

	@Override
	public void doLogicDeleteRelations(Long[] selectedIDs) {
		for (Long id : selectedIDs) {
			this.relationDao.logicDelete(id);
		}
	}

	@Override
	public boolean checkExistForeignKey(Relation relation) {
		// 根据关系类型判断
		String mainTable = relation.getMainTableName();
		String subTable = relation.getSubTableName();

		if (2 == relation.getRelationType()) { // 多对一
			String tmp = subTable;
			subTable = mainTable;// 主次表颠倒
			mainTable = tmp;
		}
		String foreignKey = StringUtil.getForeignKeyString(mainTable, subTable);
		return relationDao.findForeignKey(subTable, foreignKey);
	}
}
