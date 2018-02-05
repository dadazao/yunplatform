package com.cloudstong.platform.core.util;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanDateConnverter implements Converter {
	private static final Logger logger = LoggerFactory.getLogger(BeanDateConnverter.class);
	public static final String[] ACCEPT_DATE_FORMATS = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };

	public Object convert(Class arg0, Object value) {
		logger.debug("conver " + value + " to date object");
		if (value == null)
			return null;
		String dateStr = value.toString();
		dateStr = dateStr.replace("T", " ");
		try {
			return DateUtils.parseDate(dateStr, ACCEPT_DATE_FORMATS);
		} catch (Exception ex) {
			logger.debug("parse date error:" + ex.getMessage());
		}
		return null;
	}
}