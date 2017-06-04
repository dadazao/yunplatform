package com.cloudstong.platform.core.bpmn20.entity;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tDataStore", propOrder={"dataState"})
public class DataStore extends RootElement
{
  protected DataState dataState;

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected BigInteger capacity;

  @XmlAttribute
  protected Boolean isUnlimited;

  @XmlAttribute
  protected QName itemSubjectRef;

  public DataState getDataState()
  {
    return dataState;
  }

  public void setDataState(DataState value)
  {
    dataState = value;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public BigInteger getCapacity()
  {
    return capacity;
  }

  public void setCapacity(BigInteger value)
  {
    capacity = value;
  }

  public boolean isIsUnlimited()
  {
    if (isUnlimited == null) {
      return true;
    }
    return isUnlimited.booleanValue();
  }

  public void setIsUnlimited(Boolean value)
  {
    isUnlimited = value;
  }

  public QName getItemSubjectRef()
  {
    return itemSubjectRef;
  }

  public void setItemSubjectRef(QName value)
  {
    itemSubjectRef = value;
  }
}