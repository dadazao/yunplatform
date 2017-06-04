package com.cloudstong.platform.core.bpmn20.entity.omgdc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Point")
public class Point
{

  @XmlAttribute(required=true)
  protected double x;

  @XmlAttribute(required=true)
  protected double y;

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
}