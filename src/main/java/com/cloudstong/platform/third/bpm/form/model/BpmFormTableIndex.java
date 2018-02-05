package com.cloudstong.platform.third.bpm.form.model;

import java.util.List;

import com.cloudstong.platform.core.model.EntityBase;

public class BpmFormTableIndex extends EntityBase implements Cloneable {
	private static final long serialVersionUID = 1L;
	public static String INDEX_TYPE_BITMAP = "BITMAP";
	public static String INDEX_TYPE_BTREE = "BTREE";
	public static String INDEX_TYPE_FUNCTION = "FUNCTION";
	public static String INDEX_TYPE_HEAP = "HEAP";
	public static String INDEX_TYPE_CLUSTERED = "CLUSTERED";
	public static String INDEX_TYPE_NONCLUSTERED = "NONCLUSTERED";
	public static String INDEX_TYPE_XML = "XML";
	public static String INDEX_TYPE_SPATIAL = "SPATIAL";
	public static String INDEX_TYPE_REG = "REGULAR";
	public static String INDEX_TYPE_DIM = "DIMENSIONBLOCK";
	public static String INDEX_TYPE_BLOK = "BLOCK";

	public static String TABLE_TYPE_TABLE = "TABLE";
	public static String TABLE_TYPE_VIEW = "VIEW";

	public static String INDEX_STATUS_VALIDATE = "VALIDATE";
	public static String INDEX_STATUS_INVALIDATE = "INVALIDATE";
	private String indexTable;
	private String tableType;
	private String indexName;
	private String indexType;
	private String indexStatus;
	private List<String> indexFields;
	private boolean unique;
	private String indexDdl;
	private String indexComment;
	private boolean pkIndex;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public List<String> getIndexFields() {
		return indexFields;
	}

	public void setIndexFields(List<String> indexFields) {
		this.indexFields = indexFields;
	}

	public String getIndexComment() {
		return indexComment;
	}

	public void setIndexComment(String indexComment) {
		this.indexComment = indexComment;
	}

	public String getIndexTable() {
		return indexTable;
	}

	public void setIndexTable(String indexTable) {
		this.indexTable = indexTable;
	}

	public String getIndexStatus() {
		return indexStatus;
	}

	public void setIndexStatus(String indexStatus) {
		this.indexStatus = indexStatus;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getIndexDdl() {
		return indexDdl;
	}

	public void setIndexDdl(String indexDdl) {
		this.indexDdl = indexDdl;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isPkIndex() {
		return pkIndex;
	}

	public void setPkIndex(boolean pkIndex) {
		this.pkIndex = pkIndex;
	}

	public String toString() {
		return "BpmFormTableIndex [indexTable=" + indexTable + ", tableType=" + tableType + ", indexName=" + indexName + ", indexType=" + indexType
				+ ", indexStatus=" + indexStatus + ", indexFields=" + indexFields + ", unique=" + unique + ", indexDdl=" + indexDdl
				+ ", indexComment=" + indexComment + ", pkIndex=" + pkIndex + "]";
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