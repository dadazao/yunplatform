/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.editor.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

/**
 * @author sam
 * Created on 2012-11-27
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@SuppressWarnings({ "serial", "deprecation" })
public class UploadFileServlet extends HttpServlet {
	
	private static String baseFileDir = File.separator + "upload" + File.separator;//上传文件存储目录
	private static String baseURLDir = "/upload/";//上传文件目录URL
	private static String fileExt = "jpg,jpeg,bmp,gif,png";
	private static Long maxSize = 0l;

	// 0:不建目录 1:按天存入目录 2:按月存入目录 3:按扩展名存目录 建议使用按天存
	private static String dirType = "1";
	
	/**
	 * 文件上传初始化工作
	 */
	public void init() throws ServletException {
		/*获取文件上传存储的相当路径*/
		if (!StringUtils.isBlank(this.getInitParameter("baseDir"))){
			baseFileDir = this.getInitParameter("baseDir");
		}
		
		String realBaseDir = this.getServletConfig().getServletContext().getRealPath(baseFileDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists()) {
			baseFile.mkdir();
		}

		/*获取文件类型参数*/
		fileExt = this.getInitParameter("fileExt");
		if (StringUtils.isBlank(fileExt)) fileExt = "jpg,jpeg,gif,bmp,png";

		/*获取文件大小参数*/
		String maxSize_str = this.getInitParameter("maxSize");
		if (StringUtils.isNotBlank(maxSize_str)) maxSize = new Long(maxSize_str);
		
		/*获取文件目录类型参数*/
		dirType = this.getInitParameter("dirType");
		
		if (StringUtils.isBlank(dirType))
			dirType = "1";
		if (",0,1,2,3,".indexOf("," + dirType + ",") < 0)
			dirType = "1";
	}

	/**
	 * 上传文件数据处理过程
	 */
	@SuppressWarnings({"unchecked" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String err = "";
		String newFileName = "";

		DiskFileUpload upload = new DiskFileUpload();
		try {
			List<FileItem> items = upload.parseRequest(request);
			Map<String, Serializable> fields = new HashMap<String, Serializable>();
			Iterator<FileItem> iter = items.iterator();
			
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField())
					fields.put(item.getFieldName(), item.getString());
				else
					fields.put(item.getFieldName(), item);
			}
			
			/*获取表单的上传文件*/
			FileItem uploadFile = (FileItem)fields.get("filedata");
			
			/*获取文件上传路径名称*/
			String fileNameLong = uploadFile.getName();
			//System.out.println("fileNameLong:" + fileNameLong);
			
			/*获取文件扩展名*/
			/*索引加1的效果是只取xxx.jpg的jpg*/
			String extensionName = fileNameLong.substring(fileNameLong.lastIndexOf(".") + 1);
			//System.out.println("extensionName:" + extensionName);
			
			/*检查文件类型*/
			if (("," + fileExt.toLowerCase() + ",").indexOf("," + extensionName.toLowerCase() + ",") < 0){
				printInfo(response, "不允许上传此类型的文件", "");
				return;
			}
			/*文件是否为空*/
			if (uploadFile.getSize() == 0){
				printInfo(response, "上传文件不能为空", "");
				return;
			}
			/*检查文件大小*/
			if (maxSize > 0 && uploadFile.getSize() > maxSize){
				printInfo(response, "上传文件的大小超出限制", "");
				return;
			}
			
			//0:不建目录, 1:按天存入目录, 2:按月存入目录, 3:按扩展名存目录.建议使用按天存.
			String fileFolder = "";
			if (dirType.equalsIgnoreCase("1"))
				fileFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());;
			if (dirType.equalsIgnoreCase("2"))
				fileFolder = new SimpleDateFormat("yyyyMM").format(new Date());
			if (dirType.equalsIgnoreCase("3"))
				fileFolder = extensionName.toLowerCase();
			
			/*文件存储的相对路径*/
			String saveDirPath = baseFileDir + fileFolder + "/";
			//System.out.println("saveDirPath:" + saveDirPath);
			
			/*文件存储在容器中的绝对路径*/
			String saveFilePath = this.getServletConfig().getServletContext().getRealPath("") + baseFileDir + fileFolder + "/";
			//System.out.println("saveFilePath:" + saveFilePath);
			
			/*构建文件目录以及目录文件*/
			File fileDir = new File(saveFilePath);
			if (!fileDir.exists()) {fileDir.mkdirs();}
			
			/*重命名文件*/
			String filename = UUID.randomUUID().toString();
			File savefile = new File(saveFilePath + filename + "." + extensionName);
			
			/*存储上传文件*/
			//System.out.println(upload == null);
			uploadFile.write(savefile);
			
			String projectBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
			newFileName = projectBasePath + baseURLDir + fileFolder + "/" + filename + "." + extensionName;		
			//System.out.println("newFileName:" + newFileName);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			newFileName = "";
			err = "错误: " + ex.getMessage();
		}
		printInfo(response, err, newFileName);
	}
	
	/**
	 * 使用I/O流输出 json格式的数据
	 * @param response
	 * @param err
	 * @param newFileName
	 * @throws IOException
	 */
	public void printInfo(HttpServletResponse response, String err, String newFileName) throws IOException {
		PrintWriter out = response.getWriter();
		//String filename = newFileName.substring(newFileName.lastIndexOf("/") + 1);
		out.println("{\"err\":\"" + err + "\",\"msg\":\"" + newFileName + "\"}");
		out.flush();
		out.close();
	}
}
