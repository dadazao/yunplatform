package com.cloudstong.platform.resource.catalog.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.cloudstong.platform.core.cache.ICache;
import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.exception.AppException;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.CacheUtil;
import com.cloudstong.platform.core.util.CopyFileUtil;
import com.cloudstong.platform.core.util.HandleFileUtil;
import com.cloudstong.platform.core.util.ZipUtil;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.catalog.service.CatalogService;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IPrivilegeService;
import com.cloudstong.platform.system.util.SecurityUtil;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description: 目录Action(配置模块)
 */
public class CompexCatalogAction extends CompexDomainAction {

	Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * 目录ID
	 */
	private Long id;
	
	/**
	 * 该目录的源码包
	 */
	private String srcPackge;
	
	/**
	 * 该目录的页面包
	 */
	private String pagePackage;
	
	@Resource
	private CatalogService catalogService;
	
	@Resource
	private IPrivilegeService privilegeService;
	
	public String load() throws Exception {
		return NONE;
	}
	
	/**
	 * 查找目录名称，返回json字符串
	 * @return
	 * @throws IOException
	 */
	public String find() throws IOException {
		Catalog catalog = this.catalogService.findCatalogById(id);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(catalog.getName());
		return NONE;
	}
	
	public String save(){
		try {
			super.save();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
		}
		Long catalogId=this.currentSaveId;
		Catalog catalog = catalogService.findCatalogById(catalogId);
		catalogService.doUpdateOrder(catalog);
		
		SecurityUtil.removeAll();
		
		return NONE;
	}
	
	/**
	 * Description:导出目录所有信息为zip文件
	 * 
	 * @return
	 */
	public String exportZip() throws Exception{
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");
		Catalog catalog = this.catalogService.findCatalogById(id);
		
		String basePath = getRequest().getRealPath("/");
		
		//复制源码到临时路径
		if(srcPackge!=null && !"".equals(srcPackge)) {
			srcPackge = srcPackge.replaceAll("\\.", "/");
			String srcPath = basePath+ "WEB-INF/classes/"+srcPackge;
			String desPath = basePath + "tmp/" + catalog.getPackageFolder() + "/WEB-INF/classes/" + srcPackge;
			CopyFileUtil.copyDirectory(srcPath,desPath,true);
		}
		
		//复制页面文件到临时路径
		if(pagePackage != null && !"".equals(pagePackage)) {
			String pageSrcPath = basePath + pagePackage;
			String pageDesPath = basePath + "tmp/" + catalog.getPackageFolder() + "/" + pagePackage;
			CopyFileUtil.copyDirectory(pageSrcPath,pageDesPath,true);
		}
		
		//sql语句
		String allsql = catalogService.buildAllCatalogSql(String.valueOf(id));
		File file = new File(basePath + "tmp/" + catalog.getPackageFolder());
		boolean success = file.mkdirs();
		if(success)	{
			File file2 = new File(basePath + "tmp/" + catalog.getPackageFolder()+"/"+ catalog.getPackageFolder() + ".sql");
			if(!file2.exists())
				file2.createNewFile();
		}
		FileWriter fw = new FileWriter(new File(basePath + "tmp/" + catalog.getPackageFolder()+"/"+ catalog.getPackageFolder() + ".sql")); 
		BufferedWriter bw=new BufferedWriter(fw);   
		bw.write(allsql);   
        bw.flush();
        bw.close();
        //打zip包
		String org = basePath + "tmp/" + catalog.getPackageFolder();
		String dest = basePath + "tmp/" + catalog.getPackageFolder() + ".zip";
		ZipUtil.zip(org,dest);
		
		//删除临时目录
		HandleFileUtil.deleteDirectory(org);
		
		InputStream fis = new BufferedInputStream(new FileInputStream(new File(dest)));
		int length = fis.available();
		byte[] buffer = new byte[length];
		fis.read(buffer);
		fis.close();

		response.reset();
		String filename = catalog.getPackageFolder() + ".zip";
		response.addHeader("Content-Disposition", "zip;filename=\"" + filename + "\"");
		response.addHeader("Content-Length", String.valueOf(length));
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
		
		return NONE;
	}
	
	/**
	 * Description:从zip包导入目录所有信息
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String importZip() throws IOException {
		String basepath = getRequest().getRealPath("/");
		String biaoshi = uploadFileName[0].substring(0, uploadFileName[0].length()-4);
		try {
			for (int i = 0; i < upload.length; i++) {
				File target = new File(getRequest().getRealPath("/")+"tmp", uploadFileName[i]);
				FileUtils.copyFile(upload[i], target);
			}
			
			//解压
			ZipUtil.unzip(basepath+"tmp/" + uploadFileName[0], basepath+"tmp/"+biaoshi);
			
			//复制
			String srcPath = basepath + "tmp/" + biaoshi;
			CopyFileUtil.copyDirectory(srcPath,basepath,true);
			
			//执行sql文件
			catalogService.doExecuteSqlFile(basepath + biaoshi + ".sql");
			
			printJSON("success", true);
		} catch (AppException e){
			printJSON("301", e.getMessage(), false);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
			printJSON("fail");
		}finally{
			//删除
			HandleFileUtil.deleteFile(basepath + biaoshi + ".sql");
			HandleFileUtil.deleteFile(basepath + "tmp/" + uploadFileName[0]);
			HandleFileUtil.deleteDirectory(basepath+"tmp/"+biaoshi);
		}
		return NONE;
	}
	
	public String deleteAll() throws Exception{
		try{
			Catalog catalog = catalogService.findCatalogById(id);
			SysUser user = (SysUser)getSession().getAttribute("user");
			int priNumber = privilegeService.queryPrivilegeNum(String.valueOf(id));
			if(priNumber != 0) {
				printJSON("300","该模块已有权限生成，请先删除权限！",false);
			}else if(!user.isSuper() && !user.getId().equals(catalog.getCreateBy())) {
				printJSON("300","当前用户不是该模块的创建者！",false);
			}else {
				catalogService.doDeleteAllCatalog(id);
				printJSON("success");
			}
		}catch(Exception e) {
			if (log.isDebugEnabled()) {
				e.printStackTrace();
				log.debug("save:" + e.getMessage());
			}
			printJSON("fail");
		}
		return NONE;
	}
	
	public String fintListId() throws Exception{
		try {
			HttpServletRequest request = getRequest();
			String catalogId = request.getParameter("cid");
			Catalog catalog = catalogService.findCatalogById(Long.parseLong(catalogId));
			ObjectMapper objectMapper = new ObjectMapper();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter printWriter = response.getWriter();
			objectMapper.writeValue(printWriter, catalog);
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
			
		}
		return NONE;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrcPackge() {
		return srcPackge;
	}

	public void setSrcPackge(String srcPackge) {
		this.srcPackge = srcPackge;
	}

	public String getPagePackage() {
		return pagePackage;
	}

	public void setPagePackage(String pagePackage) {
		this.pagePackage = pagePackage;
	}

}
