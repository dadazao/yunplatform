/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;

import com.cloudstong.platform.core.util.DateUtil;
import com.cloudstong.platform.core.util.PathUtil;

/**
 * @author liuqi
 * 
 *         Created on 2014-9-22
 * 
 *         Description:
 * 
 */
public class LogAspect {
	private static final Log log = LogFactory.getLog(LogAspect.class);
	
	// 日志配置文件
	static Properties properties = null;
	
	static {
		properties = new Properties();
		try {
			FileInputStream in = new FileInputStream(PathUtil.getWebInfPath()+"/WEB-INF/classes/log.properties");
			properties.load(in);
		} catch (FileNotFoundException e) {
			log.error("没有找到日志配置文件，请确认你的路径是否正确。");
		} catch (IOException e) {
			log.error("日志配置文件读写错误");
		}
	}
	
	@SuppressWarnings("unused")
	private void addLog(JoinPoint joinPoint) {
		String key = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
		// 得到方法描述信息
		String info = properties.getProperty(key);
		if (info != null && !info.equals("") && !info.equals(" ")) {
			Log _log = LogFactory.getLog("business");
			_log.info(MDC.get("userName")+"在"+ DateUtil.getDateTimeString(new Date()) + info);
		} else {
			//System.out.println("请检查您的日志配置文件，AOP" + "中配置了此方法记录日志，但是没有在配置文件中找到该方法的描述，方法名：" + key);
		}
	}
	
	@SuppressWarnings("unused")
	private void addErrorLog(JoinPoint joinPoint,Throwable throwable){
		String key = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
		// 得到方法描述信息
		String info = properties.getProperty(key);
		if (info != null && !info.equals("") && !info.equals(" ")) {
			Log _log = LogFactory.getLog("business");
			_log.info(MDC.get("userName")+"在"+ DateUtil.getDateTimeString(new Date()) + info +"时程序抛出了异常:"
					+throwable+"-----Exception was throw in..." + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		} else {
			//System.out.println("请检查您的日志配置文件，AOP" + "中配置了此方法记录异常日志，但是没有在配置文件中找到该方法的描述，方法名：" + key);
		}
	}
	
}
