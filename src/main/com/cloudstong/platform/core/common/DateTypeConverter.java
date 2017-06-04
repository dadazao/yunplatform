package com.cloudstong.platform.core.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:日期类型转化类
 */
public class DateTypeConverter extends StrutsTypeConverter {

	private static final String FORMATTIME = "yyyy-MM-dd HH:mm:ss";

	private static final DateFormat[] ACCEPT_DATE_HMS_FORMATS = {
			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
			new SimpleDateFormat("yyyyMMdd HH:mm:ss") }; // 支持转换的日期加时间格式

	private static final DateFormat[] ACCEPT_DATE_FORMATS = {
			new SimpleDateFormat("dd/MM/yyyy"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy/MM/dd"),
			new SimpleDateFormat("yyyyMMdd") }; // 支持转换的日期格式

	/* 
	 * 从字符串转化为日期对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		// 时分秒先转换
		Date date = null;
		String dateString = values[0];
		if (dateString != null) {
			for (DateFormat format : ACCEPT_DATE_HMS_FORMATS) {
				try {
					return format.parse(dateString);// 遍历日期支持格式，进行转换
				} catch (Exception e) {
					continue;
				}
			}
			if (date == null) {
				for (DateFormat format : ACCEPT_DATE_FORMATS) {
					try {
						return format.parse(dateString);// 遍历日期支持格式，进行转换
					} catch (Exception e) {
						continue;
					}
				}
			}
		}
		return date;
	}

	/* 
	 * 将日期对象转化为字符串
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map context, Object o) {
		if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMATTIME);
			return sdf.format((Date) o);
		}
		return "";
	}

}
