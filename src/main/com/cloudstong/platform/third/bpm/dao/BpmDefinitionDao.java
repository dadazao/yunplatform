package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmDefinition;

@Repository
public class BpmDefinitionDao extends BaseMyBatisDaoImpl<BpmDefinition, Long> {
	public Class getEntityClass() {
		return BpmDefinition.class;
	}

	public int updateSubVersions(Long parentDefId, String defKey) {
		Map params = new HashMap();
		params.put("defKey", defKey);
		params.put("parentDefId", parentDefId);

		return update("updateSubVersions", params);
	}

	public int updateDisableStatus(Long defId, Short disableStatus) {
		Map params = new HashMap();
		params.put("defId", defId);
		params.put("disableStatus", disableStatus);
		return update("updateDisableStatus", params);
	}

	public List<BpmDefinition> getByParentDefId(Long defId) {
		return getBySqlKey("getByParentDefId", defId);
	}

	public List<BpmDefinition> getByParentDefIdIsMain(Long parentDefId, Short isMain) {
		Map params = new HashMap();

		params.put("parentDefId", parentDefId);
		params.put("isMain", isMain);

		return getBySqlKey("getByParentDefIdIsMain", params);
	}

	public BpmDefinition getByActDefId(String actDefId) {
		return (BpmDefinition) getUnique("getByActDefId", actDefId);
	}

	public List<BpmDefinition> getByActDefKey(String actDefKey) {
		return getBySqlKey("getByActDefKey", actDefKey);
	}

	public BpmDefinition getByActDefKeyIsMain(String actDefKey) {
		return (BpmDefinition) getUnique("getByActDefKeyIsMain", actDefKey);
	}

	public List<BpmDefinition> getByTypeId(Long typeId) {
		return getBySqlKey("getByTypeId", typeId);
	}

	public List<BpmDefinition> getAllForAdmin(QueryCriteria queryCriteria) {
		return getBySqlKey("getAllForAdmin", queryCriteria);
	}

	public int saveParam(BpmDefinition bpmDefinition) {
		return update("saveParam", bpmDefinition);
	}

	public void delByDeployId(String actDeployId) {
		delBySqlKey("delByDeployId", actDeployId);
	}

	public BpmDefinition getByDeployId(String actDeployId) {
		return (BpmDefinition) getUnique("getByDeployId", actDeployId);
	}

	public boolean isActDefKeyExists(String key) {
		Integer rtn = (Integer) getOne("isActDefKeyExists", key);
		return rtn.intValue() > 0;
	}

	public List<BpmDefinition> getByUserId(QueryCriteria queryCriteria) {
		return getBySqlKey("getByUserId", queryCriteria);
	}

	public List<BpmDefinition> getByUserIdFilter(QueryCriteria queryCriteria) {
		return getBySqlKey("getByUserIdFilter", queryCriteria);
	}

	public List<BpmDefinition> getAllPublished(String subject) {
		Map filter = new HashMap();
		filter.put("subject", subject);
		return getBySqlKey("getAllPublished", filter);
	}

	public List<BpmDefinition> getPublishedByTypeId(String typeId) {
		return getBySqlKey("getPublishedByTypeId", typeId);
	}

	public BpmDefinition getMainByDefKey(String defKey) {
		return (BpmDefinition) getUnique("getMainByDefKey", defKey);
	}

	public List<BpmDefinition> getByUserId(Long userId, Map<String, Object> params, QueryCriteria queryCriteria) {
		return getBySqlKey("getByUserIdFilter", params, queryCriteria);
	}

	public void delProcDefByActDeployId(Long actDeployId) {
		delBySqlKey("delBytearRayByActDeployId", actDeployId);
		delBySqlKey("delDeployMentByActDeployId", actDeployId);
		delBySqlKey("delProcDefByActDeployId", actDeployId);
	}
}