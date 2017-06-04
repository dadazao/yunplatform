package com.cloudstong.platform.core.bpmn20.entity.ht;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _Order_QNAME = new QName("http://www.jee-soft.cn/BPMN20EXT", "order");

  @XmlElementDecl(namespace="http://www.jee-soft.cn/BPMN20EXT", name="order")
  public JAXBElement<Integer> createOrder(Integer value)
  {
    return new JAXBElement(_Order_QNAME, Integer.class, null, value);
  }
}