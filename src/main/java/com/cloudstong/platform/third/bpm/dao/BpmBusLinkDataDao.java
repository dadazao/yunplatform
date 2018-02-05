package com.cloudstong.platform.third.bpm.dao;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmBusLinkData;

@Repository
public class BpmBusLinkDataDao extends BaseMyBatisDaoImpl<BpmBusLinkData, Long> {
	public Class getEntityClass() {
		return BpmBusLinkData.class;
	}

	public void delByActDefIdLinkData(String actDefId) {
		delBySqlKey("delByActDefIdLinkData", actDefId);
	}
}