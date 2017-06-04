package com.cloudstong.platform.core.bpmn20.entity.omgdc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _Bounds_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DC", "Bounds");
  private static final QName _Point_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DC", "Point");
  private static final QName _Font_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DC", "Font");

  public Font createFont()
  {
    return new Font();
  }

  public Bounds createBounds()
  {
    return new Bounds();
  }

  public Point createPoint()
  {
    return new Point();
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DC", name="Bounds")
  public JAXBElement<Bounds> createBounds(Bounds value)
  {
    return new JAXBElement(_Bounds_QNAME, Bounds.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DC", name="Point")
  public JAXBElement<Point> createPoint(Point value)
  {
    return new JAXBElement(_Point_QNAME, Point.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DC", name="Font")
  public JAXBElement<Font> createFont(Font value)
  {
    return new JAXBElement(_Font_QNAME, Font.class, null, value);
  }
}