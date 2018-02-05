package com.cloudstong.platform.third.bpm.service;

import java.util.Map;

public abstract interface IFlowStatus
{
  public abstract Map<String, String> getStatusByInstanceId(Long paramLong);
}