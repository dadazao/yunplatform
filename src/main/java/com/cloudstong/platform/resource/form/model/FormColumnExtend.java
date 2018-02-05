package com.cloudstong.platform.resource.form.model;

import java.io.Serializable;
import java.util.List;

import com.cloudstong.platform.core.model.Code;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.checkbox.vo.CheckBoxMgtVO;
import com.cloudstong.platform.resource.codecasecade.model.CodeCaseCade;
import com.cloudstong.platform.resource.date.model.DateControl;
import com.cloudstong.platform.resource.editor.model.TextEditor;
import com.cloudstong.platform.resource.radio.vo.RadioMgtVO;
import com.cloudstong.platform.resource.uploadify.model.Uploadify;
import com.cloudstong.platform.resource.useinfo.model.Component;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:表单字段扩展类
 */
public class FormColumnExtend implements Serializable {

	/**
	 * 表单字段
	 */
	private FormColumn formColumn;

	/**
	 * 表单字段值
	 */
	private Object value;
	
	/**
	 * 表单字段显示值
	 */
	private String valueText;

	/**
	 * 表单字段值的类型
	 */
	private String valueType;

	/**
	 * 文本框，下拉框，搜索下拉框,文本域，密码框，上传文件框的父类
	 */
	private Component component;
	
	/**
	 * 单选框
	 */
	private RadioMgtVO radio;

	/**
	 * 复选框
	 */
	private CheckBoxMgtVO checkBox;

	/**
	 * 弹出树
	 */
	private Catalog catalog;

	/**
	 * 文本编辑器组件
	 */
	private TextEditor textEditor;
	
	/**
	 * 日起组件
	 */
	private DateControl date;
	
	/**
	 * 代码级联组件
	 */
	private CodeCaseCade codeCaseCade;
	
	/**
	 * 多文件上传组件
	 */
	private Uploadify uploadify;

	/**
	 * 代码列表
	 */
	private List<Code> codes;
	
	/**
	 * 弹出树的类型
	 */
	private String tableType;

	public List<Code> getCodes() {
		return codes;
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
	}

	public FormColumn getFormColumn() {
		return formColumn;
	}

	public void setFormColumn(FormColumn formColumn) {
		this.formColumn = formColumn;
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

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public RadioMgtVO getRadio() {
		return radio;
	}

	public void setRadio(RadioMgtVO radio) {
		this.radio = radio;
	}

	public CheckBoxMgtVO getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBoxMgtVO checkBox) {
		this.checkBox = checkBox;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public TextEditor getTextEditor() {
		return textEditor;
	}

	public void setTextEditor(TextEditor textEditor) {
		this.textEditor = textEditor;
	}

	public DateControl getDate() {
		return date;
	}

	public void setDate(DateControl date) {
		this.date = date;
	}

	public CodeCaseCade getCodeCaseCade() {
		return codeCaseCade;
	}

	public void setCodeCaseCade(CodeCaseCade codeCaseCade) {
		this.codeCaseCade = codeCaseCade;
	}

	public Uploadify getUploadify() {
		return uploadify;
	}

	public void setUploadify(Uploadify uploadify) {
		this.uploadify = uploadify;
	}

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

}
