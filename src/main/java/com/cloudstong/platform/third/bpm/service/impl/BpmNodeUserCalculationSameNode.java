package com.cloudstong.platform.third.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.model.TaskOpinion;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;
import com.cloudstong.platform.third.bpm.service.TaskOpinionService;

public class BpmNodeUserCalculationSameNode implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private TaskOpinionService taskOpinionService;

	public String getTitle() {
		return "与其他节点相同执行人";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();

		String actInstId = vars.actInstId;
		List<SysUser> sysUsers = new ArrayList();
		String nodeId = bpmNodeUser.getCmpIds();
		TaskOpinion taskOpinion = taskOpinionService.getLatestTaskOpinion(actInstId, nodeId);
		if (taskOpinion != null) {
			SysUser user = sysUserService.getById(taskOpinion.getExeUserId());
			sysUsers.add(user);
		}

		for (SysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
		}
		return uIdSet;
	}
}