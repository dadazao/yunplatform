package com.cloudstong.platform.third.bpm.service;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public abstract interface ISignComplete
{
  public static final String SIGN_RESULT_PASS = "pass";
  public static final String SIGN_RESULT_REFUSE = "refuse";

  public abstract boolean isComplete(ActivityExecution paramActivityExecution);
}