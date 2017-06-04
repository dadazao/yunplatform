package com.cloudstong.platform.third.bpm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationUpLow
  implements IBpmNodeUserCalculation
{

  @Resource
  private SysUserService sysUserService;

  public String getTitle()
  {
    return "上下级";
  }

  public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars)
  {
    Set uIdSet = new HashSet();
    Long startUserId = vars.startUserId;
    List<SysUser> sysUsers = sysUserService.getByUserIdAndUplow(
      startUserId.longValue(), bpmNodeUser.getNodeUserId().longValue());
    for (SysUser sysUser : sysUsers) {
      uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId().toString(), 
        sysUser.getFullname()));
    }
    return uIdSet;
  }
}