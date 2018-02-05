package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tChoreography", propOrder={"flowElement"})
@XmlSeeAlso({GlobalChoreographyTask.class})
public class Choreography extends Collaboration
{

  @XmlElementRef(name="flowElement", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends FlowElement>> flowElement;

  public List<JAXBElement<? extends FlowElement>> getFlowElement()
  {
    if (flowElement == null) {
      flowElement = new ArrayList();
    }
    return flowElement;
  }
}