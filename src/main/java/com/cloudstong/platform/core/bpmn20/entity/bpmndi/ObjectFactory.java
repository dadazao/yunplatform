package com.cloudstong.platform.core.bpmn20.entity.bpmndi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _BPMNLabelStyle_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/DI", "BPMNLabelStyle");
  private static final QName _BPMNLabel_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/DI", "BPMNLabel");
  private static final QName _BPMNEdge_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/DI", "BPMNEdge");
  private static final QName _BPMNDiagram_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/DI", "BPMNDiagram");
  private static final QName _BPMNShape_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/DI", "BPMNShape");
  private static final QName _BPMNPlane_QNAME = new QName("http://www.omg.org/spec/BPMN/20100524/DI", "BPMNPlane");

  public BPMNShape createBPMNShape()
  {
    return new BPMNShape();
  }

  public BPMNLabel createBPMNLabel()
  {
    return new BPMNLabel();
  }

  public BPMNPlane createBPMNPlane()
  {
    return new BPMNPlane();
  }

  public BPMNDiagram createBPMNDiagram()
  {
    return new BPMNDiagram();
  }

  public BPMNEdge createBPMNEdge()
  {
    return new BPMNEdge();
  }

  public BPMNLabelStyle createBPMNLabelStyle()
  {
    return new BPMNLabelStyle();
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/DI", name="BPMNLabelStyle")
  public JAXBElement<BPMNLabelStyle> createBPMNLabelStyle(BPMNLabelStyle value)
  {
    return new JAXBElement(_BPMNLabelStyle_QNAME, BPMNLabelStyle.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/DI", name="BPMNLabel")
  public JAXBElement<BPMNLabel> createBPMNLabel(BPMNLabel value)
  {
    return new JAXBElement(_BPMNLabel_QNAME, BPMNLabel.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/DI", name="BPMNEdge", substitutionHeadNamespace="http://www.omg.org/spec/DD/20100524/DI", substitutionHeadName="DiagramElement")
  public JAXBElement<BPMNEdge> createBPMNEdge(BPMNEdge value)
  {
    return new JAXBElement(_BPMNEdge_QNAME, BPMNEdge.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/DI", name="BPMNDiagram")
  public JAXBElement<BPMNDiagram> createBPMNDiagram(BPMNDiagram value)
  {
    return new JAXBElement(_BPMNDiagram_QNAME, BPMNDiagram.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/DI", name="BPMNShape", substitutionHeadNamespace="http://www.omg.org/spec/DD/20100524/DI", substitutionHeadName="DiagramElement")
  public JAXBElement<BPMNShape> createBPMNShape(BPMNShape value)
  {
    return new JAXBElement(_BPMNShape_QNAME, BPMNShape.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/BPMN/20100524/DI", name="BPMNPlane")
  public JAXBElement<BPMNPlane> createBPMNPlane(BPMNPlane value)
  {
    return new JAXBElement(_BPMNPlane_QNAME, BPMNPlane.class, null, value);
  }
}