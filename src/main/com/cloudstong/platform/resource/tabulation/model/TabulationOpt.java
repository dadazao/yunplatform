package com.cloudstong.platform.resource.tabulation.model;

import java.io.Serializable;

import com.cloudstong.platform.resource.button.model.Button;

/**
 * @author michael
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:列表操作按钮
 */
public class TabulationOpt implements Serializable {

	private static final long serialVersionUID = -1807645454707255263L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 顺序
	 */
	private Integer order;

	/**
	 * 操作按钮
	 */
	private Long buttonId;

	/**
	 * 列表ID
	 */
	private Long tabulationId;
	
	/**
	 * 是否有权限  0:有权限 1:没权限
	 */
	private String hasAuth = "0";
	
	/**
	 * 功能说明(按钮)
	 */
	private String comment;
	
	/**
	 * 功能说明(列表操作按钮)
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
	 * 请求访问地址
	 */
	private String url;
	
	private String buttonName;
	
	private Button button;

	public TabulationOpt() {
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

	public Long getTabulationId() {
		return tabulationId;
	}

	public void setTabulationId(Long tabulationId) {
		this.tabulationId = tabulationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(String hasAuth) {
		this.hasAuth = hasAuth;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
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
		return this.id+""+this.buttonId+""+this.tabulationId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	

}
