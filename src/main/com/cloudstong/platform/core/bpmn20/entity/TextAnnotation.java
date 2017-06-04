package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tTextAnnotation", propOrder={"text"})
public class TextAnnotation extends Artifact
{
  protected Text text;

  @XmlAttribute
  protected String textFormat;

  public Text getText()
  {
    return text;
  }

  public void setText(Text value)
  {
    text = value;
  }

  public String getTextFormat()
  {
    if (textFormat == null) {
      return "text/plain";
    }
    return textFormat;
  }

  public void setTextFormat(String value)
  {
    textFormat = value;
  }
}