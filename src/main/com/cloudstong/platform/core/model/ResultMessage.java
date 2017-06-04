package com.cloudstong.platform.core.model;

import org.activiti.engine.impl.util.json.JSONStringer;

public class ResultMessage {
	public static final int Success = 1;
	public static final int Fail = 0;
	private int result = 1;
	private String message = "";

	public ResultMessage() {
	}

	public ResultMessage(int result, String message) {
		this.result = result;
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		JSONStringer stringer = new JSONStringer();
		stringer.object();
		stringer.key("result");
		stringer.value(result);
		stringer.key("message");
		stringer.value(message);
		stringer.endObject();
		return stringer.toString();
	}
}