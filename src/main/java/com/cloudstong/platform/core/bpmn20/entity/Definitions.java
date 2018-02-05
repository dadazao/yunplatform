package com.cloudstong.platform.core.bpmn20.entity;

import com.cloudstong.platform.core.bpmn20.entity.bpmndi.BPMNDiagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tDefinitions", propOrder={"_import", "extension", "rootElement", "bpmnDiagram", "relationship"})
public class Definitions
{

  @XmlElement(name="import")
  protected List<Import> _import;
  protected List<Extension> extension;

  @XmlElementRef(name="rootElement", namespace="http://www.omg.org/spec/BPMN/20100524/MODEL", type=JAXBElement.class)
  protected List<JAXBElement<? extends RootElement>> rootElement;

  @XmlElement(name="BPMNDiagram", namespace="http://www.omg.org/spec/BPMN/20100524/DI")
  protected List<BPMNDiagram> bpmnDiagram;
  protected List<Relationship> relationship;

  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name="ID")
  protected String id;

  @XmlAttribute
  protected String name;

  @XmlAttribute(required=true)
  @XmlSchemaType(name="anyURI")
  protected String targetNamespace;

  @XmlAttribute
  @XmlSchemaType(name="anyURI")
  protected String expressionLanguage;

  @XmlAttribute
  @XmlSchemaType(name="anyURI")
  protected String typeLanguage;

  @XmlAttribute
  protected String exporter;

  @XmlAttribute
  protected String exporterVersion;

  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap();

  public List<Import> getImport()
  {
    if (_import == null) {
      _import = new ArrayList();
    }
    return _import;
  }

  public List<Extension> getExtension()
  {
    if (extension == null) {
      extension = new ArrayList();
    }
    return extension;
  }

  public List<JAXBElement<? extends RootElement>> getRootElement()
  {
    if (rootElement == null) {
      rootElement = new ArrayList();
    }
    return rootElement;
  }

  public List<BPMNDiagram> getBPMNDiagram()
  {
    if (bpmnDiagram == null) {
      bpmnDiagram = new ArrayList();
    }
    return bpmnDiagram;
  }

  public List<Relationship> getRelationship()
  {
    if (relationship == null) {
      relationship = new ArrayList();
    }
    return relationship;
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

  public String getTargetNamespace()
  {
    return targetNamespace;
  }

  public void setTargetNamespace(String value)
  {
    targetNamespace = value;
  }

  public String getExpressionLanguage()
  {
    if (expressionLanguage == null) {
      return "http://www.w3.org/1999/XPath";
    }
    return expressionLanguage;
  }

  public void setExpressionLanguage(String value)
  {
    expressionLanguage = value;
  }

  public String getTypeLanguage()
  {
    if (typeLanguage == null) {
      return "http://www.w3.org/2001/XMLSchema";
    }
    return typeLanguage;
  }

  public void setTypeLanguage(String value)
  {
    typeLanguage = value;
  }

  public String getExporter()
  {
    return exporter;
  }

  public void setExporter(String value)
  {
    exporter = value;
  }

  public String getExporterVersion()
  {
    return exporterVersion;
  }

  public void setExporterVersion(String value)
  {
    exporterVersion = value;
  }

  public Map<QName, String> getOtherAttributes()
  {
    return otherAttributes;
  }
}