package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tDataStoreReference", propOrder={"dataState"})
public class DataStoreReference extends FlowElement
{
  protected DataState dataState;

  @XmlAttribute
  protected QName itemSubjectRef;

  @XmlAttribute
  protected QName dataStoreRef;

  public DataState getDataState()
  {
    return dataState;
  }

  public void setDataState(DataState value)
  {
    dataState = value;
  }

  public QName getItemSubjectRef()
  {
    return itemSubjectRef;
  }

  public void setItemSubjectRef(QName value)
  {
    itemSubjectRef = value;
  }

  public QName getDataStoreRef()
  {
    return dataStoreRef;
  }

  public void setDataStoreRef(QName value)
  {
    dataStoreRef = value;
  }
}