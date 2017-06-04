package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="tEventBasedGatewayType")
@XmlEnum
public enum EventBasedGatewayType
{
  EXCLUSIVE(
    "Exclusive"), 
  PARALLEL(
    "Parallel");

  private final String value;

  private EventBasedGatewayType(String v) { value = v; }

  public String value()
  {
    return value;
  }

  public static EventBasedGatewayType fromValue(String v) {
    for (EventBasedGatewayType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}