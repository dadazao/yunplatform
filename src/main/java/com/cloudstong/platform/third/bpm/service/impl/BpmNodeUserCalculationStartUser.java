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
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationStartUser implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	public String getTitle() {
		return "发起人";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();
		Long startUserId = vars.startUserId;
		List<SysUser> sysUsers = new ArrayList();
		SysUser sysUser = sysUserService.getById(startUserId);
		sysUsers.add(sysUser);
		for (SysUser sysU : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysU.getId().toString(), sysU.getFullname()));
		}
		return uIdSet;
	}
}