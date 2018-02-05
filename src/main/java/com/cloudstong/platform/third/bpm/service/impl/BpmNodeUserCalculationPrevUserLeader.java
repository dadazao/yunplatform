package com.cloudstong.platform.third.bpm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.UserUnderService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationPrevUserLeader implements IBpmNodeUserCalculation {

	@Resource
	UserUnderService userUnderService;

	public String getTitle() {
		return "上个任务执行人的领导";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();
		Long prevUserId = vars.prevExecUserId;
		List<SysUser> sysUsers = userUnderService.getMyLeaders(prevUserId);
		for (SysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
		}
		return uIdSet;
	}
}