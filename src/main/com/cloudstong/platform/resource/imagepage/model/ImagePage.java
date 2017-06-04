package com.cloudstong.platform.resource.imagepage.model;

import java.io.Serializable;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author michael
 * Created on 2012-11-15
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 图片页面
 */
public class ImagePage extends EntityBase implements Serializable {

	private static final long serialVersionUID = 2882791193753050091L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 图片
	 */
	private String picName;

	/**
	 * 功能说明
	 */
	private String comment;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 路径
	 */
	private String imagePagePath;

	public ImagePage() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImagePagePath() {
		return imagePagePath;
	}

	public void setImagePagePath(String imagePagePath) {
		this.imagePagePath = imagePagePath;
	}
}
