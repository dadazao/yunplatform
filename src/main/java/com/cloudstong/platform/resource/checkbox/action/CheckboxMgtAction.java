package com.cloudstong.platform.resource.checkbox.action;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.resource.metadata.action.CompexDomainAction;

/**
 * @author michael
 * Created on 2012-11-14
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:复选框Action
 */
@ParentPackage("default")
@Namespace("/pages/resource/checkbox")
@Results(value = { 
		@Result(name="list", location = "/WEB-INF/view/resource/checkbox/compexCheckboxMgtList.jsp"),
		@Result(name="add", location = "/WEB-INF/view/resource/checkbox/compexCheckboxMgtEdit.jsp"),
		@Result(name="edit", location = "/WEB-INF/view/resource/checkbox/compexCheckboxMgtEdit.jsp"),
		@Result(name="view", location = "/WEB-INF/view/resource/checkbox/compexCheckboxMgtView.jsp")
})
public class CheckboxMgtAction extends CompexDomainAction {

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
