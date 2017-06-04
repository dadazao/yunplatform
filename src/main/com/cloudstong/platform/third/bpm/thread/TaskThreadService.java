package com.cloudstong.platform.third.bpm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.cloudstong.platform.third.bpm.model.ProcessCmd;

public class TaskThreadService {
	private static ThreadLocal<String> forkTaskTokenLocal = new ThreadLocal();

	private static ThreadLocal<List<Task>> newTasksLocal = new ThreadLocal();

	private static ThreadLocal<String> preUserLocal = new ThreadLocal();

	private static ThreadLocal<ProcessCmd> processCmdLocal = new ThreadLocal();

	private static ThreadLocal<List<String>> extSubProcess = new ThreadLocal();

	private static ThreadLocal<Object> objLocal = new ThreadLocal();

	private static ThreadLocal<Map<String, Object>> varsLocal = new ThreadLocal();

	public static void addTask(Task taskEntity) {
		List taskList = (List) newTasksLocal.get();
		if (taskList == null) {
			taskList = new ArrayList();
			newTasksLocal.set(taskList);
		}
		taskList.add(taskEntity);
	}

	public static List<Task> getNewTasks() {
		List list = (List) newTasksLocal.get();
		return list;
	}

	public static String getToken() {
		return (String) forkTaskTokenLocal.get();
	}

	public static void setToken(String token) {
		forkTaskTokenLocal.set(token);
	}

	public static void clearToken() {
		forkTaskTokenLocal.remove();
	}

	public static void clearNewTasks() {
		newTasksLocal.remove();
	}

	public static void setPreTaskUser(String user) {
		preUserLocal.set(user);
	}

	public static void cleanTaskUser() {
		preUserLocal.remove();
	}

	public static String getPreTaskUser() {
		if (preUserLocal.get() == null) {
			return "";
		}
		return (String) preUserLocal.get();
	}

	public static ProcessCmd getProcessCmd() {
		return (ProcessCmd) processCmdLocal.get();
	}

	public static void setProcessCmd(ProcessCmd processCmd) {
		processCmdLocal.set(processCmd);
	}

	public static void cleanProcessCmd() {
		processCmdLocal.remove();
	}

	public static List<String> getExtSubProcess() {
		return (List) extSubProcess.get();
	}

	public static void setExtSubProcess(List<String> extSubProcessList) {
		extSubProcess.set(extSubProcessList);
	}

	public static void addExtSubProcess(String instanceId) {
		List list = null;
		if (extSubProcess.get() == null) {
			list = new ArrayList();
			list.add(instanceId);
			extSubProcess.set(list);
		} else {
			((List) extSubProcess.get()).add(instanceId);
		}
	}

	public static void cleanExtSubProcess() {
		extSubProcess.remove();
	}

	public static void setObject(Object obj) {
		objLocal.set(obj);
	}

	public static Object getObject() {
		return objLocal.get();
	}

	public static void removeObject() {
		objLocal.remove();
	}

	public static void setVariables(Map<String, Object> vars_) {
		varsLocal.set(vars_);
	}

	public static Map<String, Object> getVariables() {
		return (Map) varsLocal.get();
	}

	public static void clearVariables() {
		varsLocal.remove();
	}

	public static void clearAll() {
		forkTaskTokenLocal.remove();
		newTasksLocal.remove();
		preUserLocal.remove();
		processCmdLocal.remove();
		extSubProcess.remove();
		objLocal.remove();
		varsLocal.remove();
	}
}