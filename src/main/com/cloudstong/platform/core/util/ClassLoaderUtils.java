package com.cloudstong.platform.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.core.ReflectUtils;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:类加载器
 */
public class ClassLoaderUtils {
	private static Method DEFINE_CLASS;
	private static final ProtectionDomain PROTECTION_DOMAIN;
	static {
		PROTECTION_DOMAIN = (ProtectionDomain) AccessController
				.doPrivileged(new PrivilegedAction<Object>() {
					public Object run() {
						return ReflectUtils.class.getProtectionDomain();
					}
				});

		AccessController.doPrivileged(new PrivilegedAction<Object>() {
			public Object run() {
				try {
					Class<?> loader = Class.forName("java.lang.ClassLoader"); // JVM
																				// crash
																				// w/o
																				// this
					DEFINE_CLASS = loader.getDeclaredMethod("defineClass",
							new Class[] { String.class, byte[].class,
									Integer.TYPE, Integer.TYPE,
									ProtectionDomain.class });
					DEFINE_CLASS.setAccessible(true);
				} catch (ClassNotFoundException e) {
					throw new CodeGenerationException(e);
				} catch (NoSuchMethodException e) {
					throw new CodeGenerationException(e);
				}
				return null;
			}
		});
	}

	/**
	 * 加载class文件
	 * 
	 * @param classLoader
	 * @param className
	 * @param b
	 * @param protectionDomain
	 */
	public static void load(ClassLoader classLoader, String className,
			byte[] b, ProtectionDomain protectionDomain) {
		Object[] args = new Object[] { className.replace('/', '.'), b, 0,
				b.length, protectionDomain };
		try {
			DEFINE_CLASS.invoke(classLoader, args);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
