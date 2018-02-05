package com.cloudstong.platform.resource.tabulation.model;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表字段的扩展类
 */
public class TabulationColumnExtend {

	/**
	 * 列表字段
	 */
	private TabulationColumn tabulationColumn;

	/**
	 * 字段值
	 */
	private Object value;

	/**
	 * 字段值的类型
	 */
	private String valueType;

	public TabulationColumn getTabulationColumn() {
		return tabulationColumn;
	}

	public void setTabulationColumn(TabulationColumn tabulationColumn) {
		this.tabulationColumn = tabulationColumn;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}
