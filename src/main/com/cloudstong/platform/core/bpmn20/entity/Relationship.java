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
@XmlType(name="tRelationship", propOrder={"source", "target"})
public class Relationship extends BaseElement
{

  @XmlElement(required=true)
  protected List<QName> source;

  @XmlElement(required=true)
  protected List<QName> target;

  @XmlAttribute(required=true)
  protected String type;

  @XmlAttribute
  protected RelationshipDirection direction;

  public List<QName> getSource()
  {
    if (source == null) {
      source = new ArrayList();
    }
    return source;
  }

  public List<QName> getTarget()
  {
    if (target == null) {
      target = new ArrayList();
    }
    return target;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String value)
  {
    type = value;
  }

  public RelationshipDirection getDirection()
  {
    return direction;
  }

  public void setDirection(RelationshipDirection value)
  {
    direction = value;
  }
}