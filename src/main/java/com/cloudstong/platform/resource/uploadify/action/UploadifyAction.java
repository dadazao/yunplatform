package com.cloudstong.platform.resource.uploadify.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.attachment.model.Attachment;
import com.cloudstong.platform.resource.attachment.service.AttachmentService;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;

/**
 * @author michael
 * Created on 2012-11-13
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:多文件上传组件Action
 */
@ParentPackage("default")
@Namespace("/pages/resource/uploadify")
public class UploadifyAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作附件的服务接口,<code>attachmentService</code> 对象是AttachmentService接口的一个实例
	 */
	@Resource
	protected AttachmentService attachmentService;
	
	/**
	 * 操作配置模块的服务接口,<code>compexDomainService</code> 对象是CompexDomainService接口的一个实例
	 */
	@Resource
	protected CompexDomainService compexDomainService;
	
	/**
	 * 表单编辑页面显示上传的文件列表
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showCompleteFile")
	public String showCompleteFile() throws IOException {
		String fileIds = getRequest().getParameter("fileIds") == null ? "" : getRequest().getParameter("fileIds")
				.toString();
		String recordId = getRequest().getParameter("recordId");
		List<Attachment> fileList = new ArrayList<Attachment>();
		if (fileIds.length() > 0) {
			fileList = attachmentService.findAttachmentByIds(fileIds);
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < fileList.size(); i++) {
			Attachment atm = fileList.get(i);
			sb.append("<div id='SWFUpload_100_" + i + "' class='uploadify-queue-item'>");
			sb.append("<div class='delete'><a href='javascript:deleteFile(\""
					+ atm.getModel()
					+ "\",\""
					+ atm.getColumn()
					+ "\",\"SWFUpload_100_"
					+ i
					+ "\",\""
					+ atm.getFilePath().replace("\\", "\\\\")
					+ "\",\""
					+ atm.getId()
					+ "\",\""
					+ recordId
					+ "\")'>X</a></div><div class='space'><a>&nbsp;</a></div><div class='view'><a href='javascript:eventCompexCKWJ(\""
					+ atm.getId() + "\")'>X</a></div>");
			sb.append("<span class='fileName'>" + atm.getFileName() + " (" + Integer.valueOf(atm.getFileSize()) / 1024
					+ "KB)</span>");
			sb.append("<span class='data'> - 完成</span>");
			sb.append("<div class='uploadify-progress'><div class='uploadify-progress-bar' style='width: 100%;'></div></div>");
			sb.append("</div>");
		}
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * 表单查看页面显示上传的文件列表
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showCompleteFileView")
	public String showCompleteFileView() throws IOException {
		String fileIds = getRequest().getParameter("fileIds") == null ? "" : getRequest().getParameter("fileIds")
				.toString();
		List<Attachment> fileList = new ArrayList<Attachment>();
		if (fileIds.length() > 0) {
			fileList = attachmentService.findAttachmentByIds(fileIds);
		}
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < fileList.size(); i++) {
			Attachment atm = fileList.get(i);
			sb.append("<div id='SWFUpload_0_" + i + "' class='uploadify-queue-item'>");
			sb.append("<div class='view'><a href='javascript:eventCompexCKWJ(\"" + atm.getId() + "\")'>X</a></div>");
			sb.append("<span class='fileName'>" + atm.getFileName() + " (" + Integer.valueOf(atm.getFileSize()) / 1024
					+ "KB)</span>");
			// sb.append("<span class='data'> - 完成</span>");
			// sb.append("<div class='uploadify-progress'><div class='uploadify-progress-bar' style='width: 100%;'></div></div>");
			sb.append("</div>");
		}
		out.write(sb.toString());
		return NONE;
	}

	/**
	 * 多文件上传组件，删除上传的文件
	 * 
	 * @return NONE
	 */
	@Action("deleteFile")
	public String deleteFile() {
		String model = getRequest().getParameter("model");
		String column = getRequest().getParameter("column");
		String filepath = getRequest().getParameter("filepath");
		String fileid = getRequest().getParameter("fileid");
		String recordid = getRequest().getParameter("recordId");
		// 删除物理文件
		File file = new File(getRequest().getRealPath("/") + filepath);
		if (file.exists()) {
			file.delete();
		}
		// 删除附件表中记录
		attachmentService.doDeleteById(fileid);
		// 将此文件从业务表中移除
		compexDomainService.doRemoveFile(model, column, Long.valueOf(fileid), Long.valueOf(recordid));
		return NONE;
	}

	/**
	 * 打开或者下载文件
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showFile")
	public String showFile() throws IOException {
		String _fileId = getRequest().getParameter("fileId");
		if (_fileId != null && !_fileId.equals("")) {
			_fileId = _fileId.replaceAll(";", "");
			// if (selectedVOs != null) {
			/*
			 * List<DomainVO> ds = getDomainVos(selectedVOs[0]); DomainVO
			 * domainVo = ds.get(0);
			 */
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			String filepath = "";
			// if(domainVo.getTable().equalsIgnoreCase("sys_attachment")){
			List<Attachment> list = attachmentService.findAttachmentByIds(_fileId);
			if (list.size() > 0) {
				Attachment attachment = list.get(0);

				// 文件下载
				File file = new File(getRequest().getRealPath("/") + attachment.getFilePath());
				InputStream fis = new BufferedInputStream(new FileInputStream(file));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();

				response.reset();

				String filename = "";
				if (getRequest().getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
					filename = new String(attachment.getFileName().getBytes("UTF-8"), "ISO8859-1");
				} else if (getRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
					filename = URLEncoder.encode(attachment.getFileName().trim(), "UTF-8");
				} else {
					filename = attachment.getFileName();
				}

				response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
				response.addHeader("Content-Length", attachment.getFileSize());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			}
			// }
			// }
		}
		return NONE;
	}

}
