/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.util;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * @author Allan Created on 2012-11-21
 * 
 * Revision History: Date Reviser Description ---- -------
 * ----------------------------------------------------
 * 
 * Description:zip工具包
 * 
 */
public class ZipUtil {

	/**
	 * Description:打zip包
	 * 
	 * @param dir
	 * @param dest
	 */
	public static void zip(String dir, String dest) {
		Project pj = new Project();
		Zip zip = new Zip();
		zip.setProject(pj);
		//打包完的目标文件
		zip.setDestFile(new File(dest));
										
		FileSet fileSet = new FileSet();
		fileSet.setProject(pj);
		//需要打包的路径
		fileSet.setDir(new File(dir));
										
		zip.addFileset(fileSet);
		zip.execute();
	}

	/**
	 * Description:解压zip包
	 * 
	 * @param src
	 * @param dest
	 */
	public static void unzip(String src, String dest) {
		Project pj = new Project();
		Expand expand = new Expand();
		expand.setProject(pj);
		expand.setDest(new File(dest));
		expand.setSrc(new File(src));
		expand.execute();
	}

	public static void main(String[] args) {
		ZipUtil.zip("E:/Workstation/MyEclipse/platform/beaver/WebRoot/tmp/pages/original", "E:/Workstation/MyEclipse/platform/beaver/WebRoot/tmp/pages/original.zip");
		ZipUtil.unzip("E:/Workstation/MyEclipse/platform/beaver/WebRoot/tmp/pages/original.zip", "E:/Workstation/MyEclipse/platform/beaver/WebRoot/tmp/pages/22");
	}

}
