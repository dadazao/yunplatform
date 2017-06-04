package com.cloudstong.platform.core.bpmn20.entity.bpmndi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipantBandKind")
@XmlEnum
public enum ParticipantBandKind
{
  TOP_INITIATING(
    "top_initiating"), 
  MIDDLE_INITIATING(
    "middle_initiating"), 
  BOTTOM_INITIATING(
    "bottom_initiating"), 
  TOP_NON_INITIATING(
    "top_non_initiating"), 
  MIDDLE_NON_INITIATING(
    "middle_non_initiating"), 
  BOTTOM_NON_INITIATING(
    "bottom_non_initiating");

  private final String value;

  private ParticipantBandKind(String v) { value = v; }

  public String value()
  {
    return value;
  }

  public static ParticipantBandKind fromValue(String v) {
    for (ParticipantBandKind c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}