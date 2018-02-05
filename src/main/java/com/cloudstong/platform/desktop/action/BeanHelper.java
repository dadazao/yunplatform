/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.desktop.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.cloudstong.platform.system.model.SysUser;

/**
 * @author Jason
 * 
 *         Created on 2014-9-29
 * 
 *         Description:
 * 
 */
@Component("beanHelper")
public class BeanHelper implements ApplicationContextAware {

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * Description:执行表达式所表示的方法
	 * 
	 * @param name
	 *            表达式：格式beanName.methodName 例如：desktopItemService.list
	 *            将会执行bean名字为desktopItemService实例的list方法
	 * 
	 * @return 方法执行后的返回值
	 * @throws NoSuchMethodException
	 */
	public Object executeMethod(String name, SysUser user) throws NoSuchMethodException {
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("the param: name can not be null");
		}
		String[] params = name.split("\\.");
		if (params.length < 2) {
			throw new IllegalArgumentException("the param :name does not match the pattern :\"beanName.beanMethod\"");
		}
		String beanName = Character.toLowerCase(params[0].charAt(0)) + params[0].substring(1); // 首字母小写
		String methodName = params[1];
		Object result = null;
		Object targetBean = getBean(beanName); // 获取bean
		try {
			Method targetMethod = null;
			try {
				targetMethod = targetBean.getClass().getDeclaredMethod(methodName);
			} catch (NoSuchMethodException e) {
				targetMethod = targetBean.getClass().getDeclaredMethod(methodName, SysUser.class);
			}
			if (targetMethod == null) {
				throw new NoSuchMethodException(methodName + " method does not exist");
			}

			targetMethod.setAccessible(true);
			Class<?>[] ptypes = targetMethod.getParameterTypes();
			if (ptypes != null && ptypes.length > 0) {
				if (ptypes.length > 1) {
					throw new IllegalStateException("the desktop item render method:" + name + " has more than one parameter..");
				}
				if (ptypes[0] == SysUser.class) {
					result = targetMethod.invoke(targetBean, user);
				}
			} else {
				result = targetMethod.invoke(targetBean);
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
}
