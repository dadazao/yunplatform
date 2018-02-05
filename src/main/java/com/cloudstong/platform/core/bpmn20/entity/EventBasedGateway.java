package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tEventBasedGateway")
public class EventBasedGateway extends Gateway
{

  @XmlAttribute
  protected Boolean instantiate;

  @XmlAttribute
  protected EventBasedGatewayType eventGatewayType;

  public boolean isInstantiate()
  {
    if (instantiate == null) {
      return false;
    }
    return instantiate.booleanValue();
  }

  public void setInstantiate(Boolean value)
  {
    instantiate = value;
  }

  public EventBasedGatewayType getEventGatewayType()
  {
    if (eventGatewayType == null) {
      return EventBasedGatewayType.EXCLUSIVE;
    }
    return eventGatewayType;
  }

  public void setEventGatewayType(EventBasedGatewayType value)
  {
    eventGatewayType = value;
  }
}