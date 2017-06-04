package com.cloudstong.platform.core.bpmn20.entity.bpmndi;

import com.cloudstong.platform.core.bpmn20.entity.omgdc.Font;
import com.cloudstong.platform.core.bpmn20.entity.omgdi.Style;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BPMNLabelStyle", propOrder={"font"})
public class BPMNLabelStyle extends Style
{

  @XmlElement(name="Font", namespace="http://www.omg.org/spec/DD/20100524/DC", required=true)
  protected Font font;

  public Font getFont()
  {
    return font;
  }

  public void setFont(Font value)
  {
    font = value;
  }
}