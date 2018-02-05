package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tComplexBehaviorDefinition", propOrder={"condition", "event"})
public class ComplexBehaviorDefinition extends BaseElement
{

  @XmlElement(required=true)
  protected FormalExpression condition;
  protected ImplicitThrowEvent event;

  public FormalExpression getCondition()
  {
    return condition;
  }

  public void setCondition(FormalExpression value)
  {
    condition = value;
  }

  public ImplicitThrowEvent getEvent()
  {
    return event;
  }

  public void setEvent(ImplicitThrowEvent value)
  {
    event = value;
  }
}