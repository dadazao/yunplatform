package com.cloudstong.platform.core.dialect;

public class SQLServerDialect extends Dialect {
	public boolean supportsLimitOffset() {
		return false;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String topN(String sql, int n) {
		return sql + " top " + n;
	}

	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}

	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, null, limit, null);
	}

	public String getLimitString(String querySelect, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		if (offset > 0) {
			throw new UnsupportedOperationException("sql server has no offset");
		}

		return new StringBuffer(querySelect.length() + 8)
				.append(querySelect)
				.insert(getAfterSelectInsertPoint(querySelect), " top " + limit)
				.toString();
	}
}