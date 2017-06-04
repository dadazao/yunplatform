package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="tMultiInstanceFlowCondition")
@XmlEnum
public enum MultiInstanceFlowCondition
{
  NONE(
    "None"), 
  ONE(
    "One"), 
  ALL(
    "All"), 
  COMPLEX(
    "Complex");

  private final String value;

  private MultiInstanceFlowCondition(String v) { value = v; }

  public String value()
  {
    return value;
  }

  public static MultiInstanceFlowCondition fromValue(String v) {
    for (MultiInstanceFlowCondition c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}