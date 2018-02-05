package com.cloudstong.platform.resource.tabulation.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.button.model.Button;
import com.cloudstong.platform.resource.buttongroup.model.ButtonGroup;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表按钮
 */
public class TabulationButton implements Serializable{
	
	/**
	 * 唯一标识
	 */
	private Long id;

	/**
	 * 按钮或按钮组ID
	 */
	private Long buttonId;

	/**
	 * 按钮类型  0 ：表示按钮 1：表示按钮组
	 */
	private String buttonType;

	/**
	 * 所属列表ID
	 */
	private Long listId;

	/**
	 * 所属表单ID
	 */

	private String formId;

	/**
	 * 按钮名称或按钮组名称
	 */
	private String buttonName;
	
	/**
	 * 显示顺序
	 */
	private Integer showOrder;

	/**
	 * 表单名称
	 */
	private String formName;

	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 按钮
	 */
	private Button button;

	/**
	 * 按钮组
	 */
	private ButtonGroup buttonGroup;
	
	/**
	 * 是否有权限  0:有权限 1:没权限
	 */
	private String hasAuth="0";
	
	/**
	 * 功能说明(按钮)
	 */
	
	private String comment;
	
	/**
	 * 列表按钮
	 */
	private String fcomment;
	
	/**
	 * 显示名称
	 */
	private String showName;
	
	/**
	 * 功能方法
	 */
	private String funcName;
	
	/**
	 * 请求URL
	 */
	private String url;
	
	public TabulationButton() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getButtonId() {
		return buttonId;
	}

	public void setButtonId(Long buttonId) {
		this.buttonId = buttonId;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public void setButtonGroup(ButtonGroup buttonGroup) {
		this.buttonGroup = buttonGroup;
	}

	public String getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(String hasAuth) {
		this.hasAuth = hasAuth;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFcomment() {
		return fcomment;
	}

	public void setFcomment(String fcomment) {
		this.fcomment = fcomment;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	
	@Override
	public String toString() {
		return this.id+""+this.buttonId+""+this.listId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
