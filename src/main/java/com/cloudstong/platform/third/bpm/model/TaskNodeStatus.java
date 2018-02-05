package com.cloudstong.platform.third.bpm.model;

import java.util.ArrayList;
import java.util.List;

public class TaskNodeStatus
{
  private String actInstId;
  private String taskKey;
  private Short lastCheckStatus = TaskOpinion.STATUS_INIT;

  private List<TaskOpinion> taskOpinionList = new ArrayList();

  public void setLastCheckStatus(Short lastCheckStatus)
  {
    this.lastCheckStatus = lastCheckStatus;
  }

  public TaskNodeStatus()
  {
  }

  public String getActInstId() {
    return actInstId;
  }

  public void setActInstId(String actInstId) {
    this.actInstId = actInstId;
  }

  public String getTaskKey() {
    return taskKey;
  }

  public void setTaskKey(String taskKey) {
    this.taskKey = taskKey;
  }

  public List<TaskOpinion> getTaskOpinionList() {
    return taskOpinionList;
  }

  public void setTaskOpinionList(List<TaskOpinion> taskOpinionList) {
    this.taskOpinionList = taskOpinionList;
  }

  public TaskNodeStatus(String actInstId, String taskKey, Short lastCheckStatus, List<TaskOpinion> taskOpinionList)
  {
    this.actInstId = actInstId;
    this.taskKey = taskKey;
    this.lastCheckStatus = lastCheckStatus;
    this.taskOpinionList = taskOpinionList;
  }

  public Short getLastCheckStatus() {
    return lastCheckStatus;
  }
}