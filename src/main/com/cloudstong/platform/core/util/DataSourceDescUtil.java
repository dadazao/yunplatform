package com.cloudstong.platform.core.util;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceDescUtil {

	private static BasicDataSource instance;

	public static BasicDataSource getDataSource() throws IOException {
		if (instance == null) {
			createDataSource();
		}
		return instance;
	}

	public static void shutdownDataSource(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		bds.close();
	}

	public static BasicDataSource refreshDataSource(String psUrl,
			String psClassName, String user, String pwd) throws IOException {
		createDataSource(psUrl, psClassName, user, pwd);
		return instance;
	}

	private static void createDataSource() throws IOException {
		instance = new BasicDataSource();
		String path = PathUtil.getWebInfPath()+"/WEB-INF/classes/global.properties";
		SystemProperty sp = new SystemProperty(path);
		instance.setUrl(sp.readValue("jdbc.url"));
		instance.setDriverClassName(sp.readValue("jdbc.driverClassName"));
		instance.setUsername(sp.readValue("jdbc.username"));
		instance.setPassword(sp.readValue("jdbc.password"));

		String initialSize = sp.readValue("dataSource.initialSize");
		String minIdle = sp.readValue("dataSource.minIdle");
		String maxIdle = sp.readValue("dataSource.maxIdle");
		String maxWait = sp.readValue("dataSource.maxWait");
		String maxActive = sp.readValue("dataSource.maxActive");
		String removeAbandonedTimeout = sp
				.readValue("dataSource.removeAbandonedTimeout");
		// 是否在自动回收超时连接的时候打印连接的超时错误
		boolean logAbandoned = (Boolean.valueOf(sp
				.readValue("dataSource.logAbandoned"))).booleanValue();
		// 是否自动回收超时连接
		boolean removeAbandoned = (Boolean.valueOf(sp
				.readValue("dataSource.removeAbandoned"))).booleanValue();

		boolean testWhileIdle = (Boolean.valueOf(sp
				.readValue("dataSource.testWhileIdle"))).booleanValue();
		boolean testOnBorrow = (Boolean.valueOf(sp
				.readValue("dataSource.testOnBorrow"))).booleanValue();
		boolean testOnReturn = (Boolean.valueOf(sp
				.readValue("dataSource.testOnReturn"))).booleanValue();
		String validationQuery = sp.readValue("dataSource.validationQuery");

		// 初始化连接数
		if (initialSize != null)
			instance.setInitialSize(Integer.parseInt(initialSize));

		// 最小空闲连接
		if (minIdle != null)
			instance.setMinIdle(Integer.parseInt(minIdle));
		// 最大空闲连接
		if (maxIdle != null)
			instance.setMaxIdle(Integer.parseInt(maxIdle));

		// 超时回收时间(以毫秒为单位)
		if (maxWait != null)
			instance.setMaxWait(Long.parseLong(maxWait));

		// 最大连接数
		if (maxActive != null) {
			if (!maxActive.trim().equals("0"))
				instance.setMaxActive(Integer.parseInt(maxActive));
		}
		if (removeAbandonedTimeout != null) {
			instance.setRemoveAbandonedTimeout(Integer
					.parseInt(removeAbandonedTimeout));
		}
		instance.setLogAbandoned(logAbandoned);
		instance.setRemoveAbandoned(removeAbandoned);

		instance.setTestWhileIdle(testWhileIdle);
		instance.setTestOnBorrow(testOnBorrow);
		instance.setTestOnReturn(testOnReturn);
		instance.setValidationQuery(validationQuery);
	}

	public static BasicDataSource createDataSource(String psUrl,
			String psClassName, String user, String pwd) throws IOException {
		instance = new BasicDataSource();
		instance.setUrl(psUrl);
		instance.setDriverClassName(psClassName);
		instance.setUsername(user);
		instance.setPassword(pwd);
		return instance;
	}
}
