package com.cloudstong.platform.resource.metadata.model;

import java.util.List;
import java.util.Map;

import com.cloudstong.platform.resource.dictionary.model.Dictionary;

public class ColumnExtend {
	private Column column;

	private Object value;

	private String valueType;

	// 代码list
	private List<Dictionary> codeList;

	// 仅为有树结构的domain使用
	private String parentName;

	public ColumnExtend() {
	}

	public ColumnExtend(Column column, String value) {
		this.column = column;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public List<Dictionary> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<Dictionary> codeList) {
		this.codeList = codeList;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
