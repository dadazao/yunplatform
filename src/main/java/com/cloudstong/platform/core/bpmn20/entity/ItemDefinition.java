package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tItemDefinition")
public class ItemDefinition extends RootElement
{

  @XmlAttribute
  protected QName structureRef;

  @XmlAttribute
  protected Boolean isCollection;

  @XmlAttribute
  protected ItemKind itemKind;

  public QName getStructureRef()
  {
    return structureRef;
  }

  public void setStructureRef(QName value)
  {
    structureRef = value;
  }

  public boolean isIsCollection()
  {
    if (isCollection == null) {
      return false;
    }
    return isCollection.booleanValue();
  }

  public void setIsCollection(Boolean value)
  {
    isCollection = value;
  }

  public ItemKind getItemKind()
  {
    if (itemKind == null) {
      return ItemKind.INFORMATION;
    }
    return itemKind;
  }

  public void setItemKind(ItemKind value)
  {
    itemKind = value;
  }
}