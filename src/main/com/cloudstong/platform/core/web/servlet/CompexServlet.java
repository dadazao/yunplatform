package com.cloudstong.platform.core.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloudstong.platform.resource.attachment.dao.AttachmentDao;
import com.cloudstong.platform.resource.attachment.dao.impl.AttachmentDaoImpl;
import com.cloudstong.platform.resource.attachment.model.Attachment;
import com.cloudstong.platform.resource.catalog.dao.CatalogDao;
import com.cloudstong.platform.resource.catalog.dao.impl.CatalogDaoImpl;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.metadata.service.CompexDomainService;
import com.cloudstong.platform.resource.metadata.service.impl.CompexDomainServiceImpl;

/**
 * @author michael
 * Created on 2012-11-13
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:多文件上传Servlet
 */
public class CompexServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CompexServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebApplicationContext wc = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		CatalogDao catalogDao=new CatalogDaoImpl(wc);
		request.setCharacterEncoding("UTF-8");
		//获取表单ID
		String formId=request.getParameter("formId");
		//根据表单ID获取目录ID
		String catalogId = null;
		if(formId!=null && !formId.equals("")){
			catalogId = String.valueOf(catalogDao.findCatalogByFormId(Long.valueOf(formId)).getId());
		}
		
		//获取表名
		String model=request.getParameter("model");
		//获取字段名
		String columnname=request.getParameter("columnname");
		//获取当前用户
		String createby=request.getParameter("createby")==null?"-1":request.getParameter("createby");
		/*//取得当前表单的记录id
		String recordid = request.getParameter("recordid")==null?"-1":request.getParameter("recordid");*/
		//获取所属对象字段
		String recordColumn = request.getParameter("recordColumn")==null?"-1":request.getParameter("recordColumn");
		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss");
		String newfileName = sdfFileName.format(date);// 文件名称
		String fileRealPath = "";// 文件存放真实地址

		String fileRealResistPath = "";// 文件存放真实相对路径

		String firstFileName = "";
		// 获得容器中上传文件夹所在的物理路径
		String savePath = this.getServletConfig().getServletContext().getRealPath("/") + "upload/" + model + "/" + newfileName + "/";
		System.out.println("路径" + savePath);
		File file = new File(savePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}

		try {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 获取多个上传文件
			List fileList = upload.parseRequest(request);
			System.out.println("size="+fileList.size());
			// 遍历上传文件写入磁盘
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				Object obit = it.next();
				if (obit instanceof DiskFileItem) {
					DiskFileItem item = (DiskFileItem) obit;

					// 如果item是文件上传表单域
					// 获得文件名及路径
					String fileName = item.getName();
					if (fileName != null) {
						firstFileName = item.getName().substring(item.getName().lastIndexOf("/") + 1);
						String formatName = firstFileName.substring(firstFileName.lastIndexOf("."));// 获取文件后缀名
						fileRealPath = savePath + newfileName + (int)(Math.random()*100000) + formatName;// 文件存放真实地址

						BufferedInputStream in = new BufferedInputStream(item.getInputStream());// 获得文件输入流
						BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(fileRealPath)));// 获得文件输出流
						Streams.copy(in, outStream, true);// 把文件写到指定的上传文件夹
						// 上传成功，则插入数据库
						if (new File(fileRealPath).exists()) {
							// 虚拟路径赋值
							fileRealResistPath = fileRealPath.substring(fileRealPath.indexOf("upload"));
							AttachmentDao attachmentDao=new AttachmentDaoImpl(wc);
							Attachment attachment=new Attachment();
							attachment.setCatalogId(catalogId);
							attachment.setFileName(firstFileName);
							attachment.setFilePath("/"+fileRealResistPath);
							attachment.setFileExt(formatName);
							attachment.setFileSize(String.valueOf(item.getSize()));
							attachment.setModel(model);
							attachment.setColumn(columnname);
							attachment.setCreateBy(Long.valueOf(createby));
							attachment.setCreateDate(new Date(System.currentTimeMillis()));
							if(!recordColumn.equals("-1") && !recordColumn.equals("")){
								//String recordColumnValue = attachmentDao.findRecordColumnValue(model,recordid,recordColumn);
								String recordColumnName = attachmentDao.findRecordColumnName(recordColumn);
								//attachment.setRecordColumnValue(recordColumnValue);
								attachment.setRecordColumnName(recordColumnName);
							}
							//attachment.setRecordId(recordid);
							//插入附件表
							Long attachmentId = attachmentDao.insertAttachment(attachment);
							//关联业务表
							//attachmentDao.addRelByAttachment(model,columnname,attachmentId,recordid);
							response.setCharacterEncoding("UTF-8");
							PrintWriter out = response.getWriter();
							out.println("{\"attachmentId\":\""+attachmentId+"\",\"name\":\""+model+"-"+columnname+"\"}");
							out.flush();
							out.close();
						}

					}
				}
			}
		} catch (org.apache.commons.fileupload.FileUploadException ex) {
			ex.printStackTrace();
			System.out.println("没有上传文件");
			return;
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
