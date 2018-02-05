package com.cloudstong.platform.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:系统配置文件操作工具
 */
public class SystemProperty {
	/**
	 * The <code>serialVersionUID</code> field is an instance of long.
	 */
	private static final long serialVersionUID = 1L;

	static String profilepath = "global.properties";

	private OutputStream fos;
	/**
	 * 采用静态方法
	 */
	private static Properties props = new Properties();
	
	public Properties getProperties() {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					profilepath));
			props.load(in);
			return props;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isEmpty() {
		if(props.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}

	public SystemProperty(String path) {
		try {
			profilepath = path;
			props.load(new FileInputStream(profilepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}

	/**
	 * 读取属性文件中相应键的值
	 * 
	 * @param key
	 *            主键
	 * @return String
	 */
	public static String getKeyValue(String key) {
		return props.getProperty(key);
	}

	/**
	 * 根据主键key读取主键的值value
	 * 
	 * @param filePath
	 *            属性文件路径
	 * @param key
	 *            键名
	 */
	public static String readValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					profilepath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新（或插入）一对properties信息(主键及其键值) 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public static void writeProperties(String keyname, String keyvalue) {
		try {
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(profilepath);
			props.setProperty(keyname, keyvalue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}

	/**
	 * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
	 * 
	 * @param keyname
	 *            键名
	 * @param keyvalue
	 *            键值
	 */
	public void updateProperties(String keyname, String keyvalue) {
		try {
			props.load(new FileInputStream(profilepath));
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(profilepath);
			props.setProperty(keyname, keyvalue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			System.err.println("属性文件更新错误");
		}
	}

	public void closeStream() {
		if (fos != null) {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	// 测试代码
	public static void main(String[] args) {
//		readValue("MAIL_SERVER_PASSWORD");
//		writeProperties("MAIL_SERVER_INCOMING", "327@qq.com");
//		System.out.println("操作完成");
		char[] temp = "2a041629b01e4a14baf3004586722dea".toCharArray();
		String a="";
		for(char t:temp ) {
			a+=new Integer(new String(new char[]{t}).hashCode()).toString();
			
		}
		System.out.println("99994852457".hashCode());
		
	}
}
