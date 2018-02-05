package com.cloudstong.platform.core.util;

import java.util.ArrayList;
import java.util.List;

public class MsgUtil {
	private static ThreadLocal<List<String>> msglist = new ThreadLocal();
	public static final int SUCCESS = 1;
	public static final int WARN = 2;
	public static final int ERROR = 3;
	public static final int OTHER = 0;

	public static void addMsg(int type, String msg) {
		List list = (List) msglist.get();
		if (BeanUtils.isNotEmpty(msg))
			msg = convertMsg(type, msg);
		if (BeanUtils.isEmpty(list)) {
			list = new ArrayList();
			list.add(msg);
			msglist.set(list);
		} else {
			list.add(msg);
		}
	}

	private static String convertMsg(int type, String msg) {
		StringBuffer sb = new StringBuffer();
		Boolean flag = Boolean.valueOf(true);
		if (BeanUtils.isNotEmpty(Integer.valueOf(type))) {
			if (type == 1)
				sb.append("!!! style=###color:green;###>");
			else if (type == 2)
				sb.append("!!!  style=###color:red;###>");
			else if (type == 3)
				sb.append("!!!  style=###color:orange;###>");
			else {
				flag = Boolean.valueOf(false);
			}
			sb.append(msg);
			if (flag.booleanValue())
				sb.append("%%%");
		}
		return sb.toString();
	}

	public static void addSplit() {
		addMsg(0, "------------------------------------------------------------");
	}

	public static String addSpace() {
		return "&nbsp;&nbsp;&nbsp;&nbsp;";
	}

	public static List<String> getMsg() {
		return getMsg(true);
	}

	public static List<String> getMsg(boolean clean) {
		List list = (List) msglist.get();
		if (clean)
			clean();
		return list;
	}

	public static String getMessage() {
		return getMessage(true);
	}

	public static String getMessage(boolean clean) {
		List<String> list = getMsg(clean);
		String str = "";
		if (BeanUtils.isEmpty(list))
			return str;
		for (String msg : list) {
			str = str + msg + "</br>";
		}
		return str;
	}

	public static void clean() {
		msglist.remove();
	}
}