package com.cloudstong.platform.resource.querycontrol.model;

/**
 * @author allan
 * Created on 2012-11-16
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:高级查询组件
 */
public class AdvanceQueryControl extends QueryControl {
	
	/**
	 * 每行有几个查询条件
	 */
	private Integer columnNumber;

	public Integer getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	

}
