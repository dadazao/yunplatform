package com.cloudstong.platform.third.bpm.graph;

public enum PortType
{
  POSITION("position"), 
  NODE_PART_REFERENCE("nodePartReference"), 
  AUTOMATIC_SIDE("automaticSide");

  private String text;

  private PortType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public String toString()
  {
    return text;
  }

  public static PortType fromString(String text) {
    if (text != null) {
      for (PortType type : values()) {
        if (text.equalsIgnoreCase(type.text)) {
          return type;
        }
      }
    }
    return null;
  }
}