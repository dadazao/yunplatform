package com.cloudstong.platform.third.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.model.SysUserOrg;
import com.cloudstong.platform.system.service.SysUserOrgService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationOrgCharge implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysUserOrgService sysUserOrgService;

	public String getTitle() {
		return "组织负责人";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();

		List<SysUser> sysUsers = new ArrayList();
		int i;
		if (!StringUtil.isEmpty(bpmNodeUser.getCmpIds())) {
			String[] orgIds = bpmNodeUser.getCmpIds().split("[,]");
			for (i = 0; i < orgIds.length; i++) {
				List<SysUserOrg> sysUserOrgs = sysUserOrgService.getChargeByOrgId(new Long(orgIds[i]));
				for (SysUserOrg sysUserOrg : sysUserOrgs) {
					SysUser sysUser = sysUserService.getById(sysUserOrg.getId());
					sysUsers.add(sysUser);
				}
			}
		}

		for (SysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
		}
		return uIdSet;
	}
}