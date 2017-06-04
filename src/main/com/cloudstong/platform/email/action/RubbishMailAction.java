/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.email.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.common.QueryCriteria;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.email.model.RubbishMail;
import com.cloudstong.platform.email.service.RubbishMailService;

/**
 * @author Jason
 * 
 *         Created on 2014-9-24
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/platform/email/rubbishMail")
@Results({ @Result(name = "list", location = "/pages/email/dustbin.jsp"), @Result(name = "view", location = "/pages/email/rubbishMailView.jsp") })
public class RubbishMailAction extends BaseAction {

	private static final long serialVersionUID = 430418998220758881L;

	@Resource
	private RubbishMailService rubbishMailService;

	private RubbishMail rubbishMail;

	private Long[] rubbishMailIDs;

	private Long id;

	@Action("list")
	public String list() {
		queryCriteria = new QueryCriteria();
		if (this.pageNum == null || "".equals(this.pageNum)) {
			this.pageNum = 1;
		}

		queryCriteria.setCurrentPage(this.pageNum);
		queryCriteria.setPageSize(this.numPerPage);
		if (rubbishMail != null) {
			String subject = rubbishMail.getSubject();
			if (subject != null && subject.trim().length() != 0) {
				queryCriteria.addQueryCondition("tbl_subject", "%" + subject.trim() + "%");
			}
		}

		queryCriteria.setOrderField("comm_updateDate");
		queryCriteria.setOrderDirection(Constants.SORT_DIRECTION_DESC);

		pageResult = rubbishMailService.queryRubbishMail(queryCriteria);
		return "list";
	}

	@Action("resetState")
	public String resetState() {
		if (rubbishMailIDs != null) {
			rubbishMailService.doResetState(rubbishMailIDs);
		}
		printJSON("success");
		return NONE;
	}

	@Action("delete")
	public String delete() {
		if (rubbishMailIDs != null) {
			rubbishMailService.doDeletePermanently(rubbishMailIDs);
		}
		printJSON("success");
		return NONE;
	}

	@Action("view")
	public String view() {
		if (id != null) {
			rubbishMail = rubbishMailService.findRubbishMailById(id);
		}
		return "view";
	}

	public RubbishMail getRubbishMail() {
		return rubbishMail;
	}

	public void setRubbishMail(RubbishMail rubbishMail) {
		this.rubbishMail = rubbishMail;
	}

	public Long[] getRubbishMailIDs() {
		return rubbishMailIDs;
	}

	public void setRubbishMailIDs(Long[] rubbishMailIDs) {
		this.rubbishMailIDs = rubbishMailIDs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
