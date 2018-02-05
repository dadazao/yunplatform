package com.cloudstong.platform.core.bpmn20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class ContextFactory
{
  private static Map<String, JAXBContext> contexts = Collections.synchronizedMap(new LinkedHashMap());

  public static JAXBContext newInstance(Class<? extends Object>[] classes) throws JAXBException {
    JAXBContext jAXBContext = null;
    List classeNames = new ArrayList();
    String newKey = "";
    Class[] arrayOfClass = classes; int j = classes.length; for (int i = 0; i < j; i++) { Class cls = arrayOfClass[i];
      newKey = newKey + cls.getName() + ",";
      classeNames.add(cls.getName());
    }
    newKey = newKey.substring(0, newKey.length() - 1);

    for (String key : contexts.keySet()) {
      String[] keyAry = key.split(",");
      List clss = Arrays.asList(keyAry);
      if (clss.equals(clss)) {
        jAXBContext = (JAXBContext)contexts.get(key);
        break;
      }
    }
    if (jAXBContext == null) {
      jAXBContext = JAXBContext.newInstance(classes);
      contexts.put(newKey, jAXBContext);
    }

    return jAXBContext;
  }
}