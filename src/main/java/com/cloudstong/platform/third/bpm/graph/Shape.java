package com.cloudstong.platform.third.bpm.graph;

import java.util.List;

public class Shape
{
  private float x = 0.0F;
  private float y = 0.0F;
  private float w = 0.0F;
  private float h = 0.0F;
  private String name = "";
  private List<Port> ports;
  private DirEnum dir;
  private float offset = 0.0F;

  public Shape(String name, float x, float y, float w, float h)
  {
    this.h = h;
    if ((name.equals("bg:StartEvent")) || (name.equals("bg:EndEvent"))) {
      this.h = w;
    }

    this.name = name;

    this.x = x;
    this.y = y;
    this.w = w;
  }

  public float getOffset()
  {
    return offset;
  }

  public void setOffset(float offset) {
    this.offset = offset;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DirEnum getDirectory()
  {
    return dir;
  }

  public float getX()
  {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getW() {
    return w;
  }

  public void setW(float w) {
    this.w = w;
  }

  public float getH() {
    return h;
  }

  public void setH(float h) {
    this.h = h;
  }

  public List<Port> getPorts() {
    return ports;
  }

  public void setPorts(List<Port> ports) {
    this.ports = ports;
  }

  public float getCenterX()
  {
    return (x + w) / 2.0F;
  }
  public float getCenterY() {
    return (y + h) / 2.0F;
  }
  public float getBottomRightX() {
    return x + w;
  }
  public float getBottomRightY() {
    return y + h;
  }
}