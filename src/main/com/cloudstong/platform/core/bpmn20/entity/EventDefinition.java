package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tEventDefinition")
@XmlSeeAlso({TimerEventDefinition.class, CancelEventDefinition.class, MessageEventDefinition.class, ErrorEventDefinition.class, ConditionalEventDefinition.class, TerminateEventDefinition.class, LinkEventDefinition.class, EscalationEventDefinition.class, CompensateEventDefinition.class, SignalEventDefinition.class})
public abstract class EventDefinition extends RootElement
{
}