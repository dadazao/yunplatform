package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tResourceParameterBinding", propOrder={"expression"})
public class ResourceParameterBinding extends BaseElement
{

  @XmlElementRef(name="expression", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected JAXBElement<? extends Expression> expression;

  @XmlAttribute(required=true)
  protected QName parameterRef;

  public JAXBElement<? extends Expression> getExpression()
  {
    return expression;
  }

  public void setExpression(JAXBElement<? extends Expression> value)
  {
    expression = value;
  }

  public QName getParameterRef()
  {
    return parameterRef;
  }

  public void setParameterRef(QName value)
  {
    parameterRef = value;
  }
}