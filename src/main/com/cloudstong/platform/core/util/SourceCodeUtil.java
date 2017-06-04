package com.cloudstong.platform.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:源码统计工具
 */
public class SourceCodeUtil {

	private static int CODELINE = 0;
	private static int FILENUM = 0;
	private static BufferedReader reader = null;

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}
	}

	public static void readFile(String psPath) {
		File pFile = new File(psPath);
		if (pFile.isDirectory()) {
			File[] tempFile = pFile.listFiles();
			for (File file : tempFile) {
				if (!file.getName().endsWith(".svn")) {
					if (file.isFile()) {
						FILENUM++;
						try {
							reader = new BufferedReader(new FileReader(file));
							int line = 1;
							while (reader.readLine() != null) {
								CODELINE++;
								line++;
							}
							System.out.println(file.getName() + "---"
									+ CODELINE);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (file.isDirectory()) {
						readFile(file.getPath());
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			// 代码行数:66606
			// 文件数量:512
			readFile("E:/cloudstong");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		System.out.println(FILENUM);
	}
}
