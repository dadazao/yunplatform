package com.cloudstong.platform.third.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysOrgType;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysOrgService;
import com.cloudstong.platform.system.service.SysOrgTypeService;
import com.cloudstong.platform.system.service.SysUserOrgService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationPrevTypeUserLeader implements IBpmNodeUserCalculation {

	@Resource
	private SysOrgService sysOrgService;

	@Resource
	private SysOrgTypeService sysOrgTypeService;

	@Resource
	private SysUserOrgService sysUserOrgService;

	public List<SysUser> getExecutors(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Long startUserId = vars.startUserId;
		Long prevUserId = vars.prevExecUserId;

		List users = new ArrayList();
		String expandParams = bpmNodeUser.getCmpIds();
		if (StringUtil.isEmpty(expandParams)) {
			return users;
		}
		JSONObject json = JSONObject.fromObject(expandParams);

		SysOrg startSysOrg = null;
		if (startUserId == AppContext.getCurrentUserId())
			startSysOrg = new SysOrg();//AppContext.getCurrentOrg();
		else {
			startSysOrg = sysOrgService.getDefaultOrgByUserId(startUserId);
		}

		Long currentOrgId = Long.valueOf(0L);
		if (startSysOrg != null)
			currentOrgId = startSysOrg.getId();
		else {
			return users;
		}

		if ("0".equals(json.get("orgSource")))
			users = getPrveTypeLeader(startUserId, json.get("level").toString(), json.get("stategy").toString(), currentOrgId);
		else {
			users = getPrveTypeLeader(prevUserId, json.get("level").toString(), json.get("stategy").toString(), currentOrgId);
		}
		return users;
	}

	public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars) {
		Set uIdSet = new HashSet();

		Long startUserId = vars.startUserId;
		Long prevUserId = vars.prevExecUserId;

		List<SysUser> sysUsers = new ArrayList();
		String expandParams = bpmNodeUser.getCmpIds();
		SysOrg startSysOrg;
		if (!StringUtil.isEmpty(expandParams)) {
			JSONObject json = JSONObject.fromObject(expandParams);

			startSysOrg = null;
			if (startUserId == AppContext.getCurrentUserId())
				startSysOrg = new SysOrg();//AppContext.getCurrentOrg();
			else {
				startSysOrg = sysOrgService.getDefaultOrgByUserId(startUserId);
			}

			Long currentOrgId = Long.valueOf(0L);
			if (startSysOrg != null) {
				currentOrgId = startSysOrg.getId();
				if ("0".equals(json.get("orgSource")))
					sysUsers = getPrveTypeLeader(startUserId, json.get("level").toString(), json.get("stategy").toString(), currentOrgId);
				else {
					sysUsers = getPrveTypeLeader(prevUserId, json.get("level").toString(), json.get("stategy").toString(), currentOrgId);
				}
			}

		}

		for (SysUser sysUser : sysUsers) {
			uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), sysUser.getFullname()));
		}
		return uIdSet;
	}

	private List<SysUser> getPrveTypeLeader(Long userId, String level, String stategy, Long orgId) {
		List users = new ArrayList();
		if (orgId.equals(Long.valueOf(0L)))
			return users;
		SysOrg sysOrg = sysOrgService.getById(orgId);
		SysOrgType sysOrgType = (SysOrgType) sysOrgTypeService.getById(Long.valueOf(Long.parseLong(level)));
		users = getPrveleader(sysOrg, sysOrgType, stategy);
		return users;
	}

	private List<SysUser> getPrveleader(SysOrg sysOrg, SysOrgType sysOrgType, String stategy) {
		List users = new ArrayList();
		SysOrg parentOrg = sysOrgService.getParentWithTypeLevel(sysOrg, sysOrgType);
		if (parentOrg == null) {
			return users;
		}
		if ("1".equals(stategy)) {
			SysOrgType currentSysOrgType = (SysOrgType) sysOrgTypeService.getById(parentOrg.getOrgType());
			if (sysOrgType.getId().equals(currentSysOrgType.getId()))
				users = sysUserOrgService.getLeaderUserByOrgId(parentOrg.getId(), false);
		} else if ("0".equals(stategy)) {
			users = sysUserOrgService.getLeaderUserByOrgId(parentOrg.getId(), false);
			if (BeanUtils.isEmpty(users)) {
				users = getPrveleader(parentOrg, sysOrgType, stategy);
			}
		}
		return users;
	}

	public String getTitle() {
		return "部门的上级类型部门的负责人";
	}
}