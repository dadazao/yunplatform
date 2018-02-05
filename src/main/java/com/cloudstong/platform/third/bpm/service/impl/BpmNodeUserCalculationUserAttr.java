package com.cloudstong.platform.third.bpm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationUserAttr implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	public String getTitle() {
		return "用户属性";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();
		try {
			List<SysUser> sysUsers = sysUserService.getByUserParam(bpmNodeUser.getCmpIds());
			for (SysUser sysUser : sysUsers)
				uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uIdSet;
	}
}