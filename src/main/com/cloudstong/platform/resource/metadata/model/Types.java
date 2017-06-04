package com.cloudstong.platform.resource.metadata.model;

import com.cloudstong.platform.core.model.EntityBase;

public class Types extends EntityBase {
	private Long id;
	private String tblFlbianma;
	private Integer tblFlusecount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTblFlbianma() {
		return tblFlbianma;
	}

	public void setTblFlbianma(String tblFlbianma) {
		this.tblFlbianma = tblFlbianma;
	}

	public Integer getTblFlusecount() {
		return tblFlusecount;
	}

	public void setTblFlusecount(Integer tblFlusecount) {
		this.tblFlusecount = tblFlusecount;
	}
}
