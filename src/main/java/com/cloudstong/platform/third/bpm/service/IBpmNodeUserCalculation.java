package com.cloudstong.platform.third.bpm.service;

import java.util.Set;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;

public abstract interface IBpmNodeUserCalculation {
	public abstract Set<TaskExecutor> getExecutor(BpmNodeUser paramBpmNodeUser, CalcVars paramCalcVars);

	public abstract String getTitle();

	public static class CalcVars {
		public Long startUserId;
		public Long prevExecUserId;
		public String actInstId;
		public boolean extractUser;

		public CalcVars(Long startUserId, Long preExecUserId, String actInstId, boolean extractUser) {
			this.startUserId = startUserId;
			prevExecUserId = preExecUserId;
			this.actInstId = actInstId;
			this.extractUser = extractUser;
		}
	}
}