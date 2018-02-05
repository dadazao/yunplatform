package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tFlowElement", propOrder={"auditing", "monitoring", "categoryValueRef"})
@XmlSeeAlso({SequenceFlow.class, DataObject.class, DataObjectReference.class, DataStoreReference.class, FlowNode.class})
public abstract class FlowElement extends BaseElement
{
  protected Auditing auditing;
  protected Monitoring monitoring;
  protected List<QName> categoryValueRef;

  @XmlAttribute
  protected String name;

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

  public List<QName> getCategoryValueRef()
  {
    if (categoryValueRef == null) {
      categoryValueRef = new ArrayList();
    }
    return categoryValueRef;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }
}