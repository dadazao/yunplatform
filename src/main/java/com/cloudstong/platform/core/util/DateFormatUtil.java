package com.cloudstong.platform.core.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatUtil {
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final DateFormat DATETIME_NOSECOND_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	public static final DateFormat TIME_NOSECOND_FORMAT = new SimpleDateFormat("HH:mm");

	public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static java.util.Date parse(String dateString) throws ParseException {
		if ((dateString.trim().indexOf(" ") > 0) && (dateString.trim().indexOf(".") > 0))
			return new Timestamp(TIMESTAMP_FORMAT.parse(dateString).getTime());
		if (dateString.trim().indexOf(" ") > 0) {
			if (dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":")) {
				return new Timestamp(DATETIME_FORMAT.parse(dateString).getTime());
			}
			return new Timestamp(DATETIME_NOSECOND_FORMAT.parse(dateString).getTime());
		}
		if (dateString.indexOf(":") > 0) {
			if (dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":")) {
				return new Time(TIME_FORMAT.parse(dateString).getTime());
			}
			return new Time(TIME_NOSECOND_FORMAT.parse(dateString).getTime());
		}

		return new java.sql.Date(DATE_FORMAT.parse(dateString).getTime());
	}

	public static String format(java.util.Date date) {
		if ((date instanceof Timestamp))
			return TIMESTAMP_FORMAT.format(date);
		if ((date instanceof Time))
			return TIME_FORMAT.format(date);
		if ((date instanceof java.sql.Date)) {
			return DATE_FORMAT.format(date);
		}
		return DATETIME_FORMAT.format(date);
	}

	public static java.util.Date parse(String dateString, String style) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(style);
		return dateFormat.parse(dateString);
	}

	public static String format(java.util.Date date, String style) {
		DateFormat dateFormat = new SimpleDateFormat(style);
		return dateFormat.format(date);
	}

	public static java.util.Date parseDate(String dateString) throws ParseException {
		return DATE_FORMAT.parse(dateString);
	}

	public static String formatDate(java.util.Date date) {
		return DATE_FORMAT.format(date);
	}

	public static java.util.Date parseDateTime(String dateString) throws ParseException {
		return DATETIME_FORMAT.parse(dateString);
	}

	public static String formaDatetTime(java.util.Date date) {
		return DATETIME_FORMAT.format(date);
	}

	public static String formatTimeNoSecond(java.util.Date date) {
		return DATETIME_NOSECOND_FORMAT.format(date);
	}

	public static java.util.Date parseTimeNoSecond(String dateString) throws ParseException {
		return DATETIME_NOSECOND_FORMAT.parse(dateString);
	}

	public static String getNowByString(String style) {
		if ((style == null) || ("".equals(style))) {
			style = "yyyy-MM-dd HH:mm:ss";
		}
		return format(new java.util.Date(), style);
	}
}