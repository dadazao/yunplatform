package com.cloudstong.platform.third.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.engine.IScript;
import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.Dom4jUtil;
import com.cloudstong.platform.core.util.TimeUtil;
import com.cloudstong.platform.system.dao.SysOrgDao;
import com.cloudstong.platform.system.dao.SysPositionDao;
import com.cloudstong.platform.system.dao.SysRoleDao;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysOrgType;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysRole;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserOrgService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.system.service.UserUnderService;
import com.cloudstong.platform.third.bpm.model.ProcessCmd;
import com.cloudstong.platform.third.bpm.model.ProcessRun;
import com.cloudstong.platform.third.bpm.service.TaskOpinionService;

public class ScriptImpl implements IScript {
	private static final Log logger = LogFactory.getLog(Dom4jUtil.class);

	@Resource
	TaskOpinionService taskOpinionService;

	@Resource
	private SysRoleDao sysRoleDao;

	@Resource
	private SysUserOrgService sysUserOrgService;

	@Resource
	private SysOrgDao sysOrgDao;

	@Resource
	private SysPositionDao sysPositionDao;

	@Resource
	private UserUnderService userUnderService;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SysUserService sysUserService;

//	@Resource
//	private SysOrgTypeDao sysOrgTypeDao;
//
//	@Resource
//	private SysUserParamDao sysUserParamDao;
//
//	@Resource
//	private ProcessService processService;

	public long getCurrentUserId() {
		return AppContext.getCurrentUserId().longValue();
	}

	public String getCurrentName() {
		return AppContext.getCurrentUser().getFullname();
	}

	public SysUser getCurrentUser() {
		return AppContext.getCurrentUser();
	}

	public String getCurrentDate() {
		return TimeUtil.getCurrentDate();
	}

	public SysOrg getCurrentOrg() {
		SysOrg sysOrg = AppContext.getCurrentOrg();
		return sysOrg;
	}

	public Long getCurrentOrgId() throws Exception {
		SysOrg sysOrg = AppContext.getCurrentOrg();
		if (sysOrg == null)
			return null;
		return sysOrg.getId();
	}

	public String getCurrentOrgName() {
		SysOrg sysOrg = AppContext.getCurrentOrg();
		if (sysOrg == null)
			return null;
		return sysOrg.getOrgName();
	}

	public Long getUserOrgId(Long userId) {
		SysOrg sysOrg = sysOrgDao.getPrimaryOrgByUserId(userId);
		if (sysOrg == null)
			return null;
		return sysOrg.getId();
	}

	public String getUserOrgName(Long userId) {
		SysOrg sysOrg = sysOrgDao.getPrimaryOrgByUserId(userId);
		if (sysOrg == null)
			return null;
		return sysOrg.getOrgName();
	}

	public SysOrgType getCurrentOrgType() {
		SysOrg sysOrg = AppContext.getCurrentOrg();
		if (sysOrg == null)
			return null;
		Long orgType = sysOrg.getOrgType();
		if (orgType == null)
			return null;
//		SysOrgType sysOrgType = (SysOrgType) sysOrgTypeDao.getById(orgType);
		return new SysOrgType();
	}

	public String getCurrentOrgTypeName() {
		SysOrg sysOrg = AppContext.getCurrentOrg();
		if (sysOrg == null)
			return "";
		Long orgType = sysOrg.getOrgType();
		if (orgType == null)
			return "";
//		SysOrgType sysOrgType = (SysOrgType) sysOrgTypeDao.getById(orgType);
		return "";
	}

	public boolean hasRole(String alias) {
		long userId = AppContext.getCurrentUserId().longValue();
		List<SysRole> roleList = sysRoleDao.getByUserId(Long.valueOf(userId));
		for (SysRole role : roleList) {
			if (role.getAlias().equals(alias)) {
				return true;
			}
		}
		return false;
	}

	public List<SysRole> getCurrentUserRoles() {
		long userId = AppContext.getCurrentUserId().longValue();
		List list = sysRoleDao.getByUserId(Long.valueOf(userId));
		return list;
	}

	public List<SysRole> getUserRoles(String strUserId) {
		Long userId = Long.valueOf(Long.parseLong(strUserId));
		List list = sysRoleDao.getByUserId(userId);
		return list;
	}

