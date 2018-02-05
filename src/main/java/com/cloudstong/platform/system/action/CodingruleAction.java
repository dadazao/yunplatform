/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.action;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.dictionary.model.Dictionary;
import com.cloudstong.platform.resource.dictionary.service.DictionaryService;
import com.cloudstong.platform.system.model.Codingrule;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.CodingruleService;

/**
 * @author Allan
 * 
 *         Created on 2014-9-29 14:58:55
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/codingrule")
@Results(value = { 
	@Result(name = "list", location = "/WEB-INF/view/system/codingrule/codingruleList.jsp"),
	@Result(name = "add", location = "/WEB-INF/view/system/codingrule/codingruleEdit.jsp"),
	@Result(name = "edit", location = "/WEB-INF/view/system/codingrule/codingruleEdit.jsp"),
	@Result(name = "view", location = "/WEB-INF/view/system/codingrule/codingruleView.jsp")
})
public class CodingruleAction extends BaseAction {

	@Resource
	private CodingruleService codingruleService;
	
	@Resource
	private DictionaryService dictionaryService;
	
	private Codingrule codingrule;
	
	private List<Dictionary> bianmaduixiangList;
	
	private List<Dictionary> bianmafangshiList;
	
	private Long[] codingruleIDs;
	
	
	@Action("save")
	public String save() throws Exception{
	    try {
	    	SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
	    	Long id = codingruleService.doSaveCodingrule(codingrule,user);
			printJSON("success", false, id.toString());
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
		}
	    return NONE;
	}
	
	@Action("edit")
	public String edit() {
	    this.codingrule = codingruleService.findCodingruleById(codingrule.getId());
	    bianmaduixiangList = dictionaryService.queryByParent(10000002440001L);
		bianmafangshiList = dictionaryService.queryByParent(10000002440004L);
		return "edit";
	}
	
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (codingruleIDs != null) {
				codingruleService.doDeleteCodingrules(codingruleIDs);
			}
			printJSON("success");
		} catch (Exception e) {
			printJSON("fail");
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return NONE;
	}
	
	@Action("view")
	public String view() {
		this.codingrule = codingruleService.findCodingruleById(codingrule.getId());
		bianmaduixiangList = dictionaryService.queryByParent(10000002440001L);
		bianmafangshiList = dictionaryService.queryByParent(10000002440004L);
		return "view";
	}
	
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if(codingrule != null) {
			if(codingrule.getBianmaduixiang() != null && !"".equals(codingrule.getBianmaduixiang())) {
				queryCriteria.addQueryCondition("bianmaduixiang", codingrule.getBianmaduixiang());
			}
			if(codingrule.getZidongbianma() != null && !"".equals(codingrule.getZidongbianma())) {
				queryCriteria.addQueryCondition("zidongbianma", codingrule.getZidongbianma());
			}
			if(codingrule.getBianmaqianzhui() != null && !"".equals(codingrule.getBianmaqianzhui())) {
				queryCriteria.addQueryCondition("bianmaqianzhui", "%" + codingrule.getBianmaqianzhui() + "%");
			}
			if(codingrule.getBianmafangshi() != null && !"".equals(codingrule.getBianmafangshi())) {
				queryCriteria.addQueryCondition("bianmafangshi", codingrule.getBianmafangshi());
			}
			if(codingrule.getKefouxiugai() != null && !"".equals(codingrule.getKefouxiugai())) {
				queryCriteria.addQueryCondition("kefouxiugai", codingrule.getKefouxiugai());
			}
			if(codingrule.getBeizhu() != null && !"".equals(codingrule.getBeizhu())) {
				queryCriteria.addQueryCondition("beizhu", "%" + codingrule.getBeizhu() + "%");
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		
		//列表按更新时间降序排序
		queryCriteria.setOrderField(Constants.DEFAULT_SORT_FIELD);
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		
		bianmaduixiangList = dictionaryService.queryByParent(10000002440001L);
		bianmafangshiList = dictionaryService.queryByParent(10000002440004L);
		
		this.pageResult = codingruleService.queryCodingrule(queryCriteria);
		
		return "list";
	}
	
	@Action("add")
	public String add() {
		codingrule = new Codingrule();
		bianmaduixiangList = dictionaryService.queryByParent(10000002440001L);
		bianmafangshiList = dictionaryService.queryByParent(10000002440004L);
		return "add";
	}
	
	public Codingrule getCodingrule() {
		return codingrule;
	}

	public void setCodingrule(Codingrule codingrule) {
		this.codingrule = codingrule;
	}
	
	public Long[] getCodingruleIDs() {
		return codingruleIDs;
	}

	public void setCodingruleIDs(Long[] codingruleIDs) {
		this.codingruleIDs = codingruleIDs;
	}

	public CodingruleService getCodingruleService() {
		return codingruleService;
	}

	public void setCodingruleService(CodingruleService codingruleService) {
		this.codingruleService = codingruleService;
	}

	public List<Dictionary> getBianmaduixiangList() {
		return bianmaduixiangList;
	}

	public void setBianmaduixiangList(List<Dictionary> bianmaduixiangList) {
		this.bianmaduixiangList = bianmaduixiangList;
	}

	public List<Dictionary> getBianmafangshiList() {
		return bianmafangshiList;
	}

	public void setBianmafangshiList(List<Dictionary> bianmafangshiList) {
		this.bianmafangshiList = bianmafangshiList;
	}

}