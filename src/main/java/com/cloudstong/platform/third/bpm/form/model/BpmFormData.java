package com.cloudstong.platform.third.bpm.form.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BpmFormData {
	private long tableId = 0L;

	private String tableName = "";
	private PkValue pkValue;
	private List<SubTable> subTableList = new ArrayList();

	private Map<String, Object> mainFields = new HashMap();

	private Map<String, String> options = new HashMap();

	private Map<String, Object> variables = new HashMap();

	public PkValue getPkValue() {
		return pkValue;
	}

	public void setPkValue(PkValue pkValue) {
		this.pkValue = pkValue;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<SubTable> getSubTableList() {
		return subTableList;
	}

	public Map<String, SubTable> getSubTableMap() {
		Map map = new HashMap();
		for (Iterator it = subTableList.iterator(); it.hasNext();) {
			SubTable tb = (SubTable) it.next();
			map.put(tb.getTableName().toLowerCase(), tb);
		}
		return map;
	}

	public void setSubTableList(List<SubTable> subTableList) {
		this.subTableList = subTableList;
	}

	public Map<String, Object> getMainFields() {
		return mainFields;
	}

	public void setMainFields(Map<String, Object> mainFields) {
		this.mainFields = mainFields;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public void addOpinion(String formName, String value) {
		options.put(formName, value);
	}

	public void addSubTable(SubTable table) {
		subTableList.add(table);
	}

	public void addMainFields(String key, Object obj) {
		mainFields.put(key, obj);
	}

	public void addMainFields(Map<String, Object> map) {
		mainFields.putAll(map);
	}

	public Map<String, Object> getMainCommonFields() {
		Map map = new HashMap();
		map.putAll(mainFields);
		map.remove(pkValue.getName());
		return map;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}