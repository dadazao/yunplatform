package com.cloudstong.platform.core.bpmn20.entity.omgdi;

import com.cloudstong.platform.core.bpmn20.entity.bpmndi.BPMNPlane;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Plane", propOrder={"diagramElement"})
@XmlSeeAlso({BPMNPlane.class})
public abstract class Plane extends Node
{

  @XmlElementRef(name="DiagramElement", namespace="http://www.omg.org/spec/DD/20100524/DI", type=JAXBElement.class)
  protected List<JAXBElement<? extends DiagramElement>> diagramElement;

  public List<JAXBElement<? extends DiagramElement>> getDiagramElement()
  {
    if (diagramElement == null) {
      diagramElement = new ArrayList();
    }
    return diagramElement;
  }
}