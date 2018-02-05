package com.cloudstong.platform.third.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserOrg;
import com.cloudstong.platform.system.service.SysUserOrgService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationSameDepartment implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysUserOrgService sysUserOrgService;

	public String getTitle() {
		return "与发起人相同部门";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();

		Long startUserId = vars.startUserId;
		List<SysUser> sysUsers = new ArrayList();
		List orgIds = sysUserOrgService.getOrgByUserId(startUserId);

		for (Iterator localIterator1 = orgIds.iterator(); localIterator1.hasNext();) {
			SysUserOrg sysUserOrg = (SysUserOrg) localIterator1.next();
			List sysUserOrgs = sysUserOrgService.getByOrgId(sysUserOrg.getOrgId());
			for (Iterator localIterator2 = sysUserOrgs.iterator(); localIterator2.hasNext();) {
				SysUserOrg userOrg = (SysUserOrg) localIterator2.next();
				SysUser user = sysUserService.getById(userOrg.getId());
				sysUsers.add(user);
			}
		}

		for (SysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
		}
		return uIdSet;
	}
}