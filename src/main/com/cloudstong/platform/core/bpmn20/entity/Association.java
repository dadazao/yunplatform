package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tAssociation")
public class Association extends Artifact
{

  @XmlAttribute(required=true)
  protected QName sourceRef;

  @XmlAttribute(required=true)
  protected QName targetRef;

  @XmlAttribute
  protected AssociationDirection associationDirection;

  public QName getSourceRef()
  {
    return sourceRef;
  }

  public void setSourceRef(QName value)
  {
    sourceRef = value;
  }

  public QName getTargetRef()
  {
    return targetRef;
  }

  public void setTargetRef(QName value)
  {
    targetRef = value;
  }

  public AssociationDirection getAssociationDirection()
  {
    if (associationDirection == null) {
      return AssociationDirection.NONE;
    }
    return associationDirection;
  }

  public void setAssociationDirection(AssociationDirection value)
  {
    associationDirection = value;
  }
}