package com.cloudstong.platform.third.bpm.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BpmNodeUserCalculationSelector
{
  private LinkedHashMap<Short, IBpmNodeUserCalculation> bpmNodeUserCalculations = new LinkedHashMap();

  public LinkedHashMap<Short, IBpmNodeUserCalculation> getBpmNodeUserCalculation() {
    return bpmNodeUserCalculations;
  }

  public void setBpmNodeUserCalculation(LinkedHashMap<Short, IBpmNodeUserCalculation> bpmNodeUserCalculations)
  {
    this.bpmNodeUserCalculations = bpmNodeUserCalculations;
  }

  public IBpmNodeUserCalculation getByKey(Short i)
  {
    return (IBpmNodeUserCalculation)bpmNodeUserCalculations.get(i);
  }

  public Map<Short, String> getNodeUserSetType()
  {
    Map map = new HashMap();
    Set<Map.Entry<Short,IBpmNodeUserCalculation>> entries = bpmNodeUserCalculations.entrySet();
    for (Map.Entry entry : entries) {
      map.put((Short)entry.getKey(), ((IBpmNodeUserCalculation)entry.getValue()).getTitle());
    }
    return map;
  }
}