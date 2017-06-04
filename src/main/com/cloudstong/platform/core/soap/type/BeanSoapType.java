package com.cloudstong.platform.core.soap.type;

import java.lang.reflect.Field;
import javax.xml.soap.SOAPElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NodeList;

public class BeanSoapType extends BaseSoapType
{
  private static Log logger = LogFactory.getLog(BaseSoapType.class);

  public Class<?>[] getBeanTypes()
  {
    return new Class[] { Object.class };
  }

  public String[] getSoapTypes()
  {
    return new String[] { "anyType" };
  }

  void setCurrentValue(SOAPElement element, Object obj, Class<?> klass)
  {
    for (Field field : klass.getDeclaredFields()) {
      field.setAccessible(true);
      Class fieldType = field.getType();
      String fieldName = field.getName();
      NodeList fieldNodeList = element.getElementsByTagName(fieldName);
      if ((fieldNodeList != null) && (fieldNodeList.getLength() >= 1))
      {
        try
        {
          SoapTypes.getTypeByBean(fieldType).setValue((SOAPElement)fieldNodeList.item(0), field.get(obj), 
            fieldType);
        }
        catch (Exception e) {
          logger.warn("字段[" + fieldName + "]设置失败.", e);
        }
      }
    }
  }

  Object convertCurrent(Class<?> klass, SOAPElement element)
  {
    try
    {
     Object bean = klass.newInstance();
    }
    catch (Exception e)
    {
      Object bean;
      logger.error("类别[" + klass + "]无法实例化.", e);
      return null;
    }
    Object bean = null;
    for (Field field : klass.getDeclaredFields()) {
      field.setAccessible(true);
      Class fieldType = field.getType();
      String fieldName = field.getName();
      NodeList fieldNodeList = element.getElementsByTagName(fieldName);
      if ((fieldNodeList != null) && (fieldNodeList.getLength() >= 1))
      {
        try
        {
          Object obj = SoapTypes.getTypeByBean(fieldType).convertToBean(fieldType, new SOAPElement[] { element });
          field.set(bean, obj);
        }
        catch (Exception e) {
          logger.warn("字段[" + fieldName + "]设置失败.", e);
        }
      }
    }

    return bean;
  }
}