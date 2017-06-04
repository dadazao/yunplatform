package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmFormQuery;

@Repository
public class BpmFormQueryDao extends BaseMyBatisDaoImpl<BpmFormQuery, Long> {
	public Class getEntityClass() {
		return BpmFormQuery.class;
	}

	public BpmFormQuery getByAlias(String alias) {
		return (BpmFormQuery) getUnique("getByAlias", alias);
	}

	public Integer isExistAlias(String alias) {
		return (Integer) getOne("isExistAlias", alias);
	}

	public Integer isExistAliasForUpd(Long id, String alias) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("alias", alias);
		return (Integer) getOne("isExistAliasForUpd", map);
	}
}