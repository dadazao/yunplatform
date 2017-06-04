/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.test.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Jason
 * 
 *         Created on 2013-9-16
 * 
 *         Description:
 * 
 */
public class ColumnsGeneratorUtil {

	public static void main(String[] args) throws ClassNotFoundException {
		Class cls = Class.forName("com.cloudstong.platform.email.model.MailAccount");
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
		}

		// Class pcls = cls.getSuperclass();
		// Field[] pfields = pcls.getDeclaredFields();
		// for (Field field : pfields) {
		// System.out.println(field.getName());
		// }

		System.out.println(fields.length);
	}

	public static String generate(Class<?> cls) {
		String tableName = getTableName(cls);
		StringBuilder sql = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName).append(";");
		sql.append("CREATE TABLE ")
				.append(tableName)
				.append(" ( id varchar(50) NOT NULL,comm_createBy varchar(50) NULL,comm_createDate timestamp NULL,comm_updateBy varchar(50) NULL,comm_updateDate timestamp NULL,comm_opt_counter int(10) default 0,comm_mark_for_delete int(1) default 0,");
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if ("id".equals(field.getName())) {
				continue;
			}
			sql.append(wrapField(field)).append(",");
		}
		return sql.toString();
	}

	private static Object wrapField(Field field) {
		String fieldName = "tbl_" + field.getName().toLowerCase(Locale.ENGLISH);
		String fieldType = field.getType().getSimpleName();
//		String sql = fieldName 
		return null;
	}

	private static String getTableName(Class<?> cls) {
		return "sys_" + cls.getSimpleName().toLowerCase(Locale.ENGLISH);
	}
}
