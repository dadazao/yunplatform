package com.cloudstong.platform.resource.attachment.model;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:附件
 */
public class Attachment extends EntityBase {
	
	private static final long serialVersionUID = 474150141135952350L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 文件类型
	 */
	private String fileExt;
	
	/**
	 * 文件大小
	 */
	private String fileSize;
	
	/**
	 * 所属表
	 */
	private String model;
	
	/**
	 * 所属字段
	 */
	private String column;
	
	/**
	 * 所属对象
	 */
	private String recordColumnValue;
	
	/**
	 * 所属对象字段
	 */
	private String recordColumnName;
	
	/**
	 * 所属对象记录id
	 */
	private String recordId;
	
	/**
	 * 目录ID
	 */
	private String catalogId;

	public Attachment() {
		super();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getRecordColumnValue() {
		return recordColumnValue;
	}

	public void setRecordColumnValue(String recordColumnValue) {
		this.recordColumnValue = recordColumnValue;
	}

	public String getRecordColumnName() {
		return recordColumnName;
	}

	public void setRecordColumnName(String recordColumnName) {
		this.recordColumnName = recordColumnName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
}
