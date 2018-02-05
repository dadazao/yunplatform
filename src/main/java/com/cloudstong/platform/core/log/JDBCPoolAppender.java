/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.log;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.jdbc.JDBCAppender;

import com.cloudstong.platform.core.util.AppUtil;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-23
 * 
 *         Description:
 * 
 */
public class JDBCPoolAppender extends JDBCAppender {

	public JDBCPoolAppender() {
		super();
	}

	@Override
	protected Connection getConnection() throws SQLException {
		Connection connection = null;
		DataSource dataSource = (DataSource)AppUtil.getBean("dataSource");
		connection =  dataSource.getConnection();
		return connection;
	}

}
