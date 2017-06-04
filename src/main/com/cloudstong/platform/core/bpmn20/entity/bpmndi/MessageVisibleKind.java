package com.cloudstong.platform.core.bpmn20.entity.bpmndi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="MessageVisibleKind")
@XmlEnum
public enum MessageVisibleKind
{
  INITIATING(
    "initiating"), 
  NON_INITIATING(
    "non_initiating");

  private final String value;

  private MessageVisibleKind(String v) { value = v; }

  public String value()
  {
    return value;
  }

  public static MessageVisibleKind fromValue(String v) {
    for (MessageVisibleKind c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}