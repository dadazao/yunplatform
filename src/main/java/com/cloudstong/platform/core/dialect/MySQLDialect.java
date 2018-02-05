package com.cloudstong.platform.core.dialect;

import com.cloudstong.platform.resource.metadata.model.Table;

public class MySQLDialect extends Dialect {
	public boolean supportsLimitOffset() {
		return true;
	}

	public boolean supportsLimit() {
		return true;
	}
	
	public String topN(String sql, int n) {
		return sql + " limit " + n;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		if (offset > 0) {
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		}

		return sql + " limit " + limitPlaceholder;
	}
	
	public String getCreateTableString(Table u) {
		StringBuffer createSql = new StringBuffer("create table " + u.getTableName() + "(id bigint(20) NOT NULL,");
		if (u.getHasCommonColumn() == 1) {
			createSql.append("comm_createBy varchar(50) NULL,comm_createDate timestamp NULL,comm_updateBy varchar(50) NULL,comm_updateDate timestamp NULL,comm_opt_counter int(10) default 0,comm_mark_for_delete int(1) default 0,");
		}
		if (u.getTableType().equals("4")) {
			createSql.append("tbl_name varchar(100) NULL,tbl_parentId varchar(50) NULL,");
		}
		createSql.append("PRIMARY KEY (`id`))");
		
		return createSql.toString();
	}
}