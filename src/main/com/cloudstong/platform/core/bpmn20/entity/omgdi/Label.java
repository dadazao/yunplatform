package com.cloudstong.platform.core.bpmn20.entity.omgdi;

import com.cloudstong.platform.core.bpmn20.entity.bpmndi.BPMNLabel;
import com.cloudstong.platform.core.bpmn20.entity.omgdc.Bounds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Label", propOrder={"bounds"})
@XmlSeeAlso({BPMNLabel.class})
public abstract class Label extends Node
{

  @XmlElement(name="Bounds", namespace="http://www.omg.org/spec/DD/20100524/DC")
  protected Bounds bounds;

  public Bounds getBounds()
  {
    return bounds;
  }

  public void setBounds(Bounds value)
  {
    bounds = value;
  }
}