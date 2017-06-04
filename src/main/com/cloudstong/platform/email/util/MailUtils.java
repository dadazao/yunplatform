/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Jason
 * 
 *         Created on 2014-9-30
 * 
 *         Description:
 * 
 */
public abstract class MailUtils {

	private static final Pattern PATTERN = Pattern
			.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

	/**
	 * 
	 * Description: 用于判断传入的参数是否是正确的Email格式
	 * 
	 * Steps:
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		return PATTERN.matcher(email).matches();
	}

	/**
	 * 
	 * Description: 将传入的集合数据进行过滤，返回符合要求的Email字符串集合，集合中重复的将只保留一个
	 * 
	 * @param emailCollection
	 * @return
	 */
	public static Set<String> filtEmails(Collection<String> emailCollection) {
		if (emailCollection == null) {
			return Collections.emptySet();
		}
		Set<String> resultSet = new HashSet<String>(emailCollection.size());
		for (String email : emailCollection) {
			if (isEmail(email)) {
				resultSet.add(email);
			}
		}
		return resultSet;
	}
}
