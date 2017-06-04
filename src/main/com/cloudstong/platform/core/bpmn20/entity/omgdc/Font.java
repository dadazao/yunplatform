package com.cloudstong.platform.core.bpmn20.entity.omgdc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Font")
public class Font
{

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected Double size;

  @XmlAttribute
  protected Boolean isBold;

  @XmlAttribute
  protected Boolean isItalic;

  @XmlAttribute
  protected Boolean isUnderline;

  @XmlAttribute
  protected Boolean isStrikeThrough;

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public Double getSize()
  {
    return size;
  }

  public void setSize(Double value)
  {
    size = value;
  }

  public Boolean isIsBold()
  {
    return isBold;
  }

  public void setIsBold(Boolean value)
  {
    isBold = value;
  }

  public Boolean isIsItalic()
  {
    return isItalic;
  }

  public void setIsItalic(Boolean value)
  {
    isItalic = value;
  }

  public Boolean isIsUnderline()
  {
    return isUnderline;
  }

  public void setIsUnderline(Boolean value)
  {
    isUnderline = value;
  }

  public Boolean isIsStrikeThrough()
  {
    return isStrikeThrough;
  }

  public void setIsStrikeThrough(Boolean value)
  {
    isStrikeThrough = value;
  }
}