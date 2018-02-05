package com.cloudstong.platform.third.bpm.dao;

import org.springframework.stereotype.Repository;

import com.cloudstong.platform.core.dao.impl.BaseMyBatisDaoImpl;
import com.cloudstong.platform.third.bpm.model.TaskComment;

@Repository
public class TaskCommentDao extends BaseMyBatisDaoImpl<TaskComment, Long> {
	public Class getEntityClass() {
		return TaskComment.class;
	}
}