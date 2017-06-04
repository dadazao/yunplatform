package com.cloudstong.platform.third.bpm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeWsParams;

@Repository
public class BpmNodeWsParamsDao extends BaseMyBatisDaoImpl<BpmNodeWsParams, Long> {
	public Class<?> getEntityClass() {
		return BpmNodeWsParams.class;
	}

	public List<BpmNodeWsParams> getByWebserviceId(Long webserviceId) {
		Map params = new HashMap();
		params.put("webserviceId", webserviceId);
		return getBySqlKey("getByWebserviceId", params);
	}

	public void delByWebserviceId(Long webserviceId) {
		getBySqlKey("delByWebserviceId", webserviceId);
	}
}