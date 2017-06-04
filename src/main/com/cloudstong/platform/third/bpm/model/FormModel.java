package com.cloudstong.platform.third.bpm.model;

import com.cloudstong.platform.core.util.StringUtil;

public class FormModel {
	private int formType = -1;

	private String formHtml = "";

	private String detailUrl = "";

	private String formUrl = "";

	public int getFormType() {
		return formType;
	}

	public void setFormType(int formType) {
		this.formType = formType;
	}

	public String getFormHtml() {
		return formHtml;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
		formType = 0;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
		if (StringUtil.isNotEmpty(detailUrl))
			formType = 2;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public boolean isFormEmpty() {
		boolean rtn = false;
		switch (formType) {
		case -1:
			rtn = true;
			break;
		case 0:
			rtn = StringUtil.isEmpty(formHtml);
			break;
		case 1:
			rtn = StringUtil.isEmpty(formUrl);
		case 2:
			rtn = StringUtil.isEmpty(formUrl);
		}

		return rtn;
	}
}