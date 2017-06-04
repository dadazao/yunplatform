package com.cloudstong.platform.core.dialect;

import org.springframework.beans.factory.FactoryBean;

public class DialectFactoryBean implements FactoryBean<Dialect> {
	private Dialect dialect;
	private String dbType = "mysql";

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public Dialect getObject() throws Exception {
		if (dbType.equals("oracle")) {
			dialect = new OracleDialect();
		} else if (dbType.equals("mssql")) {
			dialect = new SQLServer2005Dialect();
		} else if (dbType.equals("db2")) {
			dialect = new DB2Dialect();
		} else if (dbType.equals("mysql")) {
			dialect = new MySQLDialect();
		} else if (dbType.equals("h2")) {
			dialect = new H2Dialect();
		} else if (dbType.equals("dm")) {
			dialect = new DmDialect();
		} else {
			throw new Exception("没有设置合适的数据库类型");
		}
		return dialect;
	}

	public Class<?> getObjectType() {
		return Dialect.class;
	}

	public boolean isSingleton() {
		return true;
	}
}