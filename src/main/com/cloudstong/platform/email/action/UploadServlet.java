/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Jason
 * 
 *         Created on 2014-9-16
 * 
 *         Description:
 * 
 */
public class UploadServlet extends HttpServlet {

	private final Logger log = Logger.getLogger(getClass());

	private static final long serialVersionUID = 6092909545966943917L;

	@SuppressWarnings("rawtypes")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("UTF-8");
		List<String> pathList = new ArrayList<String>();
		try {
			List fileList = upload.parseRequest(request);

			String folder = request.getSession().getServletContext().getRealPath("/") + "upload/email/";
			System.out.println(folder);

			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				Object obit = it.next();
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;
					String fileName = item.getName();
					if (fileName != null) {
						// String oldNameWithoutExt =
						// fileName.substring(fileName.lastIndexOf("/") + 1);
						// String ext =
						// oldNameWithoutExt.substring(oldNameWithoutExt.lastIndexOf("."));//
						// 获取文件后缀名
						// String newName = UniqueIdUtil.getGuid() + ext;
						String fileRealPath = folder + fileName;// 文件存放真实地址

						BufferedInputStream in = new BufferedInputStream(item.getInputStream());// 获得文件输入流
						BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
						Streams.copy(in, outStream, true);// 把文件写到指定的上传文件夹
						pathList.add(fileName);
					}
				}
			}
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (FileUploadException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			ObjectMapper mapper = new ObjectMapper();
			response.setContentType("text/plain");
			response.getWriter().write(mapper.writeValueAsString(pathList));
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
