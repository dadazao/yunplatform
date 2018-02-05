package com.cloudstong.platform.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="tTask")
@XmlSeeAlso({ManualTask.class, ServiceTask.class, ScriptTask.class, ReceiveTask.class, BusinessRuleTask.class, SendTask.class, UserTask.class})
public class Task extends Activity
{
}