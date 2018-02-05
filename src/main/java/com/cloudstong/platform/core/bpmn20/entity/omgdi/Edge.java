package com.cloudstong.platform.core.bpmn20.entity.omgdi;

import com.cloudstong.platform.core.bpmn20.entity.omgdc.Point;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Edge", propOrder={"waypoint"})
@XmlSeeAlso({LabeledEdge.class})
public abstract class Edge extends DiagramElement
{

  @XmlElement(required=true)
  protected List<Point> waypoint;

  public List<Point> getWaypoint()
  {
    if (waypoint == null) {
      waypoint = new ArrayList();
    }
    return waypoint;
  }
}