package com.cloudstong.platform.core.soap.type;

import java.math.BigDecimal;
import javax.xml.soap.SOAPElement;

public class NumberSoapType extends BaseSoapType
{
  public Class<?>[] getBeanTypes()
  {
    return new Class[] { Number.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class, Short.TYPE, Short.class, 
      Double.TYPE, Double.class, Float.TYPE, Float.class, BigDecimal.class };
  }

  public String[] getSoapTypes()
  {
    return new String[] { "int", "long", "short", "integer", "float", "double", "number", "bigint", "smallint" };
  }

  void setCurrentValue(SOAPElement element, Object obj, Class<?> klass)
  {
    element.setTextContent(obj.toString());
  }

  Object convertCurrent(Class<?> klass, SOAPElement element)
  {
    String value = element.getTextContent();
    if (klass == Integer.class)
      return Integer.valueOf(value);
    if (klass == Long.class)
      return Long.valueOf(value);
    if (klass == Short.class)
      return Short.valueOf(value);
    if (klass == Double.class)
      return Double.valueOf(value);
    if (klass == Float.class)
      return Float.valueOf(value);
    if (klass == BigDecimal.class) {
      return BigDecimal.valueOf(Double.valueOf(value).doubleValue());
    }
    return value;
  }
}