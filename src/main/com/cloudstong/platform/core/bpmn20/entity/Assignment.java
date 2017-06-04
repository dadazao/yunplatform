package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tAssignment", propOrder={"from", "to"})
public class Assignment extends BaseElement
{

  @XmlElement(required=true)
  protected Expression from;

  @XmlElement(required=true)
  protected Expression to;

  public Expression getFrom()
  {
    return from;
  }

  public void setFrom(Expression value)
  {
    from = value;
  }

  public Expression getTo()
  {
    return to;
  }

  public void setTo(Expression value)
  {
    to = value;
  }
}