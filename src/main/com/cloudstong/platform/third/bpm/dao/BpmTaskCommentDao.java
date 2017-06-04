package com.cloudstong.platform.third.bpm.dao;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.BpmTaskComment;

@Repository
public class BpmTaskCommentDao extends BaseMyBatisDaoImpl<BpmTaskComment, Long> {
	public Class getEntityClass() {
		return BpmTaskComment.class;
	}

	public void delByactDefId(String actDefId) {
		delBySqlKey("delByactDefId", actDefId);
	}
}