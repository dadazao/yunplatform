/*******************************************************************************
 * Licensed Materials - Property of Cloudstong 
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.business.peach.action;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.business.peach.model.Peach;
import com.cloudstong.business.peach.service.PeachService;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;

/**
 * @author Allan
 * 
 *         Created on 2015-6-11 12:28:44
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/business/peach")
@Results(value = { 
	@Result(name = "list", location = "/WEB-INF/view/business/peach/peachList.jsp"),
	@Result(name = "add", location = "/WEB-INF/view/business/peach/peachEdit.jsp"),
	@Result(name = "edit", location = "/WEB-INF/view/business/peach/peachEdit.jsp"),
	@Result(name = "view", location = "/WEB-INF/view/business/peach/peachView.jsp")
})
public class PeachAction extends BaseAction {

	@Resource
	private PeachService peachService;
	
	private Peach peach;
	
	private Long[] peachIDs;
	
	@Action("save")
	public String save() throws Exception{
	    try {
			peachService.doSavePeach(peach);
			printSuccess(peach);
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
	    this.peach = peachService.findPeachById(peach.getId());
		return "edit";
	}
	@Action("delete")
	public String delete() throws IOException {
		try {
			if (peachIDs != null) {
				peachService.doDeletePeachs(peachIDs);
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
		this.peach = peachService.findPeachById(peach.getId());
		return "view";
	}
	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if(peach != null) {
			if(peach.getPrice() != null && !"".equals(peach.getPrice())) {
				queryCriteria.addQueryCondition("price", peach.getPrice());
			}
			if(peach.getGongxiao() != null && !"".equals(peach.getGongxiao())) {
				queryCriteria.addQueryCondition("gongxiao", "%" + peach.getGongxiao() + "%");
			}
			if(peach.getPinzhong() != null && !"".equals(peach.getPinzhong())) {
				queryCriteria.addQueryCondition("pinzhong", "%" + peach.getPinzhong() + "%");
			}
			if(peach.getMaturetime() != null && !"".equals(peach.getMaturetime())) {
				queryCriteria.addQueryCondition("maturetime", peach.getMaturetime());
			}
		}
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}
		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		
		//列表按更新时间降序排序
		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);
		
		this.pageResult = peachService.queryPeach(queryCriteria);
		
		return "list";
	}
	@Action("add")
	public String add() {
		peach = new Peach();
		return "add";
	}
	
	public Peach getPeach() {
		return peach;
	}

	public void setPeach(Peach peach) {
		this.peach = peach;
	}
	
	public Long[] getPeachIDs() {
		return peachIDs;
	}

	public void setPeachIDs(Long[] peachIDs) {
		this.peachIDs = peachIDs;
	}

}
