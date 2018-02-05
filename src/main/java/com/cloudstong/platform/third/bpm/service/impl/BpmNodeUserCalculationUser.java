package com.cloudstong.platform.third.bpm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cloudstong.platform.core.model.TaskExecutor;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.model.BpmNodeUser;
import com.cloudstong.platform.third.bpm.service.IBpmNodeUserCalculation;

public class BpmNodeUserCalculationUser
  implements IBpmNodeUserCalculation
{

  @Resource
  private SysUserService sysUserService;

  public String getTitle()
  {
    return "用户";
  }

  public Set<TaskExecutor> getExecutor(BpmNodeUser bpmNodeUser, IBpmNodeUserCalculation.CalcVars vars)
  {
    Set uIdSet = new HashSet();
    List<SysUser> sysUsers = new ArrayList();
    String uids = bpmNodeUser.getCmpIds();
    if (!StringUtil.isEmpty(uids)) {
      String[] aryUid = uids.split("[,]");
      Set uidSet = new HashSet(Arrays.asList(aryUid));
      sysUsers = sysUserService.getByIdSet(uidSet);
      for (SysUser sysUser : sysUsers) {
        uIdSet.add(TaskExecutor.getTaskUser(sysUser.getId()
          .toString(), sysUser.getFullname()));
      }
    }

    return uIdSet;
  }
}