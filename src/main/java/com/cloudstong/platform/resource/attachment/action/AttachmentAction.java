/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.resource.attachment.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.PageResult;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.attachment.service.AttachmentService;
import com.cloudstong.platform.resource.catalog.model.Catalog;
import com.cloudstong.platform.resource.form.model.FormButton;
import com.cloudstong.platform.resource.tabulation.model.Tabulation;
import com.cloudstong.platform.resource.tabulation.model.TabulationButton;
import com.cloudstong.platform.resource.tabulation.model.TabulationOpt;
import com.cloudstong.platform.system.model.SysPrivilege;

/**
 * @author liuqi
 * 
 *         Created on 2014-10-8
 * 
 *         Description:
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/resource/attachment")
@Results(value = { 
		@Result(name = "attachmentList", location = "/WEB-INF/view/resource/attachment/attachmentList.jsp")
})
public class AttachmentAction extends BaseAction {
	
	@Resource
	private AttachmentService attachmentService;
	
	private Long module;
	
	@Action("attachmentattachmentList")
	public String attachmentList(){
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		try {
			if(module!=null && !module.equals(0l)){
				queryCriteria.addQueryCondition("tbl_catalogid", module);
				this.pageResult = attachmentService.queryAttachment(queryCriteria);
			}else{
				this.pageResult = new PageResult();
				this.pageResult.setPageSize(queryCriteria.getPageSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled())
				log.error(e.getMessage(),e);
		}
		return "attachmentList";
	}
	
	@Action("attachmentdelete")
	public String delete() throws Exception {
		try {
			if(selectedIDs != null) {
				attachmentService.doDeleteAttachments(selectedIDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
		return NONE;
	}

	public Long getModule() {
		return module;
	}

	public void setModule(Long module) {
		this.module = module;
	}
	
}
