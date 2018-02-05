package com.cloudstong.platform.core.bpmn20.entity.bpmndi;

import com.cloudstong.platform.core.bpmn20.entity.omgdi.Plane;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BPMNPlane")
public class BPMNPlane extends Plane
{

  @XmlAttribute
  protected QName bpmnElement;

  public QName getBpmnElement()
  {
    return bpmnElement;
  }

  public void setBpmnElement(QName value)
  {
    bpmnElement = value;
  }
}