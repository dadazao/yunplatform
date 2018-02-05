package com.cloudstong.platform.third.bpm.thread;

import java.util.ArrayList;
import java.util.List;

import com.cloudstong.platform.core.util.BeanUtils;

public class MessageUtil {
	private static ThreadLocal<List<String>> localMsg = new ThreadLocal();

	public static void addMsg(String msg) {
		List list = (List) localMsg.get();
		if (BeanUtils.isEmpty(list)) {
			list = new ArrayList();
			list.add(msg);
			localMsg.set(list);
		} else {
			list.add(msg);
		}
	}

	public static List<String> getMsg() {
		return getMsg(true);
	}

	public static List<String> getMsg(boolean clean) {
		List list = (List) localMsg.get();
		if (clean) {
			localMsg.remove();
		}
		return list;
	}

	public static String getMessage() {
		return getMessage(true);
	}

	public static String getMessage(boolean clean) {
		List<String> list = getMsg(clean);
		String str = "";
		if (BeanUtils.isEmpty(list)) {
			return str;
		}
		for (String msg : list) {
			str = str + msg + "\r\n";
		}
		return str;
	}

	public static void clean() {
		localMsg.remove();
	}
}