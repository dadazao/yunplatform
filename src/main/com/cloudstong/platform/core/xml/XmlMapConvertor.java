package com.cloudstong.platform.core.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "XmlMapConvertor")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMapConvertor {
	private List<MapEntry> entries = new ArrayList();

	public void addEntry(MapEntry entry) {
		entries.add(entry);
	}

	public List<MapEntry> getEntries() {
		return entries;
	}

	public static class MapEntry {
		private String key;
		private Object value;

		public MapEntry() {
		}

		public MapEntry(Map.Entry<String, Object> entry) {
			key = ((String) entry.getKey());
			value = entry.getValue();
		}

		public MapEntry(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}