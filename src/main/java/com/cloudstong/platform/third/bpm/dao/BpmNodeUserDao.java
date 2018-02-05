package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;

@Repository
public class BpmNodeUserDao extends BaseMyBatisDaoImpl<BpmNodeUser, Long> {
	public Class getEntityClass() {
		return BpmNodeUser.class;
	}

	public List<BpmNodeUser> getBySetId(Long setId) {
		return getBySqlKey("getBySetId", setId);
	}

	public List<BpmNodeUser> getByActDefId(String actDefId) {
		return getBySqlKey("getByActDefId", actDefId);
	}

	public void delByActDefId(String actDefId) {
		delBySqlKey("delByActDefId", actDefId);
	}

	public List<BpmNodeUser> getBySetIdAndConditionId(Long setId, Long conditionId) {
		Map map = new HashMap();
		map.put("setId", setId);
		map.put("conditionId", conditionId);
		return getBySqlKey("getBySetIdAndConditionId", map);
	}

	public void delByConditionId(Long conditionId) {
		getBySqlKey("delByConditionId", conditionId);
	}

	public List<BpmNodeUser> selectNull() {
		return getBySqlKey("selectNull");
	}
}