package com.cloudstong.platform.third.bpm.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.dao.ReminderStateDao;

@Service
public class ReminderStateService {

	@Resource
	private ReminderStateDao dao;

}