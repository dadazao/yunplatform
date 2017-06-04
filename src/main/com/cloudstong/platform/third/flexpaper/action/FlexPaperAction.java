package com.cloudstong.platform.third.flexpaper.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.cloudstong.platform.core.util.DocumentConverterUtil;
import com.cloudstong.platform.core.util.PathUtil;
import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;
import com.cloudstong.platform.system.model.SysUser;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:文本域
 */
public class FlexPaperAction extends CompexDomainAction {

	private static final long serialVersionUID = 5066905381870932702L;
	
	/**
	 * 需要打开的pdf文件名称
	 */
	private String filePathAndName;
	private String swfDirAndName;
	private String isPrint = "false";
	private String isReport = "false";
	
	private int fileNum;
	
	public String open() throws IOException{
		try {
			SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
			
			String serverPath = PathUtil.getWebInfPath(); 
			String pdfPath = serverPath+filePathAndName.substring(0, filePathAndName.lastIndexOf("/"));
			String swfPath = serverPath+filePathAndName.substring(0, filePathAndName.lastIndexOf("."));
			String fileName = filePathAndName.substring(filePathAndName.lastIndexOf("/")+1, filePathAndName.lastIndexOf("."));
			String extensionName = filePathAndName.substring(filePathAndName.lastIndexOf(".")+1);
			
			File swfDirs = new File(swfPath);
			if(!swfDirs.exists()){
				swfDirs.mkdirs();
			}
			try {
				if ("doc,docx,xls,xlsx,ppt,pptx,pdf".contains(extensionName)) {
					File pdfFile = new File(pdfPath+"/"+fileName+".pdf");
					if(!pdfFile.isFile()){
						DocumentConverterUtil.convert(serverPath+filePathAndName, pdfPath+"/"+fileName+".pdf");
					}
					
					boolean isPdfFile = false;
					while(!isPdfFile){
						if(pdfFile.isFile()){
							isPdfFile = true;
							DocumentConverterUtil.pdf2swf(pdfPath+"/"+fileName+".pdf", swfPath+"/"+fileName, false);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileNum = swfDirs.list().length;
			swfDirAndName = filePathAndName.substring(0, filePathAndName.lastIndexOf("."))+"/"+fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "flexPaperOpen";
	}
	
	public String getFilePathAndName() {
		return filePathAndName;
	}

	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}

	public String getSwfDirAndName() {
		return swfDirAndName;
	}

	public void setSwfDirAndName(String swfDirAndName) {
		this.swfDirAndName = swfDirAndName;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public String getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}
	
	
}
