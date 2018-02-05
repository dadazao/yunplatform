package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCorrelationSubscription", propOrder={"correlationPropertyBinding"})
public class CorrelationSubscription extends BaseElement
{
  protected List<CorrelationPropertyBinding> correlationPropertyBinding;

  @XmlAttribute(required=true)
  protected QName correlationKeyRef;

  public List<CorrelationPropertyBinding> getCorrelationPropertyBinding()
  {
    if (correlationPropertyBinding == null) {
      correlationPropertyBinding = new ArrayList();
    }
    return correlationPropertyBinding;
  }

  public QName getCorrelationKeyRef()
  {
    return correlationKeyRef;
  }

  public void setCorrelationKeyRef(QName value)
  {
    correlationKeyRef = value;
  }
}