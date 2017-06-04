package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tProcess", propOrder={"auditing", "monitoring", "property", "laneSet", "flowElement", "artifact", "resourceRole", "correlationSubscription", "supports"})
public class Process extends CallableElement
{
  protected Auditing auditing;
  protected Monitoring monitoring;
  protected List<Property> property;
  protected List<LaneSet> laneSet;

  @XmlElementRef(name="flowElement", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends FlowElement>> flowElement;

  @XmlElementRef(name="artifact", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends Artifact>> artifact;

  @XmlElementRef(name="resourceRole", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends ResourceRole>> resourceRole;
  protected List<CorrelationSubscription> correlationSubscription;
  protected List<QName> supports;

  @XmlAttribute
  protected ProcessType processType;

  @XmlAttribute
  protected Boolean isClosed;

  @XmlAttribute
  protected Boolean isExecutable;

  @XmlAttribute
  protected QName definitionalCollaborationRef;

  public Auditing getAuditing()
  {
    return auditing;
  }

  public void setAuditing(Auditing value)
  {
    auditing = value;
  }

  public Monitoring getMonitoring()
  {
    return monitoring;
  }

  public void setMonitoring(Monitoring value)
  {
    monitoring = value;
  }

  public List<Property> getProperty()
  {
    if (property == null) {
      property = new ArrayList();
    }
    return property;
  }

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

  public List<JAXBElement<? extends ResourceRole>> getResourceRole()
  {
    if (resourceRole == null) {
      resourceRole = new ArrayList();
    }
    return resourceRole;
  }

  public List<CorrelationSubscription> getCorrelationSubscription()
  {
    if (correlationSubscription == null) {
      correlationSubscription = new ArrayList();
    }
    return correlationSubscription;
  }

  public List<QName> getSupports()
  {
    if (supports == null) {
      supports = new ArrayList();
    }
    return supports;
  }

  public ProcessType getProcessType()
  {
    if (processType == null) {
      return ProcessType.NONE;
    }
    return processType;
  }

  public void setProcessType(ProcessType value)
  {
    processType = value;
  }

  public boolean isIsClosed()
  {
    if (isClosed == null) {
      return false;
    }
    return isClosed.booleanValue();
  }

  public void setIsClosed(Boolean value)
  {
    isClosed = value;
  }

  public Boolean isIsExecutable()
  {
    return isExecutable;
  }

  public void setIsExecutable(Boolean value)
  {
    isExecutable = value;
  }

  public QName getDefinitionalCollaborationRef()
  {
    return definitionalCollaborationRef;
  }

  public void setDefinitionalCollaborationRef(QName value)
  {
    definitionalCollaborationRef = value;
  }
}