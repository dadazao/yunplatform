package com.cloudstong.platform.core.soap.type;

import java.util.Calendar;
import java.util.Date;

import javax.xml.soap.SOAPElement;

import org.apache.xmlbeans.XmlDateTime;
import org.apache.xmlbeans.impl.values.XmlDateTimeImpl;


public class DateSoapType extends BaseSoapType
{
  public Class<?>[] getBeanTypes()
  {
    return new Class[] { Date.class, Calendar.class };
  }

  public String[] getSoapTypes()
  {
    return new String[] { "date", "datetime" };
  }

  Object convertCurrent(Class<?> klass, SOAPElement element)
  {
    XmlDateTime xmlDateTime = new XmlDateTimeImpl();
    xmlDateTime.setStringValue(element.getTextContent());
    if (klass == Date.class) {
      return xmlDateTime.getDateValue();
    }

    if (klass == Calendar.class) {
      return xmlDateTime.getCalendarValue();
    }

    return element.getTextContent();
  }

  void setCurrentValue(SOAPElement element, Object obj, Class<?> klass)
  {
    XmlDateTime xmlDateTime = new XmlDateTimeImpl();
    if ((obj instanceof Date))
      xmlDateTime.setDateValue((Date)obj);
    else if ((obj instanceof Calendar)) {
      xmlDateTime.setCalendarValue((Calendar)obj);
    }
    element.setTextContent(xmlDateTime.getStringValue());
  }
}