package com.cloudstong.platform.core.model;

import java.io.Serializable;
import java.util.List;

import com.cloudstong.platform.resource.form.model.FormColumnExtend;
import com.cloudstong.platform.resource.metadata.model.ColumnExtend;
import com.cloudstong.platform.resource.metadata.model.Table;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:通用数据模型
 */
public class Domain implements Serializable{

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 表属性
	 */
	private Table table;

	/**
	 * 所有字段
	 */
	private List<ColumnExtend> columnExtends;

	/**
	 * 表单字段
	 */
	private List<FormColumnExtend> formColumnExtends;

	/**
	 * 列表字段
	 */
	private List<FormColumnExtend> tabulationColumnExtends;

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public List<ColumnExtend> getColumnExtends() {
		return columnExtends;
	}

	public void setColumnExtends(List<ColumnExtend> columnExtends) {
		this.columnExtends = columnExtends;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<FormColumnExtend> getFormColumnExtends() {
		return formColumnExtends;
	}

	public void setFormColumnExtends(List<FormColumnExtend> formColumnExtends) {
		this.formColumnExtends = formColumnExtends;
	}

	public List<FormColumnExtend> getTabulationColumnExtends() {
		return tabulationColumnExtends;
	}

	public void setTabulationColumnExtends(
			List<FormColumnExtend> listColumnExtends) {
		this.tabulationColumnExtends = listColumnExtends;
	}

}
