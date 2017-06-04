package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tInputOutputBinding")
public class InputOutputBinding extends BaseElement
{

  @XmlAttribute(required=true)
  protected QName operationRef;

  @XmlAttribute(required=true)
  @XmlIDREF
  @XmlSchemaType(name="IDREF")
  protected Object inputDataRef;

  @XmlAttribute(required=true)
  @XmlIDREF
  @XmlSchemaType(name="IDREF")
  protected Object outputDataRef;

  public QName getOperationRef()
  {
    return operationRef;
  }

  public void setOperationRef(QName value)
  {
    operationRef = value;
  }

  public Object getInputDataRef()
  {
    return inputDataRef;
  }

  public void setInputDataRef(Object value)
  {
    inputDataRef = value;
  }

  public Object getOutputDataRef()
  {
    return outputDataRef;
  }

  public void setOutputDataRef(Object value)
  {
    outputDataRef = value;
  }
}