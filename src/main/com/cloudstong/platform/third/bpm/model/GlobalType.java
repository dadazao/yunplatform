package com.cloudstong.platform.third.bpm.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cloudstong.platform.core.model.EntityBase;

public class GlobalType extends EntityBase {
	public static final String CAT_FLOW = "FLOW_TYPE";
	public static final String CAT_FORM = "FORM_TYPE";
	public static final String CAT_FILE = "FILE_TYPE";
	public static final String CAT_ATTACH = "ATTACH_TYPE";
	public static final String CAT_DIC = "DIC";
	public static final String CAT_FILE_FORMAT = "FILEFORMAT";
	public static final String CAT_REPORT = "REPORT_TYPE";
	public static final String NODE_KEY_DIC = "DIC";
	public static final String TYPE_NAME_BPM = "流程分类";
	public static final String TYPE_NAME_DIC = "数据字典";
	public static final Integer DATA_TYPE_TREE = Integer.valueOf(1);

	public static final Integer DATA_TYPE_FLAT = Integer.valueOf(0);
	public static final long ROOT_PID = -1L;
	public static final long ROOT_ID = 0L;
	public static final long ROOT_DEPTH = 0L;
	public static final String IS_PARENT_N = "false";
	public static final String IS_PARENT_Y = "true";
	public static final int IS_LEAF_N = 0;
	public static final int IS_LEAF_Y = 1;
	public static final String NODE_CODE_TYPE_AUTO_N = "0";
	public static final String NODE_CODE_TYPE_AUTO_Y = "1";
	protected Long typeId = Long.valueOf(0L);
	protected String typeName;
	protected String nodePath;
	protected Integer depth;
	protected Long parentId;
	protected String nodeKey;
	protected String catKey;
	protected Long sn;
	protected Long userId = Long.valueOf(0L);

	protected Long depId = Long.valueOf(0L);

	protected Integer type = Integer.valueOf(0);

	protected String open = "true";
	protected String isParent;
	protected Integer isLeaf;
	protected int childNodes = 0;
	protected String nodeCode;
	protected Short nodeCodeType = Short.valueOf((short) 0);

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getIsParent() {
		return childNodes > 0 ? "true" : "false";
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getDepth() {
		int i = nodePath.split("\\.").length - 1;
		return Integer.valueOf(i);
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	public String getCatKey() {
		return catKey;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getSn() {
		return sn;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public Long getDepId() {
		return depId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public int getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(int childNodes) {
		this.childNodes = childNodes;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public Short getNodeCodeType() {
		return nodeCodeType;
	}

	public void setNodeCodeType(Short nodeCodeType) {
		this.nodeCodeType = nodeCodeType;
	}

	public boolean equals(Object object) {
		if (!(object instanceof GlobalType)) {
			return false;
		}
		GlobalType rhs = (GlobalType) object;
		return new EqualsBuilder().append(typeId, rhs.typeId).append(typeName, rhs.typeName).append(nodePath, rhs.nodePath).append(depth, rhs.depth)
				.append(parentId, rhs.parentId).append(nodeKey, rhs.nodeKey).append(catKey, rhs.catKey).append(sn, rhs.sn).append(userId, rhs.userId)
				.append(depId, rhs.depId).append(type, rhs.type).append(nodeCode, rhs.nodeCode).append(nodeCodeType, rhs.nodeCodeType).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(typeId).append(typeName).append(nodePath).append(depth).append(parentId)
				.append(nodeKey).append(catKey).append(sn).append(userId).append(depId).append(type).append(nodeCode).append(nodeCodeType)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("typeId", typeId).append("typeName", typeName).append("nodePath", nodePath).append("depth", depth)
				.append("parentId", parentId).append("nodeKey", nodeKey).append("catKey", catKey).append("sn", sn).append("userId", userId)
				.append("depId", depId).append("type", type).append("nodeCode", nodeCode).append("nodeCodeType", nodeCodeType).toString();
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub need to be implemented.
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub need to be implemented.
		
	}
}