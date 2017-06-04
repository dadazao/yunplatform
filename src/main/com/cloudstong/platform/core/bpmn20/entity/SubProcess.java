package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tSubProcess", propOrder={"laneSet", "flowElement", "artifact"})
@XmlSeeAlso({AdHocSubProcess.class, Transaction.class})
public class SubProcess extends Activity
{
  protected List<LaneSet> laneSet;

  @XmlElementRef(name="flowElement", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends FlowElement>> flowElement;

  @XmlElementRef(name="artifact", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends Artifact>> artifact;

  @XmlAttribute
  protected Boolean triggeredByEvent;

  public List<LaneSet> getLaneSet()
  {
    if (laneSet == null) {
      laneSet = new ArrayList();
    }
    return laneSet;
  }

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

  public boolean isTriggeredByEvent()
  {
    if (triggeredByEvent == null) {
      return false;
    }
    return triggeredByEvent.booleanValue();
  }

  public void setTriggeredByEvent(Boolean value)
  {
    triggeredByEvent = value;
  }
}