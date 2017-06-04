package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCallActivity")
public class CallActivity extends Activity
{

  @XmlAttribute
  protected String calledElement;

  public String getCalledElement()
  {
    return calledElement;
  }

  public void setCalledElement(String value)
  {
    calledElement = value;
  }
}