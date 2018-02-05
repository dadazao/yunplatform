package com.cloudstong.platform.core.bpmn20.entity.omgdi;

import com.cloudstong.platform.core.bpmn20.entity.bpmndi.BPMNShape;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="LabeledShape")
@XmlSeeAlso({BPMNShape.class})
public abstract class LabeledShape extends Shape
{
}