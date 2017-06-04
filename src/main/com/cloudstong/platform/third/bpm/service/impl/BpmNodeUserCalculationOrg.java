package com.cloudstong.platform.third.bpm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysUserOrg;
import com.cloudstong.platform.system.service.SysUserOrgService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationOrg implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysUserOrgService sysUserOrgService;

	public String getTitle() {
		return "组织";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set userIdSet = new HashSet();
		boolean extractUser = vars.extractUser;
		String uids = bpmNodeUser.getCmpIds();
		if (StringUtil.isEmpty(uids)) {
			return userIdSet;
		}
		if (extractUser) {
			List<SysUserOrg> userOrgList = sysUserOrgService.getUserByOrgIds(bpmNodeUser.getCmpIds());
			for (SysUserOrg sysUserOrg : userOrgList) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskUser(sysUserOrg.getId().toString(), sysUserOrg.getSysUser().getUsername());
				userIdSet.add(taskExecutor);
			}
		} else {
			String[] orgIds = bpmNodeUser.getCmpIds().split("[,]");
			String[] orgNames = bpmNodeUser.getCmpNames().split("[,]");
			for (int i = 0; i < orgIds.length; i++) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskOrg(orgIds[i].toString(), orgNames[i]);
				userIdSet.add(taskExecutor);
			}
		}

		return userIdSet;
	}
}