package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCompensateEventDefinition")
public class CompensateEventDefinition extends EventDefinition
{

  @XmlAttribute
  protected Boolean waitForCompletion;

  @XmlAttribute
  protected QName activityRef;

  public Boolean isWaitForCompletion()
  {
    return waitForCompletion;
  }

  public void setWaitForCompletion(Boolean value)
  {
    waitForCompletion = value;
  }

  public QName getActivityRef()
  {
    return activityRef;
  }

  public void setActivityRef(QName value)
  {
    activityRef = value;
  }
}