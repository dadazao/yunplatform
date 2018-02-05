package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.third.bpm.dao.BpmNodeWsParamsDao;
import com.cloudstong.platform.third.bpm.model.BpmNodeWsParams;

@Service
public class BpmNodeWsParamsService {

	@Resource
	private BpmNodeWsParamsDao dao;

	public BpmNodeWsParams save(BpmNodeWsParams bpmNodeWsParams) {
		if (BeanUtils.isEmpty(bpmNodeWsParams)) {
			return null;
		}
		if (BeanUtils.isEmpty(bpmNodeWsParams.getId())) {
			bpmNodeWsParams.setId(Long.valueOf(UniqueIdUtil.genId()));
			dao.save(bpmNodeWsParams);
		} else {
			dao.update(bpmNodeWsParams);
		}
		return bpmNodeWsParams;
	}

	public List<BpmNodeWsParams> getByWebserviceId(Long webserviceId) {
		return dao.getByWebserviceId(webserviceId);
	}

	public void delByWebserviceId(Long webserviceId) {
		dao.delByWebserviceId(webserviceId);
	}
}