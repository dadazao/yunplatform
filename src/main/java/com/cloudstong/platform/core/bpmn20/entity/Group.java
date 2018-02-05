package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tGroup")
public class Group extends Artifact
{

  @XmlAttribute
  protected QName categoryValueRef;

  public QName getCategoryValueRef()
  {
    return categoryValueRef;
  }

  public void setCategoryValueRef(QName value)
  {
    categoryValueRef = value;
  }
}