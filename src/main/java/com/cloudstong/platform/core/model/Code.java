package com.cloudstong.platform.core.model;

import java.io.Serializable;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:代码模型
 */
public class Code implements Serializable {

	/**
	 * 唯一标识
	 */
	private Long id;

	/**
	 * 显示名称
	 */
	private String text;

	/**
	 * 值
	 */
	private String value;

	public Code() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Code(Long id, String text, String value) {
		this.id = id;
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
