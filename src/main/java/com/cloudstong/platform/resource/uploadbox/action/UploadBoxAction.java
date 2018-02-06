package com.cloudstong.platform.resource.uploadbox.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cloudstong.platform.core.web.action.BaseAction;

/**
 * @author michael
 * Created on 2012-11-13
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:上传文件框Action
 */
@ParentPackage("default")
@Namespace("/pages/resource/uploadbox")
public class UploadBoxAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 查看页面时打开或下载文件
	 * 
	 * @return NONE
	 * @throws IOException
	 */
	@Action("showSingleFile")
	public String showSingleFile() throws IOException {
		try {
			String _path = new String(getRequest().getParameter("path").getBytes("iso-8859-1"), "utf-8");
			if (_path != null && !_path.equals("")) {
				HttpServletResponse response = getResponse();
				response.setCharacterEncoding("UTF-8");

				// 文件下载
				File file = new File(getRequest().getRealPath("/") + _path);
				InputStream fis = new BufferedInputStream(new FileInputStream(file));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();

				response.reset();

				String filename = _path.substring(_path.lastIndexOf("/"));
				if (getRequest().getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
					filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
				} else if (getRequest().getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
					filename = URLEncoder.encode(filename.trim(), "UTF-8");
				}

				response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
				response.addHeader("Content-Length", String.valueOf(file.length()));
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
}
