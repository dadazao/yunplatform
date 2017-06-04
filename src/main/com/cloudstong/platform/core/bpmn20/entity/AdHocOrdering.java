package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="tAdHocOrdering")
@XmlEnum
public enum AdHocOrdering
{
  PARALLEL(
    "Parallel"), 
  SEQUENTIAL(
    "Sequential");

  private final String value;

  private AdHocOrdering(String v) { value = v; }

  public String value()
  {
    return value;
  }

  public static AdHocOrdering fromValue(String v) {
    for (AdHocOrdering c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}