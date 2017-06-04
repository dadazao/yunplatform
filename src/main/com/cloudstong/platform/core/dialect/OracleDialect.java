package com.cloudstong.platform.core.dialect;

import com.cloudstong.platform.resource.metadata.model.Table;

public class OracleDialect extends Dialect {
	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}

	public String topN(String sql, int n) {
		String tmp = "select * from ( " + sql + ") where rownum <=" + n;
		return tmp;
	}

	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect
					.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= " + endString
					+ " and rownum_ > " + offsetPlaceholder);
		} else {
			pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
		}
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect.toString();
	}
	
	public String getCreateTableString(Table u) {
		StringBuffer createSql = new StringBuffer("create table " + u.getTableName() + "(id NUMBER(20,0) DEFAULT 0,");
		if (u.getHasCommonColumn() == 1) {
			createSql.append("comm_createBy varchar2(50) NULL,comm_createDate timestamp NULL,comm_updateBy varchar2(50) NULL,comm_updateDate timestamp NULL,comm_opt_counter NUMBER(10,0),comm_mark_for_delete NUMBER(10,0),");
		}
		if (u.getTableType().equals("4")) {
			createSql.append("tbl_name varchar2(100) NULL,tbl_parentId varchar2(50) NULL,");
		}
		createSql.append("PRIMARY KEY (id))");
		
		return createSql.toString();
	}
}