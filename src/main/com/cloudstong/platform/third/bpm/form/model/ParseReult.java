package com.cloudstong.platform.third.bpm.form.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParseReult {
	private BpmFormTable bpmFormTable = new BpmFormTable();

	private String template = "";

	private List<String> errors = new ArrayList();

	private Map<String, String> opinionMap = new HashMap();

	public void addError(String error) {
		errors.add(error);
	}

	public BpmFormTable getBpmFormTable() {
		return bpmFormTable;
	}

	public void setBpmFormTable(BpmFormTable bpmFormTable) {
		this.bpmFormTable = bpmFormTable;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<String> getErrors() {
		return errors;
	}

	public String getError() {
		String error = "";
		for (Iterator it = errors.iterator(); it.hasNext();) {
			error = error + (String) it.next() + "<br>";
		}
		return error;
	}

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Map<String, String> getOpinionMap() {
		return opinionMap;
	}

	public void setOpinionMap(Map<String, String> opinionMap) {
		this.opinionMap = opinionMap;
	}

	public void addOpinion(String name, String memo) {
		opinionMap.put(name, memo);
	}
}