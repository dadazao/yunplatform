package com.cloudstong.platform.third.bpm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.UserPosition;
import com.cloudstong.platform.system.service.SysUserPositionService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationPosition implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private SysUserPositionService userPositionService;

	public String getTitle() {
		return "岗位";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		boolean extractUser = vars.extractUser;
		Set userIdSet = new HashSet();
		String uids = bpmNodeUser.getCmpIds();
		if (StringUtil.isEmpty(uids)) {
			return userIdSet;
		}

		if (extractUser) {
			List<UserPosition> userList = userPositionService.getUserByPosIds(bpmNodeUser.getCmpIds());
			for (UserPosition userPos : userList) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskUser(userPos.getUserId().toString(), userPos.getFullname());
				userIdSet.add(taskExecutor);
			}
		} else {
			String[] aryId = bpmNodeUser.getCmpIds().split("[,]");
			String[] aryName = bpmNodeUser.getCmpNames().split("[,]");
			for (int i = 0; i < aryId.length; i++) {
				TaskExecutor taskExecutor = TaskExecutor.getTaskPos(aryId[i].toString(), aryName[i]);
				userIdSet.add(taskExecutor);
			}
		}
		return userIdSet;
	}
}