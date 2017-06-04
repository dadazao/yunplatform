package com.cloudstong.platform.third.bpm.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.dao.SysOrgDao;
import com.cloudstong.platform.system.dao.SysPositionDao;
import com.cloudstong.platform.system.dao.SysRoleDao;
import com.cloudstong.platform.system.dao.SysUserDao;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysPosition;
import com.cloudstong.platform.system.model.SysRole;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.third.bpm.dao.TaskUserDao;
import com.cloudstong.platform.third.bpm.model.TaskUser;

@Service
public class TaskUserService {

	@Resource
	private TaskUserDao taskUserDao;

	@Resource
	private SysUserDao sysUserDao;

	@Resource
	private SysOrgDao sysOrgDao;

	@Resource
	private SysPositionDao sysPositionDao;

	@Resource
	private SysRoleDao sysRoleDao;

	public List<TaskUser> getByTaskId(String taskId) {
		return taskUserDao.getByTaskId(taskId);
	}

	public Set<TaskExecutor> getCandidateExecutors(String taskId) {
		Set taskUserSet = new HashSet();
		List<TaskUser> taskUsers = getByTaskId(taskId);
		for (TaskUser taskUser : taskUsers) {
			if (taskUser.getUserId() != null) {
				SysUser sysUser = (SysUser) sysUserDao.getById(new Long(taskUser.getUserId()));
				taskUserSet.add(TaskExecutor.getTaskUser(taskUser.getUserId(), sysUser.getFullname()));
			} else if (taskUser.getGroupId() != null) {
				String tmpId = taskUser.getGroupId();
				if ("org".equals(taskUser.getType())) {
					SysOrg sysOrg = (SysOrg) sysOrgDao.getById(new Long(tmpId));
					taskUserSet.add(TaskExecutor.getTaskOrg(tmpId, sysOrg.getOrgName()));
				} else if ("pos".equals(taskUser.getType())) {
					SysPosition position = (SysPosition) sysPositionDao.getById(new Long(tmpId));
					taskUserSet.add(TaskExecutor.getTaskPos(tmpId, position.getPositionName()));
				} else if ("role".equals(taskUser.getType())) {
					SysRole sysRole = (SysRole) sysRoleDao.getById(new Long(tmpId));
					taskUserSet.add(TaskExecutor.getTaskPos(tmpId, sysRole.getRoleName()));
				}
			}
		}
		return taskUserSet;
	}

	public Set<SysUser> getCandidateUsers(String taskId) {
		Set taskUserSet = new HashSet();
		List<TaskUser> taskUsers = getByTaskId(taskId);
		for (TaskUser taskUser : taskUsers) {
			if (taskUser.getUserId() != null) {
				SysUser sysUser = (SysUser) sysUserDao.getById(Long.valueOf(Long.parseLong(taskUser.getUserId())));
				taskUserSet.add(sysUser);
			} else if (taskUser.getGroupId() != null) {
				Long tmpId = Long.valueOf(Long.parseLong(taskUser.getGroupId()));
				if ("org".equals(taskUser.getType())) {
					List userList = sysUserDao.getByOrgId(tmpId);
					taskUserSet.addAll(userList);
				} else if ("pos".equals(taskUser.getType())) {
					List userList = sysUserDao.getByPosId(tmpId);
					taskUserSet.addAll(userList);
				} else if ("role".equals(taskUser.getType())) {
					List userList = sysUserDao.getByRoleId(tmpId);
					taskUserSet.addAll(userList);
				}
			}
		}
		return taskUserSet;
	}
}