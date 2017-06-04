package com.cloudstong.platform.core.model;

import java.util.Set;

/**
 * 树节点
 * @author Allan
 * 
 */
public class TreeNode {

	/**
	 * 唯一ID
	 */
	private Long id;
	/**
	 * 树节点名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 树节点值
	 */
	private String value;
	/**
	 * 是否是叶子节点，0代表不是，1代表是
	 */
	private Integer leaf;
	/**
	 * 级数
	 */
	private Integer level;
	/**
	 * 父节点ID
	 */
	private Long parentId;
	/**
	 * 父节点
	 */
	private TreeNode parent;
	/**
	 * 子节点
	 */
	private Set<TreeNode> children;

	/**
	 * 字体
	 */
	private String fontFamily;
	
	/**
	 * 字号 
	 */
	private Integer fontSize;
	
	/**
	 * 颜色
	 */
	private String color;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public Set<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}