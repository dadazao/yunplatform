package com.cloudstong.platform.core.soap.type;

import java.util.Arrays;
import java.util.List;

public enum SoapTypes
{
  string("字符串(string)", new StringSoapType()), 
  number(
    "数字(number)", new NumberSoapType()), 
  date(
    "时间/日期(date)", new DateSoapType()), 
  bean(
    "复合类型(bean)", new BeanSoapType());

  private SoapType soapType;
  private String name;

  private SoapTypes(String name, SoapType soapType) { this.name = name;
    this.soapType = soapType; }

  public SoapType getSoapType()
  {
    return soapType;
  }

  public String getName() {
    return name;
  }

  public String toString()
  {
    return name();
  }

  public static SoapType getTypeByBean(Class<?> klass)
  {
    if (klass == null) {
      return bean.getSoapType();
    }

    for (SoapTypes types : values()) {
      if (Arrays.asList(types.getSoapType().getBeanTypes()).contains(klass)) {
        return types.getSoapType();
      }
    }

    return bean.getSoapType();
  }

  public static SoapType getTypeBySoap(String type)
  {
    if (type == null) {
      return bean.getSoapType();
    }

    for (SoapTypes types : values()) {
      if (Arrays.asList(types.getSoapType().getSoapTypes()).contains(type)) {
        return types.getSoapType();
      }
    }

    return bean.getSoapType();
  }
}