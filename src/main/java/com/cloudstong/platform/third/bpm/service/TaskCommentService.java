package com.cloudstong.platform.third.bpm.service;

import java.util.Date;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.struts2.views.util.ContextUtil;
import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.TaskCommentDao;
import com.cloudstong.platform.third.bpm.model.TaskComment;

@Service
public class TaskCommentService {

	@Resource
	private TaskCommentDao dao;

	public void addTaskComment(TaskComment taskComment, TaskEntity taskEntity) throws Exception {
		taskComment.setCommentId(Long.valueOf(UniqueIdUtil.genId()));
		taskComment.setAuthorId(AppContext.getCurrentUserId());
		taskComment.setAuthor(AppContext.getCurrentUser().getFullname());
		taskComment.setNodeName(taskEntity.getName());
		taskComment.setCommentTime(new Date());
		dao.save(taskComment);
	}
}