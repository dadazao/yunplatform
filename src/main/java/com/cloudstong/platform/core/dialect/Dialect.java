package com.cloudstong.platform.core.dialect;

import com.cloudstong.platform.resource.metadata.model.Table;

public class Dialect {
	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return supportsLimit();
	}

	public String getCountSql(String sql) {
		return "select count(1) amount from (" + sql + ")  a";
	}

	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException("paged queries not supported");
	}
	
	public String getCreateTableString(Table u) {
		throw new UnsupportedOperationException("create table not supported");
	}
	public String topN(String sql, int n) {
		throw new UnsupportedOperationException("not select top N");
	}
}