package com.cloudstong.platform.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:业务异常基类
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 5439915454935047935L;

	public static final String copyright = "";

	/**
	 * 错误代码
	 */
	private String errorCode;

	/**
	 * 异常容器
	 */
	private Collection<AppException> exceptions = new ArrayList<AppException>();

	/**
	 * 消息键
	 */
	private String messageKey = null;

	/**
	 * 消息参数
	 */
	private Object[] messageArgs = null;

	public AppException(String message) {
		super(message);
	}

	public AppException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	protected Throwable rootCause = null;

	public AppException() {
		super();
	}

	public AppException(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	public Collection getCollection() {
		return exceptions;
	}

	public void addException(AppException ex) {
		exceptions.add(ex);
	}

	public void setMessageKey(String key) {
		this.messageKey = key;
	}

	public String getMessageKey() {

		return messageKey;
	}

	public void setMessageArgs(Object[] args) {
		this.messageArgs = args;
	}

	public Object[] getMessageArgs() {
		return messageArgs;
	}

	public void setRootCause(Throwable anException) {
		rootCause = anException;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);
		if (getRootCause() != null) {
			getRootCause().printStackTrace(writer);
		}
		writer.flush();
	}

}
