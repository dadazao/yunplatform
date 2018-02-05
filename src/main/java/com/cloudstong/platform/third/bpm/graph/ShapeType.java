package com.cloudstong.platform.third.bpm.graph;

public enum ShapeType
{
  STRAIGHT("straight"), 
  FREE("free"), 
  ORTHOGONAL("orthogonal"), 
  OBLIQUE("oblique");

  private String text;

  private ShapeType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public String toString()
  {
    return text;
  }

  public static ShapeType fromString(String text) {
    if (text != null) {
      for (ShapeType type : values()) {
        if (text.equalsIgnoreCase(type.text)) {
          return type;
        }
      }
    }
    return null;
  }
}