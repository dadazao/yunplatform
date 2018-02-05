package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tDataAssociation", propOrder={"sourceRef", "targetRef", "transformation", "assignment"})
@XmlSeeAlso({DataInputAssociation.class, DataOutputAssociation.class})
public class DataAssociation extends BaseElement
{

  @XmlElementRef(name="sourceRef", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<Object>> sourceRef;

  @XmlElement(required=true)
  @XmlIDREF
  @XmlSchemaType(name="IDREF")
  protected Object targetRef;
  protected FormalExpression transformation;
  protected List<Assignment> assignment;

  public List<JAXBElement<Object>> getSourceRef()
  {
    if (sourceRef == null) {
      sourceRef = new ArrayList();
    }
    return sourceRef;
  }

  public Object getTargetRef()
  {
    return targetRef;
  }

  public void setTargetRef(Object value)
  {
    targetRef = value;
  }

  public FormalExpression getTransformation()
  {
    return transformation;
  }

  public void setTransformation(FormalExpression value)
  {
    transformation = value;
  }

  public List<Assignment> getAssignment()
  {
    if (assignment == null) {
      assignment = new ArrayList();
    }
    return assignment;
  }
}