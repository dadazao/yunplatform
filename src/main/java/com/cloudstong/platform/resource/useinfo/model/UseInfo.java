package com.cloudstong.platform.resource.useinfo.model;

import java.io.Serializable;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author michael
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:使用信息
 */
public class UseInfo extends EntityBase implements Serializable {

	private static final long serialVersionUID = 928930349097631667L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 业务数据ID
	 */
	private Long bizId;
	
	/**
	 * 组件ID
	 */
	private Long compId;
	
	/**
	 * 业务名称
	 */
	private String bizName;
	
	/**
	 * 组件名称
	 */
	private String compName;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 类型  1:表单元素   2:表单按钮  3：列表按钮 4:列表操作按钮
	 */
	private Integer typeId;
	
	/**
	 * 类型显示名称
	 */
	private String type;
	
	/**
	 * 关联信息ID
	 */
	private String relationId;
	
	public UseInfo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public Long getBizId() {
		return bizId;
	}

	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

}
