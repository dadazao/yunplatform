package com.cloudstong.platform.core.bpmn20.entity.activiti;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"value"})
@XmlRootElement(name="formProperty")
public class FormProperty
{
  protected List<Value> value;

  @XmlAttribute(required=true)
  protected String id;

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected String type;

  @XmlAttribute
  protected String readable;

  @XmlAttribute
  protected String writable;

  @XmlAttribute
  protected String required;

  @XmlAttribute
  protected String variable;

  @XmlAttribute
  protected String expression;

  @XmlAttribute
  protected String datePattern;

  @XmlAttribute(name="value")
  protected String attrValue;

  public List<Value> getValue()
  {
    if (value == null) {
      value = new ArrayList();
    }
    return value;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String value)
  {
    id = value;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String value)
  {
    name = value;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String value)
  {
    type = value;
  }

  public String getReadable()
  {
    return readable;
  }

  public void setReadable(String value)
  {
    readable = value;
  }

  public String getWritable()
  {
    return writable;
  }

  public void setWritable(String value)
  {
    writable = value;
  }

  public String getRequired()
  {
    return required;
  }

  public void setRequired(String value)
  {
    required = value;
  }

  public String getVariable()
  {
    return variable;
  }

  public void setVariable(String value)
  {
    variable = value;
  }

  public String getExpression()
  {
    return expression;
  }

  public void setExpression(String value)
  {
    expression = value;
  }

  public String getDatePattern()
  {
    return datePattern;
  }

  public void setDatePattern(String value)
  {
    datePattern = value;
  }

  public String getAttrValue()
  {
    return attrValue;
  }

  public void setAttrValue(String value)
  {
    attrValue = value;
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name="")
  public static class Value
  {

    @XmlAttribute
    protected String id;

    @XmlAttribute
    protected String name;

    public String getId()
    {
      return id;
    }

    public void setId(String value)
    {
      id = value;
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
}