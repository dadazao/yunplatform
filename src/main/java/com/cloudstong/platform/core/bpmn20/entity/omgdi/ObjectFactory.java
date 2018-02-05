package com.cloudstong.platform.core.bpmn20.entity.omgdi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _Style_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "Style");
  private static final QName _LabeledEdge_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "LabeledEdge");
  private static final QName _Plane_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "Plane");
  private static final QName _LabeledShape_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "LabeledShape");
  private static final QName _Shape_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "Shape");
  private static final QName _Edge_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "Edge");
  private static final QName _Node_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "Node");
  private static final QName _Diagram_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "Diagram");
  private static final QName _Label_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "Label");
  private static final QName _DiagramElement_QNAME = new QName("http://www.omg.org/spec/DD/20100524/DI", "DiagramElement");

  public DiagramElement.Extension createDiagramElementExtension()
  {
    return new DiagramElement.Extension();
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="Style")
  public JAXBElement<Style> createStyle(Style value)
  {
    return new JAXBElement(_Style_QNAME, Style.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="LabeledEdge")
  public JAXBElement<LabeledEdge> createLabeledEdge(LabeledEdge value)
  {
    return new JAXBElement(_LabeledEdge_QNAME, LabeledEdge.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="Plane")
  public JAXBElement<Plane> createPlane(Plane value)
  {
    return new JAXBElement(_Plane_QNAME, Plane.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="LabeledShape")
  public JAXBElement<LabeledShape> createLabeledShape(LabeledShape value)
  {
    return new JAXBElement(_LabeledShape_QNAME, LabeledShape.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="Shape")
  public JAXBElement<Shape> createShape(Shape value)
  {
    return new JAXBElement(_Shape_QNAME, Shape.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="Edge")
  public JAXBElement<Edge> createEdge(Edge value)
  {
    return new JAXBElement(_Edge_QNAME, Edge.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="Node")
  public JAXBElement<Node> createNode(Node value)
  {
    return new JAXBElement(_Node_QNAME, Node.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="Diagram")
  public JAXBElement<Diagram> createDiagram(Diagram value)
  {
    return new JAXBElement(_Diagram_QNAME, Diagram.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="Label")
  public JAXBElement<Label> createLabel(Label value)
  {
    return new JAXBElement(_Label_QNAME, Label.class, null, value);
  }

  @XmlElementDecl(namespace="http://www.omg.org/spec/DD/20100524/DI", name="DiagramElement")
  public JAXBElement<DiagramElement> createDiagramElement(DiagramElement value)
  {
    return new JAXBElement(_DiagramElement_QNAME, DiagramElement.class, null, value);
  }
}