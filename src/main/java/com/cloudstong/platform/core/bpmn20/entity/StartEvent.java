package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tStartEvent")
public class StartEvent extends CatchEvent
{

  @XmlAttribute
  protected Boolean isInterrupting;

  public boolean isIsInterrupting()
  {
    if (isInterrupting == null) {
      return true;
    }
    return isInterrupting.booleanValue();
  }

  public void setIsInterrupting(Boolean value)
  {
    isInterrupting = value;
  }
}