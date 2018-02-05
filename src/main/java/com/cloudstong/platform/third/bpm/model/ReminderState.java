package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ReminderState extends EntityBase
{
  protected Long id;
  protected String taskId;
  protected Date reminderTime;
  protected String actInstanceId = "";

  protected Long userId = Long.valueOf(0L);

  protected String actDefId = "";

  protected int remindType = 1;

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getId()
  {
    return id;
  }

  public void setTaskId(String taskId)
  {
    this.taskId = taskId;
  }

  public String getTaskId()
  {
    return taskId;
  }

  public void setReminderTime(Date reminderTime)
  {
    this.reminderTime = reminderTime;
  }

  public Date getReminderTime()
  {
    return reminderTime;
  }

  public String getActInstanceId() {
    return actInstanceId;
  }
  public void setActInstanceId(String actInstanceId) {
    this.actInstanceId = actInstanceId;
  }
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getActDefId() {
    return actDefId;
  }
  public void setActDefId(String actDefId) {
    this.actDefId = actDefId;
  }

  public int getRemindType() {
    return remindType;
  }
  public void setRemindType(int remindType) {
    this.remindType = remindType;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof ReminderState))
    {
      return false;
    }
    ReminderState rhs = (ReminderState)object;
    return new EqualsBuilder()
      .append(id, rhs.id)
      .append(taskId, rhs.taskId)
      .append(reminderTime, rhs.reminderTime)
      .isEquals();
  }

  public int hashCode()
  {
    return new HashCodeBuilder(-82280557, -700257973)
      .append(id)
      .append(taskId)
      .append(reminderTime)
      .toHashCode();
  }

  public String toString()
  {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("taskId", taskId)
      .append("reminderTime", reminderTime)
      .toString();
  }
}