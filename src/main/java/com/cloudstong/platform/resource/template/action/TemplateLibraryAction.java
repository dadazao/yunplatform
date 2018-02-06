package com.cloudstong.platform.resource.template.action;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;

/**
 * @author sam
 * Created on 2012-11-21
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:主要负责模板库的一些操作
 */
@SuppressWarnings("serial")
@ParentPackage("default")
@Namespace("/pages/resource/library")
@Results(value = { 
		@Result(name="list", location = "/WEB-INF/view/resource/template/library/compexLibraryList.jsp"),
		@Result(name="add", location = "/WEB-INF/view/resource/template/library/compexLibraryEdit.jsp"),
		@Result(name="edit", location = "/WEB-INF/view/resource/template/library/compexLibraryEdit.jsp"),
		@Result(name="view", location = "/WEB-INF/view/resource/template/library/compexLibraryView.jsp")
})
public class TemplateLibraryAction extends CompexDomainAction {

	@Action("list")
	public String list() {
		return super.list();
	}
	
	@Action("add")
	public String add() {
		return super.add();
	}
	
	@Action("edit")
	public String edit() {
		return super.add();
	}
	
	@Action("view")
	public String view() throws Exception {
		return super.view();
	}
	
	@Action("del")
	public String delete() throws Exception {
		return super.delete();
	}
	
	@Action("dataJson")
	public String dataJson() throws Exception {
		return super.dataJson();
	}
	
	@Action("viewJson")
	public String viewJson() throws Exception {
		return super.viewJson();
	}
	
	@Action("singleDelete") 
	public String singleDelete() throws Exception {
		return super.simpleDelete();
	}
	
	@Action("logicDelete") 
	public String logicDelete() throws IOException  {
		return super.logicDelete();
	}
	
}
