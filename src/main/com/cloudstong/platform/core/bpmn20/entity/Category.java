package com.cloudstong.platform.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tCategory", propOrder={"categoryValue"})
public class Category extends RootElement
{
  protected List<CategoryValue> categoryValue;

  @XmlAttribute
  protected String name;

  public List<CategoryValue> getCategoryValue()
  {
    if (categoryValue == null) {
      categoryValue = new ArrayList();
    }
    return categoryValue;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }
}