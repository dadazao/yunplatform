package com.cloudstong.platform.third.bpm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.third.bpm.dao.BpmAgentDao;
import com.cloudstong.platform.third.bpm.dao.SysUserAgentDao;
import com.cloudstong.platform.third.bpm.model.BpmAgent;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;

@Service
public class BpmAgentService {

	@Resource
	private BpmAgentDao dao;

	@Resource
	private SysUserAgentDao sysUserAgentDao;

	public void add(ProcessCmd cmd) {
	}

	public List<BpmAgent> getByAgentId(Long agentid) {
		return dao.getByAgentId(agentid);
	}

	public List<String> getNotInByAgentId(Long agentid) {
		return dao.getNotInByAgentId(agentid);
	}

	public void delByActDefId(String actDefId) {
		List<BpmAgent> list = dao.getByActDefId(actDefId);
		if (list.size() > 0) {
			dao.delByActDefId(actDefId);
			for (BpmAgent bpmAgent : list) {
				List agentList = dao.getByAgentId(bpmAgent.getAgentid());
				if (agentList.size() == 0)
					sysUserAgentDao.delById(bpmAgent.getAgentid());
			}
		}
	}
}