package com.cloudstong.platform.third.bpm.graph;

public class Point
{
  private float x = 0.0F;
  private float y = 0.0F;

  public Point(float x, float y)
  {
    this.x = x;
    this.y = y;
  }

  public float getX() {
    return x;
  }

  public void setX(float x)
  {
    this.x = x;
  }
  public float getY() {
    return y;
  }

  public void setY(float y)
  {
    this.y = y;
  }
}