package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCorrelationPropertyBinding", propOrder={"dataPath"})
public class CorrelationPropertyBinding extends BaseElement
{

  @XmlElement(required=true)
  protected FormalExpression dataPath;

  @XmlAttribute(required=true)
  protected QName correlationPropertyRef;

  public FormalExpression getDataPath()
  {
    return dataPath;
  }

  public void setDataPath(FormalExpression value)
  {
    dataPath = value;
  }

  public QName getCorrelationPropertyRef()
  {
    return correlationPropertyRef;
  }

  public void setCorrelationPropertyRef(QName value)
  {
    correlationPropertyRef = value;
  }
}