package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmDefRights;

@Repository
public class BpmDefRightsDao extends BaseMyBatisDaoImpl<BpmDefRights, Long> {
	public Class getEntityClass() {
		return BpmDefRights.class;
	}

	public List<BpmDefRights> getDefRight(Long defId, Short rightType) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("rightType", rightType);
		params.put("searchType", Short.valueOf((short) 0));
		return getBySqlKey("getDefRight", params);
	}

	public List<BpmDefRights> getTypeRight(Long typeId, Short rightType) {
		Map params = new HashMap();
		params.put("typeId", typeId);
		params.put("rightType", rightType);
		params.put("searchType", Short.valueOf((short) 1));
		return getBySqlKey("getTypeRight", params);
	}

	public void delByTypeId(Long typeId) {
		getBySqlKey("delByTypeId", typeId);
	}

	public void delByDefKey(String defKey) {
		delBySqlKey("delByDefKey", defKey);
	}

	public List<BpmDefRights> getByDefKey(String defKey) {
		return getBySqlKey("getByDefKey", defKey);
	}

	public List<BpmDefRights> getByTypeId(Long typeId) {
		return getBySqlKey("getByTypeId", typeId);
	}
}