package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.model.EntityBase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BpmFormRun extends EntityBase
{
  public static final short SETTYPE_NODE = 0;
  public static final short SETTYPE_START = 1;
  public static final short SETTYPE_GOBAL = 2;
  protected Long id;
  protected Long formdefId;
  protected Long formdefKey;
  protected String actInstanceId;
  protected String actDefId;
  protected String actNodeId;
  protected Long runId;
  protected Short setType = Short.valueOf((short)0);

  protected Short formType = Short.valueOf((short)-1);
  protected String formUrl;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getFormdefId() {
    return formdefId;
  }

  public void setFormdefId(Long formdefId) {
    this.formdefId = formdefId;
  }

  public Long getFormdefKey() {
    return formdefKey;
  }

  public void setFormdefKey(Long formdefKey) {
    this.formdefKey = formdefKey;
  }

  public String getActInstanceId() {
    return actInstanceId;
  }

  public void setActInstanceId(String actInstanceId) {
    this.actInstanceId = actInstanceId;
  }

  public String getActDefId() {
    return actDefId;
  }

  public void setActDefId(String actDefId) {
    this.actDefId = actDefId;
  }

  public String getActNodeId() {
    return actNodeId;
  }

  public void setActNodeId(String actNodeId) {
    this.actNodeId = actNodeId;
  }

  public Long getRunId() {
    return runId;
  }

  public void setRunId(Long runId) {
    this.runId = runId;
  }

  public Short getSetType() {
    return setType;
  }

  public void setSetType(Short setType) {
    this.setType = setType;
  }

  public Short getFormType() {
    return formType;
  }

  public void setFormType(Short formType) {
    this.formType = formType;
  }

  public String getFormUrl() {
    return formUrl;
  }

  public void setFormUrl(String formUrl) {
    this.formUrl = formUrl;
  }

  public boolean equals(Object object)
  {
    if (!(object instanceof BpmFormRun))
    {
      return false;
    }
    BpmFormRun rhs = (BpmFormRun)object;
    return new EqualsBuilder()
      .append(id, rhs.id)
      .isEquals();
  }

  public int hashCode()
  {
    return new HashCodeBuilder(-82280557, -700257973)
      .append(id)
      .toHashCode();
  }

  public String toString()
  {
    return new ToStringBuilder(this)
      .append("id", id)
      .toString();
  }
}