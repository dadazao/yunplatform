package com.cloudstong.platform.third.bpm.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.dao.BpmTaskCommentDao;
import com.cloudstong.platform.third.bpm.model.BpmTaskComment;

@Service
public class BpmTaskCommentService {

	@Resource
	private BpmTaskCommentDao dao;

}