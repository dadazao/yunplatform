package com.cloudstong.platform.third.bpm.form.model;

import org.jsoup.nodes.Element;

public class Pair {
	private Element el;
	private String fieldName = "";

	public Pair(Element el, String fieldName) {
		this.el = el;
		this.fieldName = fieldName;
	}

	public Element getEl() {
		return el;
	}

	public void setEl(Element el) {
		this.el = el;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}