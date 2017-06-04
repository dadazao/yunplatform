package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tConditionalEventDefinition", propOrder={"condition"})
public class ConditionalEventDefinition extends EventDefinition
{

  @XmlElement(required=true)
  protected Expression condition;

  public Expression getCondition()
  {
    return condition;
  }

  public void setCondition(Expression value)
  {
    condition = value;
  }
}