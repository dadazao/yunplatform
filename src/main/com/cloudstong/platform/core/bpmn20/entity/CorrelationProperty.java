package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCorrelationProperty", propOrder={"correlationPropertyRetrievalExpression"})
public class CorrelationProperty extends RootElement
{

  @XmlElement(required=true)
  protected List<CorrelationPropertyRetrievalExpression> correlationPropertyRetrievalExpression;

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected QName type;

  public List<CorrelationPropertyRetrievalExpression> getCorrelationPropertyRetrievalExpression()
  {
    if (correlationPropertyRetrievalExpression == null) {
      correlationPropertyRetrievalExpression = new ArrayList();
    }
    return correlationPropertyRetrievalExpression;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public QName getType()
  {
    return type;
  }

  public void setType(QName value)
  {
    type = value;
  }
}