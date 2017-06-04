package com.cloudstong.platform.core.bpmn20.entity.activiti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"string", "expression"})
@XmlRootElement(name="field")
public class Field
{
  protected String string;
  protected String expression;

  @XmlAttribute(required=true)
  protected String name;

  @XmlAttribute
  protected String stringValue;

  @XmlAttribute(name="expression")
  protected String attrExpression;

  public String getString()
  {
    return string;
  }

  public void setString(String value)
  {
    string = value;
  }

  public String getExpression()
  {
    return expression;
  }

  public void setExpression(String value)
  {
    expression = value;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public String getStringValue()
  {
    return stringValue;
  }

  public void setStringValue(String value)
  {
    stringValue = value;
  }

  public String getAttrExpression()
  {
    return attrExpression;
  }

  public void setAttrExpression(String value)
  {
    attrExpression = value;
  }
}