package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tSubChoreography", propOrder={"flowElement", "artifact"})
public class SubChoreography extends ChoreographyActivity
{

  @XmlElementRef(name="flowElement", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends FlowElement>> flowElement;

  @XmlElementRef(name="artifact", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends Artifact>> artifact;

  public List<JAXBElement<? extends FlowElement>> getFlowElement()
  {
    if (flowElement == null) {
      flowElement = new ArrayList();
    }
    return flowElement;
  }

  public List<JAXBElement<? extends Artifact>> getArtifact()
  {
    if (artifact == null) {
      artifact = new ArrayList();
    }
    return artifact;
  }
}