package com.cloudstong.platform.third.bpm.graph;

public class ShapeMeta
{
  private String xml = "";

  private int width = 0;

  private int height = 0;

  public ShapeMeta(int w, int h, String xml)
  {
    width = w;
    height = h;
    this.xml = xml;
  }

  public String getXml() {
    return xml;
  }

  public void setXml(String xml) {
    this.xml = xml;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}