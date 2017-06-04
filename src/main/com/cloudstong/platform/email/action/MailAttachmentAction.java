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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.util.Streams;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.util.UniqueIdUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.email.model.MailAttachment;
import com.cloudstong.platform.email.service.MailAttachmentService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-26
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/email/mailAttachment")
@Results({ @Result(name = "download", type = "stream", params = { "contentType", "application/octet-stream", "bufferSize", "4096",
		"contentDisposition", "attachment;filename=${fileName}", "contentLength", "${fileSize}", "inputName", "inputStream" }) })
public class MailAttachmentAction extends BaseAction {

	private static final long serialVersionUID = 5150132710980671173L;

	// 下载所需
	private Long id;
	private InputStream inputStream;
	private String fileName;
	private int fileSize;

	private Long mailId;

	// 上传所需
	private List<File> uploadFiles;
	private List<String> uploadFilesContentType;
	private List<String> uploadFilesFileName;

	@Resource
	private MailAttachmentService mailAttachmentService;

	@Action("download")
	public String download() {
		MailAttachment mailFile = mailAttachmentService.findMailAttachmentById(id);
		try {
			inputStream = new FileInputStream(new File(mailFile.getPath()));
			fileSize = inputStream.available();
			fileName = mailFile.getName();
			try {
				if (getRequest().getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
					fileName = new String(mailFile.getName().getBytes("UTF-8"), "ISO8859-1");
				} else if (getRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
					fileName = URLEncoder.encode(mailFile.getName().trim(), "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				if (log.isErrorEnabled())
					log.error(e.getMessage(), e);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "download";
	}

	@Action("upload")
	public String upload() {
		String folder = getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "email" + File.separator
				+ new SimpleDateFormat("yyyy-mm-dd").format(new Date()) + File.separator;
		File folderFile = new File(folder);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		List<File> files = getUploadFiles();
		StringBuilder ids = new StringBuilder();
		if (files != null) {
			List<MailAttachment> mailAttachments = new ArrayList<MailAttachment>(files.size());
			for (int i = 0; i < files.size(); i++) {
				String fileRealPath = folder + uploadFilesFileName.get(i);
				try {
					BufferedInputStream in = new BufferedInputStream(new FileInputStream(files.get(i)));// 获得文件输入流
					BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
					Streams.copy(in, outStream, true);// 把文件写到指定的上传文件夹
					// 插入数据库
					Long mid = UniqueIdUtil.genId();
					ids.append(mid).append(";");
					mailAttachments.add(new MailAttachment(uploadFilesFileName.get(i), fileRealPath,mailId));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			mailAttachmentService.doSaveMailAttachments(mailAttachments);
		}
		printObject(ids.deleteCharAt(ids.length() - 1).toString());
		return NONE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public int getFileSize() {
		return fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public List<File> getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(List<File> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	public List<String> getUploadFilesContentType() {
		return uploadFilesContentType;
	}

	public void setUploadFilesContentType(List<String> uploadFilesContentType) {
		this.uploadFilesContentType = uploadFilesContentType;
	}

	public List<String> getUploadFilesFileName() {
		return uploadFilesFileName;
	}

	public void setUploadFilesFileName(List<String> uploadFilesFileName) {
		this.uploadFilesFileName = uploadFilesFileName;
	}

	public Long getMailId() {
		return mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

}
