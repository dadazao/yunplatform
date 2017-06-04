package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeUserUplowDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeUserUplow;

@Service
public class BpmNodeUserUplowService {

	@Resource
	private BpmNodeUserUplowDao dao;

	public void upd(long nodeUserId, List<BpmNodeUserUplow> uplowList) throws Exception {
		dao.delByNodeUserId(nodeUserId);

		if (BeanUtils.isEmpty(uplowList))
			return;

		for (BpmNodeUserUplow e : uplowList) {
			e.setId(Long.valueOf(UniqueIdUtil.genId()));
			e.setNodeUserId(Long.valueOf(nodeUserId));
			dao.save(e);
		}
	}

	public List<BpmNodeUserUplow> getByNodeUserId(long userId) {
		return dao.getByNodeUserId(userId);
	}
}