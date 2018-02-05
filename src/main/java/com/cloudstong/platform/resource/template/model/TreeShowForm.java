package com.cloudstong.platform.resource.template.model;

public class TreeShowForm implements java.io.Serializable {

	// 树结构表名称
	private String model;
	// 树根ID值
	private Long root;
	// ID列
	private String idColumn;
	// 名称列
	private String nameColumn;
	// 父ID列
	private String parentIdColumn;
	// 路径列
	private String pathColumn;
	// 排序列
	private String orderColumn;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getRoot() {
		return root;
	}

	public void setRoot(Long root) {
		this.root = root;
	}

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public String getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}

	public String getParentIdColumn() {
		return parentIdColumn;
	}

	public void setParentIdColumn(String parentIdColumn) {
		this.parentIdColumn = parentIdColumn;
	}

	public String getPathColumn() {
		return pathColumn;
	}

	public void setPathColumn(String pathColumn) {
		this.pathColumn = pathColumn;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
}
