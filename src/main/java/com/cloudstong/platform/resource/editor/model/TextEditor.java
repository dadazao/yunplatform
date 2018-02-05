package com.cloudstong.platform.resource.editor.model;

import java.io.Serializable;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 文本编辑器
 */
public class TextEditor implements Serializable {

	private static final long serialVersionUID = 967054222246865539L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 编辑器名称
	 */
	private String textEditorName;
	/**
	 * 是否启用 1:启用 0:不启用
	 */
	private Integer enabled = 1;
	/**
	 * 行数
	 */
	private Integer rows;
	/**
	 * 列数
	 */
	private Integer cols;
	/**
	 * 编辑器标识
	 */
	private String editorId;
	/**
	 * 说明
	 */
	private String remark;
	/**
	 * 备注
	 */
	private String beizhu;
	/**
	 * 创建人
	 */
	private String createBy = "-1";
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 修改人
	 */
	private String updateBy = "-1";
	/**
	 * 修改日期
	 */
	private java.util.Date updateDate;

	public TextEditor() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTextEditorName() {
		return textEditorName;
	}

	public void setTextEditorName(String textEditorName) {
		this.textEditorName = textEditorName;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

}
