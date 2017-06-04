package com.cloudstong.platform.core.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlMapAdapter extends XmlAdapter<XmlMapConvertor, Map<String, Object>> {
	public XmlMapConvertor marshal(Map<String, Object> map) throws Exception {
		XmlMapConvertor convertor = new XmlMapConvertor();
		for (Map.Entry entry : map.entrySet()) {
			XmlMapConvertor.MapEntry e = new XmlMapConvertor.MapEntry(entry);
			convertor.addEntry(e);
		}
		return convertor;
	}

	public Map<String, Object> unmarshal(XmlMapConvertor map) throws Exception {
		Map result = new HashMap();
		for (XmlMapConvertor.MapEntry e : map.getEntries()) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}
}