package com.cloudstong.platform.third.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.engine.GroovyScriptEngine;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationScript implements IBpmNodeUserCalculation {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private GroovyScriptEngine groovyScriptEngine;

	public String getTitle() {
		return "脚本";
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();

		Long startUserId = vars.startUserId;
		Long prevUserId = vars.prevExecUserId;

		String script = bpmNodeUser.getCmpNames();

		List<SysUser> sysUsers = new ArrayList();

		Map grooVars = new HashMap();
		grooVars.put("startUser", String.valueOf(startUserId));
		grooVars.put("prevUser", prevUserId);
		Object result = groovyScriptEngine.executeObject(script, grooVars);
		if (result != null) {
			Set set = (Set) result;
			String userId;
			for (Iterator it = set.iterator(); it.hasNext();) {
				userId = (String) it.next();
				SysUser sysUser = sysUserService.getById(Long.valueOf(Long.parseLong(userId)));
				sysUsers.add(sysUser);
			}

			for (SysUser sysUser : sysUsers) {
				uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
			}
		}

		return uIdSet;
	}
}