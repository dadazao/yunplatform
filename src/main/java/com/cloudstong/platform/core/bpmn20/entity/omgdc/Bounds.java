package com.cloudstong.platform.core.bpmn20.entity.omgdc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Bounds")
public class Bounds
{

  @XmlAttribute(required=true)
  protected double x;

  @XmlAttribute(required=true)
  protected double y;

  @XmlAttribute(required=true)
  protected double width;

  @XmlAttribute(required=true)
  protected double height;

  public double getX()
  {
    return x;
  }

  public void setX(double value)
  {
    x = value;
  }

  public double getY()
  {
    return y;
  }

  public void setY(double value)
  {
    y = value;
  }

  public double getWidth()
  {
    return width;
  }

  public void setWidth(double value)
  {
    width = value;
  }

  public double getHeight()
  {
    return height;
  }

  public void setHeight(double value)
  {
    height = value;
  }
}