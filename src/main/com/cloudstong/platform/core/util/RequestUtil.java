package com.cloudstong.platform.core.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {
	private static ThreadLocal<HttpServletRequest> reqLocal = new ThreadLocal();

	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal();

	private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	public static void setHttpServletRequest(HttpServletRequest request) {
		reqLocal.set(request);
	}

	public static void clearHttpReqResponse() {
		reqLocal.remove();
		responseLocal.remove();
	}

	public static void setHttpServletResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) reqLocal.get();
	}

	public static HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static String getString(HttpServletRequest request, String key, String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return value.replace("'", "''").trim();
	}

	public static String getString(HttpServletRequest request, String key) {
		return getString(request, key, "");
	}

	public static String getSecureString(HttpServletRequest request, String key, String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return filterInject(value);
	}

	public static String getSecureString(HttpServletRequest request, String key) {
		return getSecureString(request, key, "");
	}

	public static String filterInject(String str) {
		String injectstr = "\\||;|exec|insert|select|delete|update|count|chr|truncate|char";
		Pattern regex = Pattern.compile(injectstr, 226);

		Matcher matcher = regex.matcher(str);
		str = matcher.replaceAll("");
		str = str.replace("'", "''");
		str = str.replace("-", "—");
		str = str.replace("(", "（");
		str = str.replace(")", "）");
		str = str.replace("%", "％");

		return str;
	}

	public static String getLowercaseString(HttpServletRequest request, String key) {
		return getString(request, key).toLowerCase();
	}

	public static int getInt(HttpServletRequest request, String key) {
		return getInt(request, key, 0);
	}

	public static int getInt(HttpServletRequest request, String key, int defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Integer.parseInt(str);
	}

	public static long getLong(HttpServletRequest request, String key) {
		return getLong(request, key, 0L);
	}

	public static Long[] getLongAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtils.isEmpty(aryKey))
			return null;
		Long[] aryLong = new Long[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryLong[i] = Long.valueOf(Long.parseLong(aryKey[i]));
		}
		return aryLong;
	}

	public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
		String sysUserId = request.getParameter(key);
		String[] aryId = sysUserId.split(",");
		Long[] lAryId = new Long[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = Long.valueOf(Long.parseLong(aryId[i]));
		}
		return lAryId;
	}

	public static String[] getStringAryByStr(HttpServletRequest request, String key) {
		String sysUserId = request.getParameter(key);
		String[] aryId = sysUserId.split(",");
		String[] lAryId = new String[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = aryId[i];
		}
		return lAryId;
	}

	public static Integer[] getIntAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Integer[] aryInt = new Integer[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryInt[i] = Integer.valueOf(Integer.parseInt(aryKey[i]));
		}
		return aryInt;
	}

	public static Float[] getFloatAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Float[] fAryId = new Float[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			fAryId[i] = Float.valueOf(Float.parseFloat(aryKey[i]));
		}
		return fAryId;
	}

	public static long getLong(HttpServletRequest request, String key, long defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Long.parseLong(str);
	}

	public static float getFloat(HttpServletRequest request, String key) {
		return getFloat(request, key, 0.0F);
	}

	public static float getFloat(HttpServletRequest request, String key, float defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Float.parseFloat(request.getParameter(key));
	}

	public static boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, false);
	}

	public static boolean getBoolean(HttpServletRequest request, String key, boolean defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		if (StringUtils.isNumeric(str))
			return Integer.parseInt(str) == 1;
		return Boolean.parseBoolean(str);
	}

	public static Short getShort(HttpServletRequest request, String key) {
		return getShort(request, key, Short.valueOf((short) 0));
	}

	public static Short getShort(HttpServletRequest request, String key, Short defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Short.valueOf(Short.parseShort(str));
	}

	public static Date getDate(HttpServletRequest request, String key, String style) throws ParseException {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		if (StringUtil.isEmpty(style))
			style = "yyyy-MM-dd HH:mm:ss";
		return DateFormatUtil.parse(str, style);
	}

	public static Date getDate(HttpServletRequest request, String key) throws ParseException {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		return DateFormatUtil.parseDate(str);
	}

	public static Date getTimestamp(HttpServletRequest request, String key) throws ParseException {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		return DateFormatUtil.parseDateTime(str);
	}

	public static String getUrl(HttpServletRequest request) {
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(request.getRequestURI());
		Enumeration e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}

	public static String getPrePage(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	public static Map getQueryMap(HttpServletRequest request) {
		Map map = new HashMap();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if ((key.equals("sortField")) || (key.equals("orderSeq")) || (key.equals("sortColumns"))) {
				String val = values[0].trim();
				if (StringUtil.isNotEmpty(val)) {
					map.put(key, values[0].trim());
				}
			}
			if ((values.length > 0) && (StringUtils.isNotEmpty(values[0]))) {
				if (key.startsWith("Q_")) {
					addParameter(key, values, map);
				} else if (values.length == 1) {
					String val = values[0].trim();
					if (StringUtil.isNotEmpty(val))
						map.put(key, values[0].trim());
				} else {
					map.put(key, values);
				}
			}
		}

		return map;
	}

	private static void addParameter(String key, String[] values, Map<String, Object> map) {
		String[] aryParaKey = key.split("_");
		if (aryParaKey.length < 3)
			return;
		String paraName = key.substring(2, key.lastIndexOf("_"));
		String type = key.substring(key.lastIndexOf("_") + 1);
		if (values.length == 1) {
			String value = values[0].trim();
			if (StringUtil.isNotEmpty(value))
				try {
					if (value.indexOf("_") != -1) {
						value = value.replaceAll("_", "|_");
					}
					Object obj = getObjValue(type, value);
					map.put(paraName, obj);
				} catch (Exception e) {
					logger.debug(e.getMessage());
				}
		} else {
			Object[] aryObj = getObjValue(type, values);
			map.put(paraName, aryObj);
		}
	}

	private static Object getObjValue(String type, String paramValue) {
		Object value = null;

		if ("S".equals(type)) {
			value = paramValue;
		} else if ("SL".equals(type)) {
			value = "%" + paramValue + "%";
		} else if ("SLR".equals(type)) {
			value = paramValue + "%";
		} else if ("SLL".equals(type)) {
			value = "%" + paramValue;
		} else if ("L".equals(type)) {
			value = new Long(paramValue);
		} else if ("N".equals(type))
			value = new Integer(paramValue);
		else if ("DB".equals(type)) {
			value = new Double(paramValue);
		} else if ("BD".equals(type)) {
			value = new BigDecimal(paramValue);
		} else if ("FT".equals(type)) {
			value = new Float(paramValue);
		} else if ("SN".equals(type)) {
			value = new Short(paramValue);
		} else if ("DL".equals(type)) {
			value = TimeUtil.convertString(paramValue, "yyyy-MM-dd");
		} else if ("DG".equals(type))
			value = TimeUtil.getNextDays(TimeUtil.convertString(paramValue, "yyyy-MM-dd"), 1);
		else {
			value = paramValue;
		}
		return value;
	}

	private static Object[] getObjValue(String type, String[] value) {
		Object[] aryObj = new Object[value.length];
		for (int i = 0; i < aryObj.length; i++) {
			aryObj[i] = getObjValue(type, value);
		}
		return aryObj;
	}

	public static Map getParameterValueMap(HttpServletRequest request, boolean remainArray, boolean isSecure) {
		Map map = new HashMap();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if (values != null) {
				if (values.length == 1) {
					String tmpValue = values[0];
					if (tmpValue != null) {
						tmpValue = tmpValue.trim();
						if (!tmpValue.equals("")) {
							if (isSecure)
								tmpValue = filterInject(tmpValue);
							if (!tmpValue.equals("")) {
								map.put(key, tmpValue);
							}
						}
					}
				} else {
					String rtn = getByAry(values, isSecure);
					if (rtn.length() > 0)
						if (remainArray)
							map.put(key, rtn.split(","));
						else
							map.put(key, rtn);
				}
			}
		}
		return map;
	}

	private static String getByAry(String[] aryTmp, boolean isSecure) {
		String rtn = "";
		for (int i = 0; i < aryTmp.length; i++) {
			String str = aryTmp[i].trim();
			if (!str.equals("")) {
				if (isSecure) {
					str = filterInject(str);
					if (!str.equals(""))
						rtn = rtn + str + ",";
				} else {
					rtn = rtn + str + ",";
				}
			}
		}
		if (rtn.length() > 0)
			rtn = rtn.substring(0, rtn.length() - 1);
		return rtn;
	}

	public static String getStringValues(HttpServletRequest request, String paramName) {
		String[] values = request.getParameterValues(paramName);
		if (BeanUtils.isEmpty(values))
			return "";
		String tmp = "";
		for (int i = 0; i < values.length; i++) {
			if (i == 0)
				tmp = tmp + values[i];
			else {
				tmp = tmp + "," + values[i];
			}
		}
		return tmp;
	}

	public static Locale getLocal(HttpServletRequest request) {
		Locale local = request.getLocale();
		if (local == null)
			local = Locale.CHINA;
		return local;
	}

	public static final String getErrorUrl(HttpServletRequest request) {
		String errorUrl = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (errorUrl == null) {
			errorUrl = (String) request.getAttribute("javax.servlet.forward.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = (String) request.getAttribute("javax.servlet.include.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = request.getRequestURL().toString();
		}
		return errorUrl;
	}

	public static final StringBuilder getErrorInfoFromRequest(HttpServletRequest request, boolean isInfoEnabled) {
		StringBuilder sb = new StringBuilder();
		String errorUrl = getErrorUrl(request);
		sb.append(StringUtil.formatMsg("Error processing url: %1, Referrer: %2, Error message: %3.\n",
				new Object[] { errorUrl, request.getHeader("REFERER"), request.getAttribute("javax.servlet.error.message") }));

		Throwable ex = (Throwable) request.getAttribute("exception");
		if (ex == null) {
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}

		if (ex != null) {
			sb.append(StringUtil.formatMsg("Exception stack trace: \n", new Object[] { ex }));
		}
		return sb;
	}

	public static final StringBuilder getRequestInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------Debuging request.getParameterNames()---------\n");
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			sb.append(StringUtil.formatMsg("Request Parameter - %1 = %2.\n", new Object[] { paramName, request.getParameter(paramName) }));
		}

		sb.append("-----------Debuging request.getAttributeNames()---------\n");
		enumeration = request.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String attrName = (String) enumeration.nextElement();
			if (!attrName.endsWith("exception")) {
				sb.append(StringUtil.formatMsg("Request Attribute - %1 = %2.\n", new Object[] { attrName, request.getAttribute(attrName) }));
			}
		}

		sb.append("-----------Debuging request.getHeaderNames()---------------\n");
		enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String headerName = (String) enumeration.nextElement();
			sb.append(StringUtil.formatMsg("Request Header - %1 = %2.\n", new Object[] { headerName, request.getHeader(headerName) }));
		}

		return sb;
	}
}