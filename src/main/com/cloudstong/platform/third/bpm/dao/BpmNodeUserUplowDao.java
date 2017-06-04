package com.cloudstong.platform.third.bpm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmNodeUserUplow;

@Repository
public class BpmNodeUserUplowDao extends BaseMyBatisDaoImpl<BpmNodeUserUplow, Long> {
	public Class getEntityClass() {
		return BpmNodeUserUplow.class;
	}

	public int delByNodeUserId(long nodeUserId) {
		return delBySqlKey("delByNodeUserId", Long.valueOf(nodeUserId));
	}

	public List<BpmNodeUserUplow> getByNodeUserId(long userId) {
		return getBySqlKey("getByNodeUserId", Long.valueOf(userId));
	}
}