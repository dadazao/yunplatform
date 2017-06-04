package com.cloudstong.platform.third.bpm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.UserRole;
import com.cloudstong.platform.system.service.SysUserRoleService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationRole implements IBpmNodeUserCalculation {

	@Resource
	private SysUserRoleService userRoleService;

	@Resource
	private SysUserService sysUserService;

	public String getTitle() {
		return "角色";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		boolean extractUser = vars.extractUser;
		Set userIdSet = new HashSet();
		String uids = bpmNodeUser.getCmpIds();
		if (StringUtil.isEmpty(uids)) {
			return userIdSet;
		}

		if (extractUser) {
			List<UserRole> userList = userRoleService.getUserByRoleIds(bpmNodeUser.getCmpIds());
			for (UserRole user : userList) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskUser(user.getId().toString(), user.getFullname());
				userIdSet.add(taskExecutor);
			}
		} else {
			String[] roleIds = bpmNodeUser.getCmpIds().split("[,]");
			String[] roleNames = bpmNodeUser.getCmpNames().split("[,]");
			for (int i = 0; i < roleIds.length; i++) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskRole(roleIds[i], roleNames[i]);
				userIdSet.add(taskExecutor);
			}
		}

		return userIdSet;
	}
}