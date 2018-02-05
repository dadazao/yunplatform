package com.cloudstong.platform.resource.catalog.model;

import java.util.Set;

import com.cloudstong.platform.core.model.EntityBase;

/**
 * @author Allan Created on 2012-11-14
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description: 目录
 */
public class Catalog extends EntityBase implements java.io.Serializable {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 目录名称
	 */
	private String name;

	/**
	 * 别名 (唯一标识)
	 */
	private String alias;

	/**
	 * 目录编码
	 */
	private String code;

	/**
	 * 父目录ID
	 */
	private Long parentId;

	/**
	 * 父目录名称
	 */
	private String parentName;

	/**
	 * 关联路径
	 */
	private String path;

	/**
	 * 显示顺序
	 */
	private Integer orderNum;

	/**
	 * 删除标识
	 */
	private Integer status = 0;

	/**
	 * 父目录
	 */
	private Catalog parent;

	/**
	 * 子目录集合
	 */
	private Set<Catalog> children;

	/**
	 * 所属分组
	 */
	private String systemTeam;

	/**
	 * 目录关联的列表ID
	 */
	private Long tabulationId;

	/**
	 * 规则
	 */
	private String rule;

	/**
	 * 文件夹标示
	 */
	private String packageFolder;

	/**
	 * 功能说明
	 */
	private String comment;
	
	/**
	 * 是否赋权
	 */
	private Integer isAuth=0;

	public Catalog() {
	}

	public Catalog(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Set<Catalog> getChildren() {
		return children;
	}

	public void setChildren(Set<Catalog> children) {
		this.children = children;
	}

	public Catalog getParent() {
		return parent;
	}

	public void setParent(Catalog parent) {
		this.parent = parent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSystemTeam() {
		return systemTeam;
	}

	public void setSystemTeam(String systemTeam) {
		this.systemTeam = systemTeam;
	}

	public Long getTabulationId() {
		return tabulationId;
	}

	public void setTabulationId(Long tabulationId) {
		this.tabulationId = tabulationId;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getPackageFolder() {
		return packageFolder;
	}

	public void setPackageFolder(String packageFolder) {
		this.packageFolder = packageFolder;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}
	
	
	
	
}
