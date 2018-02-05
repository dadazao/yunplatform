package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.GlobalType;

@Repository
public class GlobalTypeDao extends BaseMyBatisDaoImpl<GlobalType,Long> {
	public List<GlobalType> getByNodePath(String nodePath) {
		Map params = new HashMap();
		params.put("nodePath", nodePath + "%");
		return getBySqlKey("getByNodePath", params);
	}

	public List<GlobalType> getByParentId(long parentId) {
		return getBySqlKey("getByParentId", Long.valueOf(parentId));
	}

	public boolean isNodeKeyExists(String catKey, String nodeKey) {
		Map params = new HashMap();
		params.put("catkey", catKey);
		params.put("nodeKey", nodeKey);
		int rtn = ((Integer) getOne("isNodeKeyExists", params)).intValue();
		return rtn > 0;
	}

	public boolean isNodeKeyExistsForUpdate(Long typeId, String catKey, String nodeKey) {
		Map params = new HashMap();
		params.put("typeId", typeId);
		params.put("catkey", catKey);
		params.put("nodeKey", nodeKey);
		int rtn = ((Integer) getOne("isNodeKeyExistsForUpdate", params)).intValue();
		return rtn > 0;
	}

	public void updSn(Long typeId, Long sn) {
		GlobalType globalType = new GlobalType();
		globalType.setTypeId(typeId);
		globalType.setSn(sn);
		update("updSn", globalType);
	}

	public List<GlobalType> getByCatKey(String catKey) {
		return getBySqlKey("getByCatKey", catKey);
	}

	public GlobalType getByDictNodeKey(String nodeKey) {
		GlobalType globalType = (GlobalType) getUnique("getByDictNodeKey", nodeKey);
		return globalType;
	}

	public List<GlobalType> getPersonType(String catKey, Long userId) {
		Map params = new Hashtable();
		params.put("catkey", catKey);
		params.put("userId", userId);
		List list = getBySqlKey("getPersonType", params);
		return list;
	}

	public List<GlobalType> getByBpmRights(String catKey, Long userId, String roleIds, String orgIds) {
		Map params = new HashMap();
		params.put("ownerId", userId);
		params.put("catKey", catKey);
		if (StringUtils.isNotEmpty(roleIds)) {
			params.put("roleIds", roleIds);
		}
		if (StringUtils.isNotEmpty(orgIds)) {
			params.put("orgIds", orgIds);
		}
		return getBySqlKey("getByBpmRights", params);
	}

	public List<GlobalType> getByFormRights(String catKey, Long userId, String roleIds, String orgIds) {
		Map params = new HashMap();
		params.put("ownerId", userId);
		params.put("catKey", catKey);
		if (StringUtils.isNotEmpty(roleIds)) {
			params.put("roleIds", roleIds);
		}
		if (StringUtils.isNotEmpty(orgIds)) {
			params.put("orgIds", orgIds);
		}
		return getBySqlKey("getByFormRights", params);
	}
}