	public boolean isUserInRole(String userId, String role) {
		Long lUserId = Long.valueOf(Long.parseLong(userId));
		List<SysRole> list = sysRoleDao.getByUserId(lUserId);
		if (BeanUtils.isEmpty(list))
			return false;
		for (SysRole sysRole : list) {
			if (sysRole.getAlias().equals(role)) {
				return true;
			}
		}
		return false;
	}

	public boolean isUserInOrg(String userId, String org) {
		Long lUserId = Long.valueOf(Long.parseLong(userId));
		List<SysOrg> list = sysOrgDao.getOrgsByUserId(lUserId);
		if (BeanUtils.isEmpty(list))
			return false;
		for (SysOrg sysOrg : list) {
			if (sysOrg.getOrgName().equals(org)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCurUserInOrg(String org) {
		Long userId = AppContext.getCurrentUserId();
		return isUserInOrg(userId.toString(), org);
	}

	public String getUserPos(Long userId) {
		String posName = "";
		SysPosition position = sysPositionDao.getPosByUserId(userId);
		if (!BeanUtils.isEmpty(position)) {
			posName = position.getPositionName();
		}
		return posName;
	}

	public Object getParaValue(String paramKey) {
		Long currentUserId = AppContext.getCurrentUserId();

		return getParaValueByUser(currentUserId, paramKey);
	}

	public Object getParaValueByUser(Long userId, String paramKey) {
//		SysUserParam sysUserParam = sysUserParamDao.getByParaUserId(userId.longValue(), paramKey);
//		if (sysUserParam == null) {
//			return null;
//		}
//		String dataType = sysUserParam.getDataType();
//		if ("String".equals(dataType))
//			return sysUserParam.getParamValue();
//		if ("Integer".equals(dataType)) {
//			return sysUserParam.getParamIntValue();
//		}
		return null;
	}

	public String getCurUserPos() {
		long userId = AppContext.getCurrentUserId().longValue();
		String posName = getUserPos(Long.valueOf(userId));
		return posName;
	}

	public String getCurDirectLeaderPos() {
		String userId = AppContext.getCurrentUserId().toString();
		String posName = getDirectLeaderPosByUserId(userId);
		return posName;
	}

	public String getDirectLeaderPosByUserId(String userId) {
		String posName = "";
		posName = sysUserOrgService.getLeaderPosByUserId(Long.valueOf(Long.parseLong(userId)));
		return posName;
	}

	public Set<String> getDirectLeaderByUserId(String userId) {
		Set userSet = new HashSet();
		List<TaskExecutor> userList = sysUserOrgService.getLeaderByUserId(Long.valueOf(Long.parseLong(userId)));

		if (BeanUtils.isEmpty(userList))
			return userSet;
		for (TaskExecutor user : userList) {
			userSet.add(user.getExecuteId());
		}

		return userSet;
	}

	public boolean isDepartmentLeader(String userId, String orgId) {
		List<TaskExecutor> leaders = sysUserOrgService.getLeaderByOrgId(Long.valueOf(Long.parseLong(orgId)));
		for (TaskExecutor leader : leaders) {
			if (userId.equals(leader.getExecuteId())) {
				return true;
			}
		}
		return false;
	}

	public Set<String> getMyLeader(Long userId) {
		Set set = userUnderService.getMyLeader(userId);
		Set userSet = new HashSet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			userSet.add(((TaskExecutor) it.next()).getExecuteId());
		}
		return userSet;
	}

	public Set<String> getMyUnderUserId(Long userId) {
		return userUnderService.getMyUnderUserId(userId);
	}

	public void setAssigneeByAccount(TaskEntity task, String userAccout) {
		String[] aryAccount = userAccout.split(",");
		List userIds = new ArrayList();
		for (String str : aryAccount) {
			SysUser sysUser = sysUserService.getByAccount(str);
			userIds.add(sysUser.getId().toString());
		}
		if (userIds.size() == 0) {
			return;
		}
		if (userIds.size() == 1) {
			task.setAssignee((String) userIds.get(0));
		} else {
			task.addCandidateUsers(userIds);
		}
	}

	public ProcessRun startFlow(String flowKey, String businnessKey, Map<String, Object> vars) throws Exception {
		ProcessCmd processCmd = new ProcessCmd();
		processCmd.setFlowKey(flowKey);
		processCmd.setBusinessKey(businnessKey);
		if (BeanUtils.isNotEmpty(vars)) {
			processCmd.setVariables(vars);
		}
		return new ProcessRun();//processService.start(processCmd);
	}
}